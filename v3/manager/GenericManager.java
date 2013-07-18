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
public interface GenericManager<T> {

    public T create(T t);

    public void delete(T t);

    public T modify(T t);

    public List<T> findAll();

    public T findById(String id);
}
