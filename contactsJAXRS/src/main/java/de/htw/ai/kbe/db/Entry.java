package de.htw.ai.kbe.db;

import java.util.List;

public abstract class Entry<V extends IValue>
{
	private Integer id;
	private V value;
	
	public Entry()
	{
		super();
		this.id = null;
		this.value = null;
	}
	
	public Entry(V value)
	{
		super();
		this.id = null;
		this.value = value;
	}
	
	public Entry(int id, V value)
	{
		super();
		this.id = Integer.valueOf(id);
		this.value = value;
		mergeId();
	}
	
	public boolean hasId()
	{
		return this.id != null && this.id.intValue() >= 0;
	}
	
	public int getId()
	{
		return this.id.intValue();
	}
	
	public void setId(int id)
	{
		this.id = Integer.valueOf(id);
		mergeId();
	}
	
	public V retrieve()
	{
		return mergeId();
	}
	
	public void store(V value)
	{
		this.value = mergeId();
	}
	
	
	private V mergeId()
	{
		if(hasId())
			this.value.setId(this.id.intValue());
		return this.value;
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
