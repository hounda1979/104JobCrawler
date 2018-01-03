package hd.crawler.vo;

public class Jobdata {
	
	private int Id;
	// ID
	private String coname;
	//公司名稱
	private String academic;
	// 學歷
	private String salary;
	// 薪資
	private String area;
	// 地區
	private String jobname;
	// 職務名稱
	private String skill;
	// 工作技能
	private String jobtype;
	// 工作職務
	private String jobDescription;
	//工作內容
	private String other;
	//其它

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getAcademic() {
		return academic;
	}

	public void setAcademic(String academic) {
		this.academic = academic;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getJobname() {
		return jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getJobtype() {
		return jobtype;
	}

	public void setJobtype(String jobtype) {
		this.jobtype = jobtype;
	}
	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}
	public String getConame() {
		return coname;
	}

	public void setConame(String coname) {
		this.coname = coname;
	}

}
