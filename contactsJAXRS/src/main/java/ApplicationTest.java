import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import de.htw.ai.kbe.songs.Song;

public class ApplicationTest {

	public static void main(String[] args)
	{
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("_s0559090__songsdb");
		EntityManager manager = factory.createEntityManager();

		try {
			manager.getTransaction().begin();
			Song song = new Song("title", "artist", "album", 2010, 1000);
			manager.persist(song);
			manager.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("ERROR");
		}
		
	}

}
