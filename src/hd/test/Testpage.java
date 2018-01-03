package hd.test;

/**
 * 卡關
 * 問題1: 用Thread 等3秒去重跑是因為.當要去連接分析出來網址時會不定時有網址無法連線.
 * 		     所以等3秒讓它重跑.(測試了OK)
 * 問題2: 但有些網址的求職資料無效了,所以程式跑到這網址會卡住,所以我想記錄它等它過4次就砍掉那網址.
 *        但好像不能實現.(取消了 把第一層的網頁重下載就沒這問題)
 *        
 *  現在問題是因為停3秒才執行 單線程很慢 網址數一多就花時間.
 */

import java.util.ArrayList;

import hd.crawler.doa.jobdbCI;
import hd.crawler.loader.LoaderHtml;
import hd.crawler.resolver.Pageresolver;
import hd.crawler.vo.Jobdata;

public class Testpage {
	Jobdata jb = new Jobdata();
		
	int count = 0;
	public static void main(String[] args) {
		
		ArrayList setList = new ArrayList<String>();
		// TODO Auto-generated method stub
		Pageresolver pe = new Pageresolver();
		setList =  pe.getAllIdAddress(); //取得第二層的html ipaddress
		Testpage tp = new Testpage();
		tp.dd(setList);
		for(int i = 0 ; i < setList.size() ; i++){
			System.out.println(setList.get(i).toString());
		}
		
	}
	
	
	private void dd(ArrayList filename){
		if(filename.isEmpty()){
			return ;
		}
	/*	if(count == 4){
			//問題2
			System.out.println(count);
			firename.remove(0); //計數4次移除這網址
			count = 0;
			dd(firename);
		
		
		}*/
		
		Pageresolver pe = new Pageresolver();
		LoaderHtml Ll = new LoaderHtml();
		String filenamestr="";
		filenamestr = Ll.downLoad(filename.get(0).toString(), "2");
		System.out.println("取得網頁並存入 : "+filenamestr+" OK");
		pe.getNeedData(filenamestr);
		filename.remove(0);
		//dd(firename);
		
			//當連接網址有問題時
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					//問題1:
					try {
						Thread.sleep(3000); 
						//等待3秒
						
						//記錄次數
						dd(filename);
						
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
	    	//return;
		}
	/*else{
			System.out.println("取得網頁並存入 : "+firenamestr+" OK");
			pe.getNeedData(firenamestr);
			firename.remove(0);
			dd(firename);
		}*/
	
}
