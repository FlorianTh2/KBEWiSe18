package de.htw.ai.kbe.db;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public interface IValue
{
	public int getId();
	public void setId(int id);
}
