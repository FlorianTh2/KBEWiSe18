package de.htw.ai.kbe.db;

import java.util.List;

import de.htw.ai.kbe.songs.Song;

public interface IDatabase<T extends Entry<V>, V extends IValue>
{
	void insert(Entry<V> entry);
	Entry<V> retrieve(int id);
	void replace(Entry<Song> entryOld, Entry<Song> entryNew);
	boolean exists(int id);
	void add(V value);
	V get(int id);
	void delete(int id);
	V remove(int id);
	void update(int id, V value);
	int size();
	void clear();
	List<V> values();
	List<Entry<V>> entries();
	String toJson();
	void fromJson(String json);
	String toXml();
	void fromXml(String xml);
}
