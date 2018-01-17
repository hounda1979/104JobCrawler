package hd.crawler.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import hd.crawler.loader.LoaderHtml;
import hd.crawler.model.jobModel;
import hd.crawler.resolver.Pageresolver;

public class CrawlerIndex {

	static ArrayList<String> allList = new ArrayList<String>(); // 全部網址
	static ArrayList<String> notCrawlurList = new ArrayList<String>(); // 未爬過的網址;
	static ArrayList<String> jobList = new ArrayList<String>(); // 未分析的文件;
	static HashMap<String, Integer> eroList = new HashMap<>();
	static int count = 0;
	private static LoaderHtml lo;
	private static Pageresolver ps;
	static long startTime;
	static Cltxt cl = new Cltxt();
	public CrawlerIndex() {
		jobModel jm = new jobModel();
		try {
			if(jm.delTable("jobtable")){
				System.out.println("DEL OK");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(jm.CreatTable("jobtable")){
			System.out.println("OK");
		}
				
		lo = new LoaderHtml();
		ps = new Pageresolver();
		int con = this.getNum() / 20;
		for (int a = 0; a < con; a++) {
			String url = "https://www.104.com.tw/jobs/search/?ro=1&jobcat="+cl.getCat()+"&area="+cl.getArea()+"&order=2&asc=0&page="
					+ a + "&mode=s&jobsource=n104bank1";
			// System.out.println(url);
			allList.add(url);

		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		CrawlerIndex cx = new CrawlerIndex();
		
		System.out.println("開始爬蟲....................................");
		startTime = System.currentTimeMillis();
		notCrawlurList = ps.getAllIdAddress();
		 fisturl();
		
	}

	private static void fisturl() {
		if (allList.isEmpty()) {
			long endTime = System.currentTimeMillis();
			System.out.println("第一層下載完成");
			System.out.println("總共花費了 :" + (endTime - startTime) / 1000 + " 秒");
			notCrawlurList = ps.getAllIdAddress();
			secondurl();
			return;
		}
		String allisttostr = allList.get(0).toString();
		if (! (eroList.get(allisttostr)== null)) {
			if (eroList.get(allisttostr) < 2) {

				count = eroList.get(allisttostr);
				count++;
				eroList.put(allisttostr, count);// 計入錯誤
			} else {
				
				allList.remove(0);
				eroList.remove(allisttostr);
			}
		}
		String filenamestr = "";
		filenamestr = lo.downLoad(allisttostr, "1" ,cl);
		if (filenamestr.equals("f")) {
			allList.add(allisttostr);// 重新加入下載列中
			if(eroList.get(allisttostr)==null ||eroList.get(allisttostr)!= 1 ||eroList.get(allisttostr) !=2 || eroList.get(allisttostr)!=3){
			eroList.put(allisttostr, count);// 計入錯誤
			}
			allList.remove(0);// 移除這次下載網址
		} else {
			System.out.println("取得網頁並存入 : " + filenamestr + " OK");
			allList.remove(0);
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(800);
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
	 * 
	 * @param allurl
	 *            第二層全部的網址
	 */
	private static synchronized void secondurl() {

		if (notCrawlurList.isEmpty()) {
			long endTime = System.currentTimeMillis();
			System.out.println("第二層下載完成");
			System.out.println("總共花費了 :" + (endTime - startTime) / 1000 + " 秒");
			System.out.println("開始分析文件");
			paperAnalyse();
			return;
		}
		String allisttostr = notCrawlurList.get(0).toString();

		if (!(eroList.get(allisttostr) == null)) {
			//int x = eroList.get(allisttostr);
			if (eroList.get(allisttostr) < 2) {
				count = eroList.get(allisttostr);
				System.out.println(count);
				count++;
				System.out.println("count++ : "+count);
				eroList.put(allisttostr, count);
				
			} else {

				notCrawlurList.remove(0);
				eroList.remove(allisttostr);
				
			}
		}
		String filenamestr = "";
		filenamestr = lo.downLoad(allisttostr, "2",cl);
		if (filenamestr.equals("f")) {
			notCrawlurList.add(allisttostr);// 重新加入下載列中
			// System.out.println(notCrawlurList.get(1).toString());
			if(eroList.get(allisttostr)==null ||eroList.get(allisttostr)!= 1 ||eroList.get(allisttostr) !=2 || eroList.get(allisttostr)!=3 ){
			eroList.put(allisttostr, count);// 計入錯誤
			}
			notCrawlurList.remove(0);// 移除這次下載網址
		} else {
			System.out.println("取得網頁並存入 : " + filenamestr + " OK");
			jobList.add(filenamestr);
			// ps.getNeedData(filenamestr);
			notCrawlurList.remove(0);
		}

		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					Thread.sleep(1000);
					// 等待3秒

					secondurl();

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

	}

	private static void paperAnalyse() {
		if (jobList.isEmpty()) {
			long endTime = System.currentTimeMillis();
			System.out.println("總共花費了 :" + (endTime - startTime) / 1000 + " 秒");
			dbTocsv tocsv = new dbTocsv(cl);
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
				"http://www.104.com.tw/i/apis/jobsearch.cfm?cat="+cl.getCat()+"&role=1&area="+cl.getArea()+"&fmt=2");
		RequestConfig reqConfig = null;
		reqConfig = RequestConfig.custom().setConnectTimeout(9000) // 设置连接超时
				.setSocketTimeout(9000) // 设置数据获取超时
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
