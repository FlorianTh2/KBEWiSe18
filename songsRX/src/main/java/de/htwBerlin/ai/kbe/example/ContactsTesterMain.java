package de.htwBerlin.ai.kbe.example;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import de.htw.ai.kbe.auth.StandardUser;
import de.htw.ai.kbe.songs.Song;
import de.htw.ai.kbe.songs.SongList;

public class ContactsTesterMain {
	private static final String PERSISTENCE_UNIT_NAME = "_s0559090__songsdb";

    public static void main(String[] args) {
        EntityManager em = null;
        // Datei persistence.xml wird automatisch eingelesen, beim Start der Applikation
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        try {
            // EntityManager bietet Zugriff auf Datenbank
            em = factory.createEntityManager();
//            Song songFromDB = em.find(Song.class, new Integer(1));
//            System.out.println("hier: " + songFromDB);
//
//            em.getTransaction().begin();
//            Song song1 = new Song("test1", "artistTest", "testAlbum", new Integer(2100));
//            em.persist(song1);
//            em.getTransaction().commit();
//            
//            System.out.println("##############");
//            TypedQuery<StandardUser> query = em.createQuery("SELECT u FROM StandardUser u", StandardUser.class);
//            List<StandardUser> list = query.getResultList();
//            
//            
//            System.out.println(list);
//            
//            for(StandardUser u : list)
//            	System.out.println(u);
        	        	
        	System.out.println("hi");

        	Set<Song> list = new HashSet<Song>();
        	list.add(new Song("7 Years", "Lukas Graham", "Lukas Graham (Blue Album)", new Integer(2015),new Integer(10)));
        	
        	StandardUser user = new StandardUser(new Integer(1),"mmuster","Maxime", "Muster");

        	em.getTransaction().begin();
        	SongList songlist = new SongList(user,list, new Integer(123));
            em.persist(songlist);
        	em.getTransaction().commit();
        	
        	
        } catch (Exception ex) {
        	System.out.println(ex.getMessage());
            em.getTransaction().rollback();
        } finally {
            if (em != null)
            	em.close();
            factory.close();
        }
        
        
    }
}


//Address address1 = new Address("Bobby Str 1", "JunoirCity", "56789", "Bobland", contact);
//Address address2 = new Address("New Str 2", "SeniorCity", "12345", "Bobland", contact);
//Set<Address> addressSet = new HashSet<>();
//addressSet.add(address1);
//addressSet.add(address2);
//contact.setAddressSet(addressSet);
// Wir persistieren nur contact,
// wegen cascade=CascadeType.ALL werden auch address1, address 2 persistiert