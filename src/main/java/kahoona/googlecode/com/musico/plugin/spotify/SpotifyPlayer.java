package kahoona.googlecode.com.musico.plugin.spotify;

import kahoona.googlecode.com.musico.player.Player;
import kahoona.googlecode.com.musico.player.Song;
import de.felixbruns.jotify.api.JotifyConnection;
import de.felixbruns.jotify.api.media.File;
import de.felixbruns.jotify.api.media.Track;
import de.felixbruns.jotify.api.player.PlaybackListener;

public class SpotifyPlayer extends Player {
	
	public static final String ID = "kahoona.googlecode.com.musico.player.spotify.SpotifyPlayer";

	private JotifyConnection jotify = new JotifyConnection();

	public SpotifyPlayer(PlaybackListener playbackListener) {
		super(playbackListener);
	}

	@Override
	public void init() throws Exception {
		jotify.login("kahoona77", "5676ebeb");

		System.out.println("SpotifyPlayer: Logged in!");
	}

	@Override
	public void pause() {
		jotify.pause();
	}

	@Override
	public void play(Song song) throws Exception {
		/* Stop if something is already playing. */
		jotify.stop();

		/* Load metadata (files etc.) for track. */
		Track track = jotify.browseTrack(song.getId());

		if (track == null) {
			throw new Exception("Browsing track failed!");
		}

		/* Start playing */
		jotify.play(track, File.BITRATE_160, this.getPlaybackListner());
	}

	@Override
	public void stop() {
		jotify.stop();
	}

	@Override
	public String getID() {
		return ID;
	}
}
