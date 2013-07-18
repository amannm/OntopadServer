/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontopad.data;

/**
 *
 * @author admin
 */
public interface KeyValueStore<K, V> {

    public void open();

    public void close();

    public V get(K key);

    public boolean put(K key, V value);

    public boolean delete(K key);
}
