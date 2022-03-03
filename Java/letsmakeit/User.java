package letsmakeit;

public class User {
	private int num;//�û���ţ�����Ψһ��
	private String name;//�û��ǳ�
	private String pswd;//�û����룬���ⲻ�ɼ����ǿɶ�д
	private String sex;//�û��Ա�
	private int age;//�û�����
	private String simpleinfo;//�û����
	
	public User(int num,String name,String pswd,String sex,int age,String simpleinfo)
	{
		this.num = num;
		this.name=  name;
		this.pswd = pswd;
		this.sex = sex;
		this.age = age;
		this.simpleinfo = simpleinfo;
	}
	
	public User(int num,String name,String pswd,String sex,int age)
	{
		this.num = num;
		this.name=  name;
		this.pswd = pswd;
		this.sex = sex;
		this.age = age;
		this.simpleinfo = "";
	}
	
	public User(){
		num = -1;
		name = "";
		pswd = "";
		sex = "";
		age = -1;
		simpleinfo = "";
	}
	
	public int getNum() {
		return num;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + num;
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime * result + ((simpleinfo == null) ? 0 : simpleinfo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (age != other.age)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (num != other.num)
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		if (simpleinfo == null) {
			if (other.simpleinfo != null)
				return false;
		} else if (!simpleinfo.equals(other.simpleinfo))
			return false;
		return true;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPswd() {
		return pswd;
	}
	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getSimpleinfo() {
		return simpleinfo;
	}
	public void setSimpleinfo(String simpleinfo) {
		this.simpleinfo = simpleinfo;
	}
	
	
	public void showinfo(){
		System.out.println("num:"+num);
		System.out.println("name:"+name);
		System.out.println("sex:"+sex);
		System.out.println("age:"+age);
		System.out.println("simpleinfo:"+simpleinfo);
	}
	public String getinfo(){
		return "num:"+num+"\tname:"+name+"\tsex:"+sex+"\tage:"+age+"\tsimpleinfo:"+simpleinfo;//+"\n";
	}
//	public static void main(String[] args) {
//		User user1=new User(2000,"����","123123","��",20,"����˺�����ʲôҲûд");
//		user1.showinfo();
//		
//	}

}
