package kahoona.googlecode.com.musico;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import kahoona.googlecode.com.musico.content.Album;
import kahoona.googlecode.com.musico.content.Artist;
import kahoona.googlecode.com.musico.content.ContentItem;
import kahoona.googlecode.com.musico.content.ContentProvider;
import kahoona.googlecode.com.musico.content.Song;
import kahoona.googlecode.com.musico.plugin.spotify.SpotifyContentProvider;

import org.apache.commons.lang.StringUtils;

/**
 * Hello world!
 * 
 */
public class App {	
	public static void main(String[] args) {
		System.out.println("Hello Musico!");
		
		Controller controller = new Controller();
		
		List<ContentItem> list = new ArrayList<ContentItem>();
		
		String providerID = SpotifyContentProvider.ID;

		
		Scanner scanner = new Scanner(System.in);
		while(true){
			String command = scanner.nextLine();
			if(command.equals("next")){
				controller.next();
			}
			else if(command.equals("prev")){
				controller.previous();
			}
			else if(command.equals("pause")){
				controller.playPause();
			}
			else if(command.equals("play")){
				controller.playPause();
			}
			else if(command.equals("stop")){
				controller.stop();
			}
			else if(command.equals("searchTrack")){
				System.out.println("Please enter query:");
				String query = scanner.nextLine();
				List<ContentItem> tracks = controller.searchTrack(providerID,query);
				printItems(tracks);
				list.clear();
				list.addAll(tracks);		
			}
			
			else if(command.equals("searchAlbum")){
				System.out.println("Please enter query:");
				String query = scanner.nextLine();
				List<ContentItem> tracks = controller.searchAlbum(providerID, query);
				printItems(tracks);
				list.clear();
				list.addAll(tracks);
			}
			else if(command.equals("searchArtist")){
				System.out.println("Please enter query:");
				String query = scanner.nextLine();
				List<ContentItem> tracks = controller.searchArtist(providerID, query);
				printItems(tracks);
				list.clear();
				list.addAll(tracks);
			}
			
			else if (command.equals("browse")) {
				System.out.println("Please enter index:");
				String index = scanner.nextLine();
				if (StringUtils.isNumeric(index)) {
					int i = Integer.parseInt(index);
					ContentItem item = list.get(i);
					List<ContentItem> tracks = controller.browse(providerID, item);
					
					printItems(tracks);
					
					list.clear();
					list.addAll(tracks);
				}
			}
			
			else if (command.equals("addIndex")) {
				System.out.println("Please enter index:");
				String index = scanner.nextLine();
				if (StringUtils.isNumeric(index)) {
					int i = Integer.parseInt(index);
					Song item = (Song) list.get(i);
					controller.stop();
					controller.addSong(item);
					controller.playPause();
				}
			}
			
			else if (command.equals("back")) {
				List<ContentItem> items = controller.browseBack();
				printItems(items);
				
				list.clear();
				list.addAll(items);
			}
			
			else if(command.equals("addAll")){
				controller.addContentItems(list, 0);
			}
			
			else if(command.equals("printList")){
				controller.addContentItems(list, 0);
				printItems(list);
			}
			
		}
	}

	private static void printItems(List<ContentItem> tracks) {
		int i = 0;
		for (ContentItem contentItem : tracks) {
			if(contentItem instanceof Album){
				System.out.println(" (" + i + ") Album: " + contentItem.getName());
			}
			
			else if(contentItem instanceof Artist){
				System.out.println(" (" + i + ") Artist: " + contentItem.getName());
			}
			else if(contentItem instanceof Song){
				Song song = (Song) contentItem;
				System.out.println(" (" + i + ") Song: " + song.getArtist() + " - " + song.getName());
			}
			i++;
		}
	}
}
