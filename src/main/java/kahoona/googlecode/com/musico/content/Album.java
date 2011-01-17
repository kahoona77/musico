package kahoona.googlecode.com.musico.content;

public class Album extends ContentItem{
	private Artist artist;

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}
}
