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

    private EntityManagerFactory emf;

    @Inject
    public SongPostgresDatabase(EntityManagerFactory emf) {
        this.emf = emf;
    }
	
	
	@Override
	public void insert(Entry<Song> entry) {
		
	}

	@Override
	public Entry<Song> retrieve(int id) {
		return null;
	}

	@Override
	public void replace(Entry<Song> entryOld, Entry<Song> entryNew) {
		
	}

	@Override
	public boolean exists(int id) {
		return false;
	}

	@Override
	public void add(Song value) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            
            em.persist(value);
            transaction.commit(); // wir müssen wir irgendwie die id des values returnen
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error adding song: " + e.getMessage());
            transaction.rollback();
            throw new PersistenceException("Could not persist entity: " + e.toString());
        } finally {
            em.close();
        }
	}

	@Override
	public Song get(int id) {
        EntityManager em = emf.createEntityManager();
        Song entity = null;
        try {
            entity = em.find(Song.class, id);
        } finally {
            em.close();
        }
        return entity;
    }

	@Override
	public void delete(int id) {
 		
	}

	@Override
	public Song remove(int id) {
 		return null;
	}

	@Override
	public void update(int id, Song value) {
        EntityManager em = emf.createEntityManager();
        Song entity = null;
        try {
            entity = em.find(Song.class, id);
    		if((entity.getId() == value.getId())) 
    		{
    			em.getTransaction().begin();
    			entity.setTitle(value.getTitle());
    			entity.setAlbum(value.getAlbum());
    			entity.setArtist(value.getArtist());
    			entity.setReleased(value.getReleased());
    			em.getTransaction().commit();
    		}
    		

            
        } finally {
            em.close();
        }
	}

	@Override
	public int size() {
 		return 0;
	}

	@Override
	public void clear() {
 		
	}

	@Override
	public List<Song> values() {
		 EntityManager em = emf.createEntityManager();
	        try {
	            TypedQuery<Song> query = em.createQuery("SELECT s FROM Contact s", Song.class);
	            return query.getResultList();
	        } finally {
	            em.close();
	        }
	    }

	@Override
	public List<Entry<Song>> entries() {
 		return null;
	}

	@Override
	public String toJson() {
 		return null;
	}

	@Override
	public void fromJson(String json) {
 		
	}

	@Override
	public String toXml() {
 		return null;
	}

	@Override
	public void fromXml(String xml) {
 		
	}

}
