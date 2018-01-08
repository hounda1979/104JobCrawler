package hd.crawler.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import hd.crawler.main.Cltxt;



/**
 * Html 下載器,用于下載網頁原碼,儲存為本地端的html檔案.
 * @author HoundaChen
 *
 */
public class LoaderHtml {
	Cltxt cl = new Cltxt();
	/**
	 * 
	 * @param url 下載目標的網址
	 * @param depth 判斷下載那一層網頁
	 * @return fileName 回傳儲存的檔案名
	 */
	public String downLoad(String url ,String depth){
		String fileName = null; //页面下载后保存的文件名
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpost = new HttpPost(url);
		RequestConfig reqConfig = null;
		reqConfig = RequestConfig.custom()
				.setConnectTimeout(10000) //设置连接超时
				.setSocketTimeout(10000) //设置数据获取超时
				.setConnectionRequestTimeout(10000)
				.build();
		httpost.setConfig(reqConfig);
		httpost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
		httpost.setHeader("Host", "idma.104.com.tw");
		httpost.setHeader("Connection", "keep-alive");
		httpost.setHeader("Content-Type","application/x-javascript; charset=utf-8");
		httpost.setHeader("Cookie","_ga=GA1.3.1322469430.1514112802;"
				+ " __auc=fe4a8a3e1608828162f1a7bbbfa; "
				+ "_gaexp=GAX1.3.vCo_medgQLiOEryyaNMYbw.17565.0;"
				+ " __utma=6235315.1322469430.1514112802.1514202541.1514465768.4;"
				+ " __utmz=6235315.1514465768.4.4.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided);"
				+ " __asc=6792d342160a7c44b9f899097a1; _gid=GA1.3.887251454.1514643148");
		try{
			CloseableHttpResponse response = httpclient.execute(httpost);
			
			
			if(response.getStatusLine().getStatusCode() == 200){
				
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
				String saveUrl = cl.getSaveUrl();
				OutputStreamWriter output = null;
				Timestamp ts = new Timestamp(System.currentTimeMillis()); //时间戳
				DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String fileNumber = df.format(ts) + (int)(Math.random() * 900 + 100); //通过时间戳命名文件
				
				if(depth.equals("1")){
				fileName = saveUrl + fileNumber + ".html";
				}else{
					fileName = cl.getLocalhtml() + fileNumber + ".html";
				}
				
				output = new OutputStreamWriter(
					new FileOutputStream(fileName), "UTF-8");
				output.write(content.toString());
				output.close();
				
				
			}else{
				System.out.println("F : "+url);
				fileName = "f";
				}
		
	
	
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
				
		
		return fileName;
	}
}
