DROP TABLE users;

DROP TABLE song;

DROP TABLE songlist;

DROP TABLE songlist_song;


CREATE TABLE users(
		id SERIAL PRIMARY KEY, 
		userId VARCHAR(50) NOT NULL, 
		firstName VARCHAR(50) NOT NULL,
		lastName VARCHAR(50) NOT NULL
);


CREATE TABLE song(
		id SERIAL PRIMARY KEY, 
		title VARCHAR(50) NOT NULL,
		artist VARCHAR(50),
		album VARCHAR(50),
		released INTEGER
);


CREATE TABLE songlist(
		id SERIAL PRIMARY KEY, 
		ownerid VARCHAR(50) NOT NULL,
		isprivate BOOLEAN NOT NULL
);


CREATE TABLE songlist_song(
		songlist_id INTEGER NOT NULL,
		song_id INTEGER NOT NULL
);


INSERT INTO users(id, userId, firstName, lastName)
	VALUES(1, 'mmuster', 'Maxime', 'Muster');
INSERT INTO users(id, userId, firstName, lastName)
	VALUES(2, 'eschuler', 'Elena', 'Schuler');

INSERT INTO song(id, title, artist, album, released)
	VALUES(10, '7 Years', 'Lukas Graham', 'Lukas Graham (Blue Album)', 2015);
INSERT INTO song(id, title, artist, album, released)
	VALUES(9, 'Private Show', 'Britney Spears', 'Glory', 2016);
INSERT INTO song(id, title, artist, album, released)
	VALUES(8, 'No', 'Meghan Trainor', 'Thank You', 2016);
INSERT INTO song(id, title, artist, album, released)
	VALUES(7, 'i hate u, i love u', 'Gnash', 'Top Hits 2017', 2017);
INSERT INTO song(id, title, artist, album, released)
	VALUES(6, 'I Took a Pill in Ibiza', 'Mike Posner', 'At Night, Alone.', 2016);
INSERT INTO song(id, title, artist, album, released)
	VALUES(5, 'Bad Things', 'Camila Cabello, Machine Gun Kelly', 'Bloom', 2017);
INSERT INTO song(id, title, artist, album, released)
	VALUES(4, 'Ghostbusters (I m not a fraid)', 'Fall Out Boy, Missy Elliott', 'Ghostbusters', 2016);
INSERT INTO song(id, title, artist, released)
	VALUES(3, 'Team', 'Iggy Azalea', 2015);
INSERT INTO song(id, title, artist, album, released)
	VALUES(2, 'Mom', 'Meghan Trainor, Kelli Trainor', 'Thank You', 2016);
INSERT INTO song(id, title, artist, album, released)
	VALUES(1, 'Can t Stop the Feeling', 'Justin Timberlake', 'Trolls', 2016);

INSERT INTO songlist(id, ownerid, isprivate)
	VALUES (0, 'mmuster', TRUE);
INSERT INTO songlist(id, ownerid, isprivate)
	VALUES (1, 'mmuster', FALSE);
INSERT INTO songlist(id, ownerid, isprivate)
	VALUES (2, 'eschuler', FALSE);

INSERT INTO songlist_song(songlist_id, song_id)
	VALUES(0, 1);
INSERT INTO songlist_song(songlist_id, song_id)
	VALUES(0, 2);
INSERT INTO songlist_song(songlist_id, song_id)
	VALUES(0, 4);
INSERT INTO songlist_song(songlist_id, song_id)
	VALUES(0, 8);

INSERT INTO songlist_song(songlist_id, song_id)
	VALUES(1, 3);
INSERT INTO songlist_song(songlist_id, song_id)
	VALUES(1, 3);


GRANT ALL PRIVILEGES ON TABLE users TO s0558101;
GRANT ALL PRIVILEGES ON TABLE song TO s0558101;
GRANT ALL PRIVILEGES ON TABLE songlist TO s0558101;
GRANT ALL PRIVILEGES ON TABLE songlist_song TO s0558101;

GRANT ALL PRIVILEGES ON TABLE users TO s0559090;
GRANT ALL PRIVILEGES ON TABLE song TO s0559090;
GRANT ALL PRIVILEGES ON TABLE songlist TO s0559090;
GRANT ALL PRIVILEGES ON TABLE songlist_song TO s0559090;

GRANT ALL PRIVILEGES ON TABLE users TO s0558101_songsdb_generic;
GRANT ALL PRIVILEGES ON TABLE song TO s0558101_songsdb_generic;
GRANT ALL PRIVILEGES ON TABLE songlist TO s0558101_songsdb_generic;
GRANT ALL PRIVILEGES ON TABLE songlist_song TO s0558101_songsdb_generic;


GRANT ALL PRIVILEGES ON TABLE users TO _s0559090__songsdb_generic;
GRANT ALL PRIVILEGES ON TABLE song TO _s0559090__songsdb_generic;
GRANT ALL PRIVILEGES ON TABLE songlist TO _s0559090__songsdb_generic;
GRANT ALL PRIVILEGES ON TABLE songlist_song TO _s0559090__songsdb_generic;

