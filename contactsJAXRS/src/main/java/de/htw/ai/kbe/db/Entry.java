package de.htw.ai.kbe.db;

import java.util.List;

public abstract class Entry<V>
{
	private int id;
	private V value;
	
	public Entry(V value)
	{
		this.value = value;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public V retrieve()
	{
		return this.value;
	}
	
	public void store(V value)
	{
		this.value = value;
	}
	
	public abstract String toJson();
	public abstract boolean fromJson(String json);
	public abstract String entriesToJson(List<Entry<V>> entries);
	public abstract List<Entry<V>> entriesFromJson(String json);
	public abstract String toXml();
	public abstract boolean fromXml(String xml);
	public abstract String entriesToXml(List<Entry<V>> entries);
	public abstract List<Entry<V>> entriesFromXml(String xml);
}
