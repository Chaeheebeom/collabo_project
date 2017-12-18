package javaTeam;

public class LoginVO {
	private int prcode;
	private String id;
	private String pwd;
	private String name;
	private int phonenum;
	private String gender;
	private int age;
	
	public LoginVO(int prcode,String id,String pwd,String name,int phonenum,String gender,int age) {
		super();	
		this.prcode=prcode;
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.phonenum = phonenum;
		this.gender = gender;
		this.age=age;
	}
	public LoginVO(String id,String pwd) {
		super();
		this.id=id;
		this.pwd=pwd;
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
	public int getPhonenum() {
		return phonenum;
	}
	public void setPhonenum(int phonenum) {
		this.phonenum = phonenum;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	
	

}
