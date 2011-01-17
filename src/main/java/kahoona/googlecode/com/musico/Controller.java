package kahoona.googlecode.com.musico;

import java.util.ArrayList;
import java.util.List;

import de.felixbruns.jotify.api.media.Track;
import de.felixbruns.jotify.api.player.PlaybackAdapter;

import kahoona.googlecode.com.musico.player.PlayQueue;
import kahoona.googlecode.com.musico.player.Player;
import kahoona.googlecode.com.musico.player.Song;
import kahoona.googlecode.com.musico.player.spotify.SpotifyPlayer;

public class Controller extends PlaybackAdapter {

	private Player currentPlayer;
	private List<Player> players;
	private PlayQueue playQueue;

	public Controller() {
		this.playQueue = new PlayQueue();

		players = new ArrayList<Player>();

		Player spotiPlayer = new SpotifyPlayer(this);
		try {
			spotiPlayer.init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		players.add(spotiPlayer);
	}

	private void play(Song song) {
		//stop current palyer
		if(this.currentPlayer != null){
			this.currentPlayer.stop();
		}
		
		//search Palyer for this song
		String playerID = song.getPlayerID();
		for (Player player : this.players) {
			if (playerID.equals(player.getID())) {
				this.currentPlayer = player;
				break;
			}
		}

		//play
		if (currentPlayer != null) {
			try {
				/* Print artist and title. */
				System.out.format("Playing: %s - %s\n", song.getArtist(), song.getName());
				
				currentPlayer.play(song);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void playNext() {
		this.play(this.playQueue.getNext());
	}

	public void addSong(Song song) {
		int last = this.playQueue.getSize();
		this.playQueue.addSong(song, last);
	}

	@Override
	public void playbackStopped(Track track) {
		System.out.println("stopped");
		this.playNext();
	}
	
	@Override
	public void playbackFinished(Track track) {
		System.out.println("finished");
		this.playNext();
	}

}
