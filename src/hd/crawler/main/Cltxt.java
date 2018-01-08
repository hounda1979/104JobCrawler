package hd.crawler.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Cltxt {
	private String saveUrl; //存放路徑
	private String Localhtml; //本地端路徑
	public String getSaveUrl() {
		return saveUrl;
	}
	
	public String getLocalhtml() {
		return Localhtml;
	}
	
	public Cltxt(){
		getString();
	}
	private void getString(){
		try {
			FileReader fr = new FileReader("Urlinfo.txt");
			BufferedReader br = new BufferedReader(fr);
			
			try {
				while(br.ready()){
					String str = br.readLine();
					String[] strsplit = str.split(",");
					//System.out.println(strsplit[1]);
					if(strsplit[0].equals("SaveUrl")){
						saveUrl =strsplit[1];
						System.out.println(strsplit[1]);
					}
					if(strsplit[0].equals("Localhtml")){
						Localhtml = strsplit[1];
						//System.out.println(strsplit[1]);
						
					}
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(this.getLocalhtml());
		File f = new File(Localhtml);
		
		if(f.exists()){
			System.out.println("找到Localhtml的文件夾不需生成");
		}else{
			boolean f_true = f.mkdirs(); 
			if(f_true){
			 System.out.println("文件夾生成OK");	
			}else{
				System.out.println("文件夾生成失敗");
				return;
			}
		
		}
	
		
	}
	
	
	
}
