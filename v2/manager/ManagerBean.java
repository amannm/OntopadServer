/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.manager;

import java.util.List;

/**
 *
 * @author Amann
 */
public interface ManagerBean<T> {

    T create(T t);

    void delete(T t);

    T modify(T t);

    T findById(String id);

    List<T> findAll();
}
