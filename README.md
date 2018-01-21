#      104JobCrawler

###    1 : 功能說明:
        1.1 :  完成查詢 地區/ 職務查詢.
        1.2 : 使用Jsoup分析網頁 取出 " 公司名稱 " , " 職務名稱 " , " 學歷 " , " 薪資 " ,  
        " 工作技能 " ," 工作內容 " , " 其他條件 ".   
        1.3 : 改善工作技能統計時 "C" 語言資料重覆.    
        1.4 : 改善下載網頁速度.    
        1.5 : 控制下載網頁失敗的次數,超過3次則放棄. 
		
###	2 : 設定說明 :
		2.1 : Urlinfo.txt  相關變數,路徑的設定.	
		2.2 : catAndarea.txt   職務 & 地區 變數查詢.	
		2.3 : typeset.txt 設定找尋工作技能的關鍵字.	
		2.4 : 104job.db (SQLite3)存放爬回來後工作的相關資料.	
		2.5 : CrawlerIndex 為主要執行檔(主程式進入點,在編譯那會用到).	
			
###	3: 編譯步驟 :	
		使用Eclipse製作可執行Jar  
		
		3.1 : 點擊專案並按下滑鼠右鍵 > 選擇Export
![image](https://github.com/hounda1979/104JobCrawler/blob/master/image_5.png)  

		3.2 : 選擇 Runnable JAR file
![image](https://github.com/hounda1979/104JobCrawler/blob/master/image_6.png)  
    
		3.3 : Launch configuration 設定程式進入點(Main), Export destination 設定匯出 JAR file 路徑(包含檔名), 點選 Finish 後即可自動匯出並自動設定 MANIFEST.MF
![image](https://github.com/hounda1979/104JobCrawler/blob/master/image_7.png)  
    
		

### 4: 使用說明 :   
		4.1 : 檢查 Urlinfo.txt , typeset.txt ,104job.db 和 JAR file 是否在同一目錄下.
		4.2 : 直到點擊二下即可執行.
		4.3 : 如不能執行.使用Command line(命令提示字元) > 進入 JAR file 目錄  > 執行 java -jar 匯出 JAR file的 檔名.jar 


