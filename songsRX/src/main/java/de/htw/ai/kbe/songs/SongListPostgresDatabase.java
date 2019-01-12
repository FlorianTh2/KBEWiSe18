package de.htw.ai.kbe.songs;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import de.htw.ai.kbe.auth.StandardUser;
import de.htw.ai.kbe.db.Entry;
import de.htw.ai.kbe.db.IDatabase;

public class SongListPostgresDatabase implements IDatabase<SongListEntry, SongList>
{
	private EntityManagerFactory entityManagerFactory;
	
	@Inject
	public SongListPostgresDatabase(EntityManagerFactory entityManagerFactory)
	{
		this.entityManagerFactory = entityManagerFactory;
	}
	
	@Override
	public void insert(Entry<SongList> entry)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Entry<SongList> retrieve(int id)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void replace(Entry<Song> entryOld, Entry<Song> entryNew)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean exists(int id)
	{
		return get(id) != null;
	}

	@Override
	public void add(SongList value)
	{
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try
        {
        	entityTransaction.begin();
        	entityManager.persist(value);
            entityTransaction.commit();
        } catch (Exception e) {
        	entityTransaction.rollback();
            e.printStackTrace();
        } finally {
        	entityManager.close();
        }
	}

	@Override
	public SongList get(int id)
	{
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        SongList songList = null;
        
        try
        {
        	songList = entityManager.find(SongList.class, id);
        } finally {
        	entityManager.close();
        }
        
        return songList;
	}

	@Override
	public void delete(int id)
	{
		remove(id);
	}

	@Override
	public SongList remove(int id)
	{
		SongList songList = get(id);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
		
        try
        {
        	entityTransaction.begin();
        	entityManager.remove(songList);
            entityTransaction.commit();
        } catch (Exception e) {
        	entityTransaction.rollback();
            e.printStackTrace();
        } finally {
        	entityManager.close();
        }
		
		return songList;
	}

	@Override
	public void update(int id, SongList value)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int size()
	{
		return values().size();
	}

	@Override
	public void clear()
	{
	}

	@Override
	public List<SongList> values()
	{
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		try
		{
			TypedQuery<SongList> query = entityManager.createQuery("SELECT s FROM SongList s", SongList.class);
			return query.getResultList();
		} finally {
			entityManager.close();
		}
	}

	@Override
	public List<Entry<SongList>> entries()
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
