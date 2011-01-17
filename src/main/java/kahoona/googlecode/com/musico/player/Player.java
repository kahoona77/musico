package kahoona.googlecode.com.musico.player;


public interface Player {
	
	public void init() throws Exception;
	
	public void play(Song song) throws Exception;
	
	public void pause();
	
	public void stop();
	
	public String getID();
}
