package javaTeam;

public class LoginVO {
	private int prcode;
	private String id;
	private String pwd;
	private String name;
	private String phonenum;
	private String gender;
	
	public LoginVO(String id,String pwd,String name,String phonenum,String gender) {
		super();	
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.phonenum = phonenum;
		this.gender = gender;
	}
	public LoginVO() {}
	
	public int getPrcode() {
		return prcode;
	}
	public void setPrcode(int prcode) {
		this.prcode = prcode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhonnum() {
		return phonenum;
	}
	public void setPhonnum(String phonnum) {
		this.phonenum = phonnum;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	

}
