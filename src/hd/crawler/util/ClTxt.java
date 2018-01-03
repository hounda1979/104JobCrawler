package hd.crawler.util;

public class ClTxt {
	
	private String saveUrl; //存放的路徑
	private String LocalHtml ;//讀取的路徑
	
	public String getSaveUrl() {
		return saveUrl;
	}
	public String getLocalHtml() {
		return LocalHtml;
	}
	public ClTxt(){
		getTxtData();
	}
	private void getTxtData(){
		this.saveUrl = "tetst/teml/";
	}
	
	
	

}
