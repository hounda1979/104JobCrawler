package hd.test;
/**
 * 第一層網址的下載
 */
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;



public class testCrawer {
	
	private int getNum(){
		int count = 0;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpost = new HttpPost("http://www.104.com.tw/i/apis/jobsearch.cfm?cat=2007001004&role=1&area=6001005000&fmt=2");
		RequestConfig reqConfig = null;
		reqConfig = RequestConfig.custom()
				.setConnectTimeout(10000) //设置连接超时
				.setSocketTimeout(10000) //设置数据获取超时
				.setConnectionRequestTimeout(10000)
				.build();
		httpost.setConfig(reqConfig);
		try{
			CloseableHttpResponse response = httpclient.execute(httpost);
			
			
			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity entity = response.getEntity();
				InputStream in = entity.getContent();
				try
				{
					String line;
					BufferedReader reader = new BufferedReader
							(new InputStreamReader(in, "UTF-8"));
					while((line = reader.readLine()) != null)
					{
						count = Integer.valueOf(line);
						//content.append(line + "\n");
					}							
				}
				finally
				{
					in.close();
				}
				
				
			}else{
				System.out.println("F");
			}
		
	
	
		}catch(Exception e){
			e.printStackTrace();
		}
	
		return count;
	}
		
		
	public void post(){
		ArrayList<Integer> list = new ArrayList();
		int Num = getNum();  //取得總數;
		Num = Num/20; //取得頁數
		System.out.println(Num);
		
		for(int i = 0 ; i< Num ; i++){
			int x = i;
				//當下載失敗
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							Thread.sleep(3000);
							getpage(x);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
			
		}
		
			
	}
	private boolean getpage(int page){
		boolean b = true;
		ArrayList list = new ArrayList();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpost = new HttpPost("https://www.104.com.tw/jobs/search/?ro=1&jobcat=2007001004&area=6001005000&order=2&asc=0&page="+page+"&mode=s&jobsource=n104bank1");
		//HttpPost httpost = new HttpPost("http://www.104.com.tw/i/apis/jobsearch.cfm?ro=1&cat=2007001004&area=6001005000&fmt=4&cols=SAL_MONTH_LOW,JOB,NAME,OTHERS,JOBSKILL_ALL_DESC,PCSKILL_ALL_DESC");
		RequestConfig reqConfig = null;
		reqConfig = RequestConfig.custom()
				.setConnectTimeout(10000) //设置连接超时
				.setSocketTimeout(10000) //设置数据获取超时
				.setConnectionRequestTimeout(10000)
				.build();
		httpost.setConfig(reqConfig);
		try{
			CloseableHttpResponse response = httpclient.execute(httpost);
			
			
			if(response.getStatusLine().getStatusCode() == 200){
				System.out.println("下載第 "+page+" 完成");
				HttpEntity entity = response.getEntity();
				StringBuilder content = new StringBuilder();
				InputStream in = entity.getContent();
				try
				{
					String line;
					BufferedReader reader = new BufferedReader
							(new InputStreamReader(in, "UTF-8"));
					while((line = reader.readLine()) != null)
					{
						content.append(line + "\n");
					}							
				}
				finally
				{
					in.close();
				}
				String saveUrl = "D:/104dbhtml/";
				OutputStreamWriter output = null;
				Timestamp ts = new Timestamp(System.currentTimeMillis()); //时间戳
				DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String fileNumber = df.format(ts) + (int)(Math.random() * 900 + 100); //通过时间戳命名文件
				String fileName = null; //页面下载后保存的文件名
				fileName = saveUrl+"html/" + fileNumber + ".html";
				File file = new File(saveUrl+"html");
				if(!file.exists())
				{
					file.mkdir();
				}
				output = new OutputStreamWriter(
					new FileOutputStream(fileName), "UTF-8");
				output.write(content.toString());
				output.close();
				
				
			}else{
				System.out.println("F");
				b = false;
			}
		
	
	
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return b;
	}
	
}
