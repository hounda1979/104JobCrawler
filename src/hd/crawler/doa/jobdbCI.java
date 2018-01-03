package hd.crawler.doa;

import hd.crawler.doa.*;
import hd.crawler.vo.Jobdata;

public class jobdbCI {
	
	
	/**
	 * 把資料加入到資料庫中 
	 * @param jb 
	 * @return
	 */
	public boolean AddJobdataToDB(Jobdata jb){
		boolean b = true;
		String sql ="insert into jobtable values(?,?,?,?,?,?,?,?,?,?)";
		String parameters[] = {null,jb.getConame(),jb.getJobname(),jb.getJobtype(),jb.getAcademic(),jb.getArea(),jb.getSalary(),jb.getSkill(),jb.getJobDescription(),jb.getOther()};
		//SqlHelper.executeUpdate(sql, parameters);
		try{
			SqlHelper.executeUpdate(sql, parameters);
			}catch(Exception e){
				b=false;
			}
			return b;
	}
}
