// SongDAO für Postgres-SQL

package de.htw.ai.kbe.songs;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import de.htw.ai.kbe.db.Entry;
import de.htw.ai.kbe.db.IDatabase;

public class SongPostgresDatabase implements IDatabase<SongEntry, Song> {

    private EntityManagerFactory entityManagerFactory;

    @Inject
    public SongPostgresDatabase(EntityManagerFactory entityManagerFactory)
    {
        this.entityManagerFactory = entityManagerFactory;
    }
	
	
	@Override
	public void insert(Entry<Song> entry)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Entry<Song> retrieve(int id)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void replace(Entry<Song> entryOld, Entry<Song> entryNew) {
		
	}

	@Override
	public boolean exists(int id)
	{
		return get(id) != null;
	}

	@Override
	public void add(Song value) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        
        try {
            transaction.begin();
            
            entityManager.persist(value);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            throw new PersistenceException("Could not persist entity: " + e.toString());
        } finally {
        	entityManager.close();
        }
	}

	@Override
	public Song get(int id) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        Song entity = null;
        try {
            entity = entityManager.find(Song.class, id);
        } finally {
        	entityManager.close();
        }
        return entity;
    }

	@Override
	public void delete(int id)
	{
 		remove(id);
	}

	@Override
	public Song remove(int id) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
		
        Song song = null;
        
        try
        {
        	entityTransaction.begin();
        	
        	song = entityManager.find(Song.class, Integer.valueOf(id));
        	
        	if(song == null)
        		return null;
        		
        	entityManager.remove(song);
            entityTransaction.commit();
        } catch (Exception e) {
        	entityTransaction.rollback();
            e.printStackTrace();
        } finally {
        	entityManager.close();
        }
		
		return song;
	}

	@Override
	public void update(int id, Song value)
	{
		if(id != value.getId())
			return;
		
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        
        Song entity = null;
        try
        {
            entity = entityManager.find(Song.class, id);
			entityTransaction.begin();
			entity.setTitle(value.getTitle());
			entity.setAlbum(value.getAlbum());
			entity.setArtist(value.getArtist());
			entity.setReleased(value.getReleased());
			entityManager.persist(entity);
			entityTransaction.commit();
        } finally {
        	entityManager.close();
        }
	}

	@Override
	public int size() 
	{
 		return values().size();
	}

	@Override
	public void clear()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Song> values()
	{
		 EntityManager entityManager = entityManagerFactory.createEntityManager();
	     try
	     {
	    	 TypedQuery<Song> query = entityManager.createQuery("SELECT s FROM Song s", Song.class);
	         return query.getResultList();
	     } finally {
	    	 entityManager.close();
	     }
	}

	@Override
	public List<Entry<Song>> entries()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public String toJson()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void fromJson(String json)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public String toXml()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void fromXml(String xml)
	{
		throw new UnsupportedOperationException();
	}

}
