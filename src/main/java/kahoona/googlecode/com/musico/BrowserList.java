package kahoona.googlecode.com.musico;

import java.util.List;

import kahoona.googlecode.com.musico.content.ContentItem;

public class BrowserList {
	private List<ContentItem> currentList;
	private BrowserList previousList;

	public List<ContentItem> getCurrentList() {
		return currentList;
	}

	public void setCurrentList(List<ContentItem> currentList) {
		this.currentList = currentList;
	}

	public BrowserList getPreviousList() {
		return previousList;
	}

	public void setPreviousList(BrowserList previousList) {
		this.previousList = previousList;
	}
}
