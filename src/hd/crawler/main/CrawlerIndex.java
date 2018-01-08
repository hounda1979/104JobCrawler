package hd.crawler.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import hd.crawler.loader.LoaderHtml;
import hd.crawler.resolver.Pageresolver;

public class CrawlerIndex {
	
	static ArrayList<String> allList = new ArrayList<String>(); // 全部網址
	static ArrayList<String> notCrawlurList = new ArrayList<String>(); // 未爬過的網址;
	static ArrayList<String> jobList = new ArrayList<String>(); //未分析的文件;
	private static LoaderHtml lo;
	private static Pageresolver ps;
	public CrawlerIndex(){
		
		lo = new LoaderHtml();
		ps = new Pageresolver();
		int con = this.getNum() / 20;
		for (int a = 0; a < con; a++) {
			String url = "https://www.104.com.tw/jobs/search/?ro=1&jobcat=2007001004&area=6001005000&order=2&asc=0&page="
					+ a + "&mode=s&jobsource=n104bank1";
			// System.out.println(url);
			allList.add(url);
			
		}
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		CrawlerIndex cx = new CrawlerIndex();
	//	fisturl();
		System.out.println("開始爬蟲....................................");
		ArrayList setList = new ArrayList<String>();
		File f = new File("D:/104job/html");
		File [] fl = f.listFiles();
		for(int i = 0 ; i < f.listFiles().length ; i++){
			String temp = fl[i].toString();
			setList.add(temp);
			//System.out.println(fl[i]);
		}
		System.out.println(setList.get(0).toString());
		jobList = setList;
		paperAnalyse();
	}
	private static  void fisturl() {
		if (allList.isEmpty()) {
			System.out.println("第一層下載完成");
			notCrawlurList = ps.getAllIdAddress();
			secondurl(notCrawlurList);
			return;
		}
		String filenamestr = "";
		filenamestr = lo.downLoad(allList.get(0).toString(), "1");
		System.out.println("取得網頁並存入 : " + filenamestr + " OK");
		allList.remove(0);

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(3000);
					fisturl();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

	}
	/**
	 * 第二層網址取得並下載分析
	 * @param allurl 第二層全部的網址
	 */
	private static void secondurl(ArrayList allurl) {

		if (allurl.isEmpty()) {
			System.out.println("第二層下載完成");
			System.out.println("開始分析文件");
			paperAnalyse();
			return;
		}
		String filenamestr = "";
		filenamestr = lo.downLoad(allurl.get(0).toString(), "2");
		System.out.println("取得網頁並存入 : " + filenamestr + " OK");
		jobList.add(filenamestr);
		//ps.getNeedData(filenamestr);
		allurl.remove(0);

		// 當連接網址有問題時
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					Thread.sleep(3000);
					// 等待3秒

					secondurl(allurl);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

	}
	private static void paperAnalyse(){
		if(jobList.isEmpty()){
			return;
			}
		ps.getNeedData(jobList.get(0).toString());
		System.out.println("新增到資料庫成功");
		jobList.remove(0);
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(1000);
					paperAnalyse();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		
	}

	/**
	 * 取得工作總數
	 * 
	 * @return 回傳數量
	 */
	private int getNum() {
		int count = 0;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpost = new HttpPost(
				"http://www.104.com.tw/i/apis/jobsearch.cfm?cat=2007001004&role=1&area=6001005000&fmt=2");
		RequestConfig reqConfig = null;
		reqConfig = RequestConfig.custom().setConnectTimeout(10000) // 设置连接超时
				.setSocketTimeout(10000) // 设置数据获取超时
				.setConnectionRequestTimeout(10000).build();
		httpost.setConfig(reqConfig);
		try {
			CloseableHttpResponse response = httpclient.execute(httpost);

			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				InputStream in = entity.getContent();
				try {
					String line;
					BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
					while ((line = reader.readLine()) != null) {
						count = Integer.valueOf(line);
						// content.append(line + "\n");
					}
				} finally {
					in.close();
				}

			} else {
				System.out.println("F");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return count;
	}
}
