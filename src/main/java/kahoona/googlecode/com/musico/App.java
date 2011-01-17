package kahoona.googlecode.com.musico;

import java.io.IOException;

import kahoona.googlecode.com.musico.player.Song;
import kahoona.googlecode.com.musico.plugin.spotify.SpotifyPlayer;

import org.tritonus.share.sampled.file.THeaderlessAudioFileWriter;

import com.google.code.jspot.Results;
import com.google.code.jspot.Spotify;
import com.google.code.jspot.Track;

import de.felixbruns.jotify.api.JotifyConnection;
import de.felixbruns.jotify.api.exceptions.AuthenticationException;
import de.felixbruns.jotify.api.exceptions.ConnectionException;
import de.felixbruns.jotify.api.media.File;
import de.felixbruns.jotify.api.player.PlaybackAdapter;

/**
 * Hello world!
 * 
 */
public class App {	
	public static void main(String[] args) {
		System.out.println("Hello Musico!");
		
		Controller controller = new Controller();

		Spotify spotify;
		try {
			spotify = new Spotify();

			Results<Track> results = spotify.searchTrack("artist:weezer");
			for (Track track : results.getItems()) {
				System.out.printf("%.2f %s // %s // %s // %s // %s\n", track.getPopularity(), track.getArtistName(), track
						.getAlbum().getName(), track.getName(), track.getId(), track.getLength());
				
				Song song = new Song();
				song.setId(track.getId());
				song.setPlayerID(SpotifyPlayer.ID);
				song.setName(track.getName());
				song.setAlbum(track.getAlbum().getName());
				song.setArtist(track.getArtistName());
				song.setLength( (int) track.getLength()*1000);
				controller.addSong(song);
			}
			
			controller.playNext();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
