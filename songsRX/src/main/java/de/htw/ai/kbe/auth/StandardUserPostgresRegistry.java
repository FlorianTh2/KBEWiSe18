package de.htw.ai.kbe.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import de.htw.ai.kbe.songs.Song;
import de.htw.ai.kbe.user.IUserRegistry;

public class StandardUserPostgresRegistry implements IUserRegistry<StandardUser>
{
	public static String defaultPath;
	
	private EntityManagerFactory entityManagerFactory;
	private ConcurrentHashMap<Integer, StandardUser> data;
	private ConcurrentHashMap<String, StandardUser> auth;
	private ConcurrentHashMap<String, Integer> userIds;
	
	@Inject
	public StandardUserPostgresRegistry(EntityManagerFactory entityManagerFactory)
	{
		this.entityManagerFactory = entityManagerFactory;
		this.data = new ConcurrentHashMap<>();
		this.auth = new ConcurrentHashMap<>();
		this.userIds = new ConcurrentHashMap<>(); 
	}
	
	@Override
	public void add(StandardUser user)
	{	
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try
        {
        	entityTransaction.begin();
        	entityManager.persist(user);
            entityTransaction.commit();
        } catch (Exception e) {
        	entityTransaction.rollback();
            e.printStackTrace();
        } finally {
        	entityManager.close();
        }
	}

	@Override
	public StandardUser get(int id)
	{
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        StandardUser user = null;
        
        try
        {
        	user = entityManager.find(StandardUser.class, id);
        } finally {
        	entityManager.close();
        }
        
        return user;
	}

	@Override
	public StandardUser remove(int id)
	{
		StandardUser user = get(id);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
		
        try
        {
        	entityTransaction.begin();
        	entityManager.remove(user);
            entityTransaction.commit();
        } catch (Exception e) {
        	entityTransaction.rollback();
            e.printStackTrace();
        } finally {
        	entityManager.close();
        }
		
		return user;
	}

	@Override
	public void delete(int id)
	{
		remove(id);
	}

	@Override
	public List<StandardUser> users()
	{
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		try
		{
			TypedQuery<StandardUser> query = entityManager.createQuery("select * from user", StandardUser.class);
			return query.getResultList();
		} finally {
			entityManager.close();
		}
	}
	
	@Override
	public String authorize(int id)
	{
		StandardUser user = get(id);
		
		if(user == null)
			return null;
		
		for(String key : this.auth.keySet())
			if(this.auth.get(key).getId() == id)
				return key;
		
		String token = user.generateToken();
		this.auth.put(token, user);
		
		return token;
	}
	
	@Override
	public void unauthorize(String token)
	{
		this.auth.remove(token);
	}
	
	@Override
	public boolean authorized(String token)
	{
		return this.auth.containsKey(token);
	}

	@Override
	public StandardUser byUserId(String userId)
	{
		if(!this.userIds.containsKey(userId))
			return null;
		
		return this.data.get(this.userIds.get(userId));
	}
}