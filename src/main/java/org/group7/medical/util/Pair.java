package org.group7.medical.util;

public class Pair<K, V> {
	public K key;
	public V val;
	
	public Pair(K key, V val) {
		this.key = key;
		this.val = val;
	}
	
	public static <K, V> Pair<K, V> of(K key, V val) {
		return new Pair<K, V>(key, val);
	}
}
