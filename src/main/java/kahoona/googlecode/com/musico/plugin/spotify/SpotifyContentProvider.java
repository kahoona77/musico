package kahoona.googlecode.com.musico.plugin.spotify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kahoona.googlecode.com.musico.content.ContentItem;
import kahoona.googlecode.com.musico.content.ContentProvider;
import kahoona.googlecode.com.musico.content.Song;

import com.google.code.jspot.Album;
import com.google.code.jspot.Artist;
import com.google.code.jspot.Results;
import com.google.code.jspot.Spotify;
import com.google.code.jspot.Track;

public class SpotifyContentProvider implements ContentProvider {

	private Spotify spotify;
	
	public static final String ID = "kahoona.googlecode.com.musico.plugin.spotify.SpotifyContentProvider";

	@Override
	public void init() throws Exception {
		spotify = new Spotify();
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public List<ContentItem> browse(ContentItem directory) {
		if (directory.isDirectory()) {
			if (directory instanceof kahoona.googlecode.com.musico.content.Artist) {
				return this.searchAlbum("artist:" + directory.getName());
			} else if (directory instanceof kahoona.googlecode.com.musico.content.Album) {
				kahoona.googlecode.com.musico.content.Album album = (kahoona.googlecode.com.musico.content.Album) directory;
				String query = "artist:\"" + album.getArtist().getName() + "\" album:\"" + album.getName();
				return this.searchTrack(query);
			}
		}
		return Collections.emptyList();
	}

	@Override
	public List<ContentItem> searchTrack(String query) {
		List<ContentItem> items = new ArrayList<ContentItem>();
		try {
			Results<Track> results = spotify.searchTrack(query);
			for (Track track : results.getItems()) {
				Song song = new Song();
				song.setId(track.getId());
				song.setPlayerID(SpotifyPlayer.ID);
				song.setName(track.getName());
				song.setAlbum(track.getAlbum().getName());
				song.setArtist(track.getArtistName());
				song.setLength( (int) track.getLength()*1000);
				
				items.add(song);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return items;
	}

	@Override
	public List<ContentItem> searchArtist(String query) {
		List<ContentItem> items = new ArrayList<ContentItem>();
		try {
			Results<Artist> results = spotify.searchArtist(query);
			for (Artist artist : results.getItems()) {
				kahoona.googlecode.com.musico.content.Artist item = new kahoona.googlecode.com.musico.content.Artist();
				item.setId("artist:" + artist.getName());
				item.setName(artist.getName());
				item.setDirectory(true);
				items.add(item);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return items;
	}

	@Override
	public List<ContentItem> searchAlbum(String query) {
		List<ContentItem> items = new ArrayList<ContentItem>();
		try {
			Results<Album> results = spotify.searchAlbum(query);
			for (Album album : results.getItems()) {
				kahoona.googlecode.com.musico.content.Album item = new kahoona.googlecode.com.musico.content.Album();
				item.setId("album:" + album.getName());
				item.setName(album.getName());
				item.setDirectory(true);
				
				kahoona.googlecode.com.musico.content.Artist artist = new kahoona.googlecode.com.musico.content.Artist();
				artist.setId("artist:" + album.getArtistName());
				artist.setName(album.getArtistName());
				artist.setDirectory(true);
				item.setArtist(artist);
				
				items.add(item);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return items;
	}
}
