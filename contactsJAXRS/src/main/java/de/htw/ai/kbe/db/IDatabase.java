package de.htw.ai.kbe.db;

import java.util.List;

public interface IDatabase<T extends Entry<V>, V>
{
	void insert(Entry<V> entry);
	Entry<V> retrieve(int id);
	void add(V value);
	V get(int id);
	void delete(int id);
	V remove(int id);
	int size();
	void clear();
	List<V> values();
	List<Entry<V>> entries();
	String toJson();
	void fromJson(String json);
	String toXml();
	void fromXml(String xml);
}
