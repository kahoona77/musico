package kahoona.googlecode.com.musico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kahoona.googlecode.com.musico.content.ContentItem;
import kahoona.googlecode.com.musico.content.ContentProvider;
import kahoona.googlecode.com.musico.content.Song;
import kahoona.googlecode.com.musico.player.PlayQueue;
import kahoona.googlecode.com.musico.player.Player;
import kahoona.googlecode.com.musico.plugin.spotify.SpotifyContentProvider;
import kahoona.googlecode.com.musico.plugin.spotify.SpotifyPlayer;
import de.felixbruns.jotify.api.media.Track;
import de.felixbruns.jotify.api.player.PlaybackAdapter;

public class Controller extends PlaybackAdapter {

	private Player currentPlayer;
	private List<Player> players;
	private List<ContentProvider> contentProvider;
	private PlayQueue playQueue;
	private BrowserList browserList;

	private boolean isPaused = true;
	private boolean isStopped = true;

	public Controller() {
		this.playQueue = new PlayQueue();

		// Load Player
		players = new ArrayList<Player>();
		Player spotiPlayer = new SpotifyPlayer(this);
		try {
			spotiPlayer.init();
			players.add(spotiPlayer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Load Content-Provider
		contentProvider = new ArrayList<ContentProvider>();
		ContentProvider sptiProvider = new SpotifyContentProvider();
		try {
			sptiProvider.init();
			contentProvider.add(sptiProvider);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void play(Song song) {
		// stop current palyer
		if (this.currentPlayer != null) {
			this.currentPlayer.stop();
		}

		// search Palyer for this song
		String playerID = song.getPlayerID();
		for (Player player : this.players) {
			if (playerID.equals(player.getID())) {
				this.currentPlayer = player;
				break;
			}
		}

		// play
		if (currentPlayer != null) {
			try {
				/* Print artist and title. */
				System.out.format("Playing: %s - %s\n", song.getArtist(), song
						.getName());
				this.isPaused = false;
				this.isStopped = false;
				currentPlayer.play(song);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void playPause() {
		if (this.isPaused) {
			this.isPaused = false;
			if (this.isStopped) {
				this.play(this.playQueue.getCurrent());
			} else if (this.currentPlayer != null) {
				this.currentPlayer.play();
			}
		} else if (this.currentPlayer != null) {
			this.isPaused = true;
			this.currentPlayer.pause();
		}
	}

	public void stop() {
		if (this.currentPlayer != null) {
			this.currentPlayer.stop();
			this.isStopped = true;
			this.isPaused = true;
		}
	}

	public void next() {
		this.play(this.playQueue.getNext());
	}

	public void previous() {
		this.play(this.playQueue.getPrevious());
	}

	public void addSong(Song song) {
		int last = this.playQueue.getSize();
		this.playQueue.addSong(song, last);
	}

	public void addContentItems(List<ContentItem> items, int position) {
		// reverse the list to add all in the right order
		Collections.reverse(items);

		for (ContentItem contentItem : items) {
			if (!contentItem.isDirectory() && contentItem instanceof Song) {
				this.playQueue.addSong((Song) contentItem, position);
			}
		}
	}

	public void addContentItem(ContentItem item, int position) {
		if (!item.isDirectory() && item instanceof Song) {
			this.playQueue.addSong((Song) item, position);
		}
	}

	public List<ContentItem> browse(String providerID, ContentItem directory) {
		ContentProvider provider = findProviderByID(providerID);
		if (provider != null) {
			List<ContentItem> list = provider.browse(directory);
			this.setBrowserList(list);
			return list;
		} else {
			// TODO log error
			return null;
		}
	}

	private void setBrowserList(List<ContentItem> list) {
		BrowserList browserList = new BrowserList();
		browserList.setCurrentList(list);
		browserList.setPreviousList(this.browserList);
		this.browserList = browserList;
	}

	public List<ContentItem> getCurrentBrowser() {
		if (this.browserList != null)
			return this.browserList.getCurrentList();
		return Collections.emptyList();
	}

	public List<ContentItem> browseBack() {
		if (this.browserList != null
				&& this.browserList.getPreviousList() != null) {
			this.browserList = this.browserList.getPreviousList();
		}
		return this.getCurrentBrowser();
	}

	public List<ContentItem> searchTrack(String providerID, String query) {
		ContentProvider provider = findProviderByID(providerID);
		if (provider != null) {
			List<ContentItem> list = provider.searchTrack(query);
			this.setBrowserList(list);
			return list;
		} else {
			// TODO log error
			return null;
		}
	}

	public List<ContentItem> searchArtist(String providerID, String query) {
		ContentProvider provider = findProviderByID(providerID);
		if (provider != null) {
			List<ContentItem> list = provider.searchArtist(query);
			this.setBrowserList(list);
			return list;
		} else {
			// TODO log error
			return null;
		}
	}

	public List<ContentItem> searchAlbum(String providerID, String query) {
		ContentProvider provider = findProviderByID(providerID);
		if (provider != null) {
			List<ContentItem> list = provider.searchAlbum(query);
			this.setBrowserList(list);
			return list;
		} else {
			// TODO log error
			return null;
		}
	}

	@Override
	public void playbackFinished(Track track) {
		this.next();
	}

	private ContentProvider findProviderByID(String providerID) {
		for (ContentProvider contentProvider : this.contentProvider) {
			if (contentProvider.getID().equals(providerID)) {
				return contentProvider;
			}
		}
		return null;
	}

	public List<ContentProvider> getContentProvider() {
		return contentProvider;
	}

}
