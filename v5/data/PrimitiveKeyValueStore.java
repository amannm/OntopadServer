/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.data;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.bind.tuple.LongBinding;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author admin
 */
public class PrimitiveKeyValueStore<K, V> implements KeyValueStore<K, V> {

    @Inject
    @Env
    private Environment environment;
    @Inject
    @ClassInfo
    private StoredClassCatalog classCatalog;
    private Class<K> keyClass;
    private Class<V> valueClass;
    private Database values;
    private final String name = UUID.randomUUID().toString();
    private static final Logger log = Logger.getLogger(PrimitiveKeyValueStore.class.getName());

    public PrimitiveKeyValueStore(Class<K> keyClass, Class<V> valueClass) {
        this.keyClass = keyClass;
        this.valueClass = valueClass;
    }

    @Override
    public void open() {
        DatabaseConfig myDbConfig = new DatabaseConfig();
        myDbConfig.setAllowCreate(true);
        myDbConfig.setTransactional(true);
        try {
            values = environment.openDatabase(null, name, myDbConfig);
            log.log(Level.INFO, "{0} opened successfully", name);
        } catch (DatabaseException de) {
            log.log(Level.SEVERE, name + " could not be opened: {0}", de.getMessage());
        }
    }

    @Override
    public void close() {
        if (values != null) {
            try {
                values.close();
                log.log(Level.INFO, "{0} closed successfully", name);
            } catch (DatabaseException de) {
                log.log(Level.SEVERE, name + " could not be closed: {0}", de.getMessage());
            }
        } else {
            log.log(Level.WARNING, "{0} could not be closed because it is not open", name);
        }
    }

    @Override
    public V get(K key) {
        DatabaseEntry keyEntry = new DatabaseEntry();
        EntryBinding keyBinding = TupleBinding.getPrimitiveBinding(keyClass);
        keyBinding.objectToEntry(key, keyEntry);
        DatabaseEntry valueEntry = new DatabaseEntry();
        if (values.get(null, keyEntry, valueEntry, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
            EntryBinding valueBinding = TupleBinding.getPrimitiveBinding(valueClass);
            return valueClass.cast(valueBinding.entryToObject(valueEntry));
        }
        return null;
    }

    @Override
    public boolean put(K key, V value) {
        DatabaseEntry keyEntry = new DatabaseEntry();
        EntryBinding keyBinding = TupleBinding.getPrimitiveBinding(keyClass);
        keyBinding.objectToEntry(key, keyEntry);
        DatabaseEntry valueEntry = new DatabaseEntry();
        EntryBinding valueBinding = TupleBinding.getPrimitiveBinding(valueClass);
        valueBinding.objectToEntry(value, valueEntry);
        return values.put(null, keyEntry, valueEntry) == OperationStatus.SUCCESS;
    }

    @Override
    public boolean delete(K key) {
        DatabaseEntry keyEntry = new DatabaseEntry();
        EntryBinding keyBinding = TupleBinding.getPrimitiveBinding(keyClass);
        keyBinding.objectToEntry(key, keyEntry);
        return values.delete(null, keyEntry) == OperationStatus.SUCCESS;
    }
}
