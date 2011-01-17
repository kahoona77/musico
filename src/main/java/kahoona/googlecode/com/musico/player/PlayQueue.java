package kahoona.googlecode.com.musico.player;

import java.util.ArrayList;
import java.util.List;

import kahoona.googlecode.com.musico.content.Song;


public class PlayQueue {
	private List<Song> queue = new ArrayList<Song>();
	private int position = 0;

	public void addSong(Song song, int position) {
		if (position > queue.size()) {
			position = queue.size();
		}
		queue.add(position, song);
	}
	
	public Song getCurrent(){
		return queue.get(position);
	}
	
	public Song getNext(){
		position++;
		if (position >= queue.size()) {
			position = queue.size() -1;
		}
		return queue.get(position);
	}
	
	public Song getPrevious(){
		position--;
		if(position < 0){
			position = 0;
		}
		return queue.get(position);
	}

	public int getSize() {
		return queue.size();
	}
}
