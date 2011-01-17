package kahoona.googlecode.com.musico.content;

import java.util.List;

public interface ContentProvider {

	public void init() throws Exception;
	
	public String getID();
	
	public List<ContentItem> browse(ContentDirectory directory);
	
	public List<ContentItem> searchTrack(String query);
	
	public List<ContentItem> searchArtist(String query);
	
	public List<ContentItem> searchAlbum(String query);
	
	
}
