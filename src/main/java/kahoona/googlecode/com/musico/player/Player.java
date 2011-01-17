package kahoona.googlecode.com.musico.player;

import de.felixbruns.jotify.api.player.PlaybackListener;


public abstract class Player {
	
	private PlaybackListener playbackListner;

	public Player(PlaybackListener playbackListener) {
		this.playbackListner = playbackListener;
	}

	public abstract void init() throws Exception;
	
	public abstract void play(Song song) throws Exception;
	
	public abstract void pause();
	
	public abstract void stop();
	
	public abstract String getID();

	public PlaybackListener getPlaybackListner() {
		return playbackListner;
	}

	public void setPlaybackListner(PlaybackListener playbackListner) {
		this.playbackListner = playbackListner;
	}
}
