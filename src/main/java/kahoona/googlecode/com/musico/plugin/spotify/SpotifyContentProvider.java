package kahoona.googlecode.com.musico.plugin.spotify;

import java.io.IOException;
import java.util.List;

import com.google.code.jspot.Results;
import com.google.code.jspot.Spotify;
import com.google.code.jspot.Track;

import kahoona.googlecode.com.musico.content.ContentDirectory;
import kahoona.googlecode.com.musico.content.ContentItem;
import kahoona.googlecode.com.musico.content.ContentProvider;

public class SpotifyContentProvider implements ContentProvider{

	private Spotify spotify;

	@Override
	public void init() throws Exception {
		spotify = new Spotify();
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ContentItem> browse(ContentDirectory directory) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ContentItem> searchTrack(String query) {
		try {
			Results<Track> results = spotify.searchTrack(query);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ContentItem> searchArtist(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ContentItem> searchAlbum(String query) {
		// TODO Auto-generated method stub
		return null;
	}

}
