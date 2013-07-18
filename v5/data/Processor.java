/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.data;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.TypeVariable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 *
 * @author admin
 */
public class Processor {

    public static void process(String path) throws IllegalArgumentException, IllegalAccessException {
        for (Field f : instance.getClass().getDeclaredFields()) {
            if (f.isAnnotationPresent(Store.class)) {
                Class<?> clazz = f.getClass();
                TypeVariable[] types = clazz.getTypeParameters();
                if (types.length == 2) {
                    Class K = types[0].getClass();
                    Class V = types[1].getClass();
                    if (V.isPrimitive()) {
                        f.set(instance, new PrimitiveKeyValueStore<>(K, V));
                    } else {
                        f.set(instance, new SerialKeyValueStore<>(K, V));
                    }
                }
            }
        }
    }

    private Iterable<Class> getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        List<Class> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }

    private List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}
