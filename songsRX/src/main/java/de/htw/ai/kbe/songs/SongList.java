package de.htw.ai.kbe.songs;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlRootElement;

import de.htw.ai.kbe.auth.StandardUser;
import de.htw.ai.kbe.db.IValue;
import de.htw.ai.kbe.user.User;


@Table(name = "songlist")
@Entity
@XmlRootElement(name = "songlist")
public class SongList implements IValue
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@JoinColumn(name="ownerid")
	@OneToOne
	private StandardUser ownerid;
	
	//@OneToMany(mappedBy = "songlist", cascade = CascadeType.ALL)
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "songlist_song", joinColumns = @JoinColumn(name = "songlist_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn( name = "song_id", referencedColumnName = "id"))
	private Set<Song> songs;

	@Column(name = "visibility")
	private Integer visibility;

	public SongList()
	{
		super();
		this.id = null;
		this.ownerid = null;
		this.songs = new HashSet<Song>();
		this.visibility = 0;
	}
	
	public SongList(StandardUser owner, Set<Song> songs, Integer visibility)
	{
		super();
		this.id = null;
		this.ownerid = owner;
		this.songs = songs;
		this.visibility = visibility;
	}
	
	public SongList(Integer id, StandardUser owner, Set<Song> songs, Integer visibility)
	{
		super();
		this.id = id;
		this.ownerid = owner;
		this.songs = songs;
		this.visibility = visibility;
	}
	
	@Override
	public int getId()
	{
		return this.id.intValue();
	}

	@Override
	public void setId(int id)
	{
		this.id = Integer.valueOf(id);
	}

	public StandardUser getOwner()
	{
		return this.ownerid;
	}

	public void setOwner(StandardUser owner)
	{
		this.ownerid = owner;
	}

	public Set<Song> getSongs()
	{
		return this.songs;
	}

	public void setSongs(Set<Song> songs)
	{
		this.songs = songs;
	}

	public int getVisibility()
	{
		return this.visibility;
	}

	public void setVisibility(int visibility)
	{
		this.visibility = visibility;
	}
	
	public Visibility visiblity()
	{
		return Visibility.values()[getVisibility()];
	}
}
