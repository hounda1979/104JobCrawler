package hd.crawler.resolver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import hd.crawler.doa.jobdbCI;
import hd.crawler.main.Cltxt;
import hd.crawler.vo.Jobdata;

public class Pageresolver {
private static ArrayList<String> setList;
Jobdata JD;



/**
 * 取得所有要下載的網址
 * @return 回傳網址
 */
	public ArrayList<String> getAllIdAddress(Cltxt cl){
		ArrayList<String> temp = new ArrayList<String>();
		setList =  this.getFileName(cl);
		String[] address = new String[setList.size()];
		setList.toArray(address);
		for(int i = 0 ; i < address.length ; i++){
			setList = new ArrayList<String>();
			setList = (ArrayList<String>) this.getidaddress(address[i]);
			String[] array = new String[setList.size()];
			setList.toArray(array);
			for(int x = 0 ; x < array.length ; x++){
				temp.add(array[x]);
			}
		}
		
		return temp;
	}
	
	public Jobdata getNeedData(String filename){
		
		JD = new Jobdata();
		if(filename.equals("f")){
			return null;
		}
		File input = new File(filename);
		try{
		Document doc = Jsoup.parse(input,"UTF-8");
		
		//Elements jobcate = doc.select("dd.cate > span");
		//String cate = jobcate.text();
		Elements jobinfo = doc.select("div.content");
		//JD.setSkill(jobinfo.get(0).select("dt").get(0).toString());
		JD.setJobDescription(jobinfo.get(0).select("p").text());//工作內容
		System.out.println("工作內容 : "+JD.getJobDescription()); 
		JD.setJobtype(jobinfo.get(0).select("dd.cate > span").text());//工作職務
		System.out.println("工作職務 : "+JD.getJobtype());
		JD.setSalary(jobinfo.get(0).select("dd").get(1).text());//薪資;
		System.out.println("工作待遇 : "+ JD.getSalary());
		JD.setArea(jobinfo.get(0).select("dd.addr").text());//工作地區;
		System.out.println("工作地區 : "+ JD.getArea());
		JD.setAcademic(jobinfo.get(1).select("dd").get(2).text());//學歷
		System.out.println("學歷要求 : "+JD.getAcademic());
		JD.setSkill(jobinfo.get(1).select("dd.tool").text());//工作技能
		System.out.println("工作技能 : "+JD.getSkill());
		JD.setOther(jobinfo.get(1).select("dd").get(7).text());//其它
		System.out.println("其它條件 : "+JD.getOther());
		Elements jobheader = doc.select("article.main > header");
		String temp = jobheader.toString().trim();
		Pattern p1 = Pattern.compile("<h1>.+</h1>");
		Matcher m1 = p1.matcher(temp);
		while(m1.find()){
			String jop = m1.group();
			jop = jop.substring(jop.indexOf("<h1>"));
			jop = jop.substring(5);
			jop = jop.substring(0,jop.indexOf("<"));
			JD.setJobname(jop);
			
		}
		Pattern p2 = Pattern.compile("<a .*href=.+</a>");
		Matcher m2 = p1.matcher(temp);
		while(m2.find()){
			String jop1 = m2.group();
			jop1 = jop1.substring(jop1.indexOf("\"cn\">"));
			jop1 = jop1.substring(5);
			jop1 = jop1.substring(0,jop1.indexOf("<"));
			JD.setConame(jop1);			
		}
		
		System.out.println("職務名稱 : "+JD.getJobname());
		System.out.println("公司名稱 : "+JD.getConame());
		jobdbCI jc = new jobdbCI();
		//新增資料到資料庫中 
		jc.AddJobdataToDB(JD);
		
		
		
		
		
		//System.out.println(JD.getSkill());
		
		
		
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return JD;
	}
	 
	/**
	 * 取得網址
	 * @param filename 檔案路徑及檔案名
	 * @return 回傳網址
	 */
	private List<String>getidaddress(String filename){
		
		setList = new ArrayList<String>();
		File input = new File(filename);
		try {
			Document doc = Jsoup.parse(input,"UTF-8");
			Elements ipaddress = doc.select("div.b-block__left > h2");
			//System.out.println(ipaddress);
			Pattern p = Pattern.compile("<a .*href=.+</a>");
			for(int i = 0 ; i < ipaddress.size() ; i++){
				String temp = ipaddress.get(i).toString();
				Matcher m = p.matcher(temp);
				while(m.find()){
					String href = m.group();
					//找到超連結地址並擷取字串
					//有無引號
					href =href.substring(href.indexOf("href="));
					if(href.charAt(5)=='\"'){
						href="https://"+href.substring(8);
						//System.out.println("substring(8)"+href);
					}else{
						href="https://"+href.substring(5);
						//System.out.println("substring(0)"+href);
					}
					//擷取到引號或空格或者到">"結束
					try{
						href=href.substring(0, href.indexOf("\""));
					}catch(Exception e){
						try{
							href=href.substring(0,href.indexOf(" "));
						}catch(Exception e1){
							href=href.substring(0,href.indexOf(">"));
						}
					}
					setList.add(href);
					//System.out.println(href);
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return setList;
	}
	/**
	 * 取得本地端檔案名
	 * @return
	 */
	private ArrayList<String> getFileName(Cltxt cl){
		setList = new ArrayList<String>();
		File f = new File(cl.getSaveUrl());
		File [] fl = f.listFiles();
		for(int i = 0 ; i < f.listFiles().length ; i++){
			String temp = fl[i].toString();
			setList.add(temp);
			//System.out.println(fl[i]);
		}
		return setList;
	}
	

}
