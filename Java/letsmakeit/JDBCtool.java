package letsmakeit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;

public class JDBCtool {
	private Connection con;
	private PreparedStatement pts;
	private String database;//���ݿ������
	
	//���������ʱ�����������
	public JDBCtool(String database){
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			System.out.println("���ݿ������ɹ�");
			con = getConnection();
			this.database = database;
//		} catch (ClassNotFoundException e) {
//			System.out.println("���ݿ�����ʧ��");
//			e.printStackTrace();
//		}
		
	}
	
	public void showUserData()//���������û���Ϣ
	{
		pts = null;
		ResultSet rs = null;
		try {
			pts = con.prepareStatement("select * from "+database);
			rs = pts.executeQuery();
			while(rs.next())
			{
				int num = rs.getInt("num");
				String name = rs.getString("name");
				String psw = rs.getString("password");
				String sex = rs.getString("sex");
				int age = rs.getInt("age");
				String simpleinfo = rs.getString("simpleinfo");
				
				User temp = new User(num,name,psw,sex,age,simpleinfo);
				temp.showinfo();
			}
			System.out.println("iteration is over");
		} catch (SQLException e) {
			System.out.println("fail to creat ResultSet instance");
			e.printStackTrace();
		}
		
	}
	public String getUserData()
	{
		pts = null;
		ResultSet rs = null;
		String result = "";
		try {
			pts = con.prepareStatement("select * from "+database);
			rs = pts.executeQuery();
			while(rs.next())
			{
				int num = rs.getInt("num");
				String name = rs.getString("name");
				String psw = rs.getString("password");
				String sex = rs.getString("sex");
				int age = rs.getInt("age");
				String simpleinfo = rs.getString("simpleinfo");
				
				User temp = new User(num,name,psw,sex,age,simpleinfo);
				result += temp.getinfo() + '\n';
			}
			//System.out.println("iteration is over");
		} catch (SQLException e) {
			System.out.println("fail to creat ResultSet instance");
			e.printStackTrace();
		}
		return result;
		
	}
	
	public boolean addUser(User user)//�����ݿ�������û���Ϣ
	{
		pts = null;
		if(selectbyNum(user.getNum()).getNum() == -1)//Ҫ���û��ı�Ŵ���Ψһ��
		try {
			pts = con.prepareStatement("insert into "+database+" values(?,?,?,?,?,?)");
			
			pts.setInt(1, user.getNum());
			pts.setString(2, user.getName());
			pts.setString(3, user.getPswd());
			pts.setString(4, user.getSex());
			pts.setInt(5, user.getAge());
			pts.setString(6, user.getSimpleinfo());
			
			pts.executeUpdate();
			
			//System.out.println("insert successfully");
			return true;
			
		} catch (SQLException e) {
			System.out.println("fail to insert data to the database");
			e.printStackTrace();
		}
		return false;
		
	}
	
	public void updateUser(String changelab,String newinfo,int number)//�����û�����
	{
		pts = null;
		try {
			
			pts = con.prepareStatement("update " + database +" set "+changelab+"= ? where num = ?");
			pts.setString(1, newinfo);
			pts.setInt(2,number);
			
			pts.executeUpdate();
			System.out.println("update completed");
		} catch (SQLException e) {
			System.out.println("fail to update database");
			e.printStackTrace();
		}
	}
	
	public void deleteUser(User user)//ɾ��ָ���û���Ϣ
	{
		pts = null;
		if(selectbyNum(user.getNum()).getNum() != -1)
		try {
			pts = con.prepareStatement("delete from "+database+" where num=?");
			pts.setInt(1, user.getNum());
			pts.executeUpdate();
			System.out.println("delete completed");
		} catch (SQLException e) {
			System.out.println("fail to delete this user");
			e.printStackTrace();
		}
		else {
			System.out.println("this user doesn't exist in the table");
		}
	}
	
	
	public void deleteUserbynum(int num)//���ݱ��ɾ���û�
	{
		pts = null;
		try {
			pts = con.prepareStatement("delete from "+database+" where num="+num);
			pts.executeUpdate();//ɾ�����
		} catch (SQLException e) {
			System.out.println("fail to delete this user");
			e.printStackTrace();
		}
		
	}
	
	

	public HashSet<User> selectbyName(String name)//�������ֲ���
	{
		pts = null;
		HashSet<User> userset = new HashSet<User>();
		try {
			pts = con.prepareStatement("select * from "+database);
			ResultSet rs = pts.executeQuery();

			while(rs.next())
			{
				if(name.equals(rs.getString("name"))){
					int num = rs.getInt("num");
					//String name = rs.getString("name");
					String psw = rs.getString("password");
					String sex = rs.getString("sex");
					int age = rs.getInt("age");
					String simpleinfo = rs.getString("simpleinfo");
					
					userset.add(new User(num,name,psw,sex,age,simpleinfo));
				}					
			}
			
			return userset;
			
		} catch (SQLException e) {
			System.out.println("fail to creat ResultSet instance");
			e.printStackTrace();
		}
		
		return userset;
		
	}
	
	
	public User selectbyNum(int num)//���ݱ�Ų���
	{
		pts = null;
		User user = new User();
		ResultSet rs = null;
		try {
			pts = con.prepareStatement("select * from "+database);
			rs = pts.executeQuery();
			while(rs.next())
			{
				if(num == rs.getInt("num")){
					user.setNum(num);
					user.setName(rs.getString("name"));
					user.setPswd(rs.getString("password"));
					user.setSex(rs.getString("sex"));
					user.setAge(rs.getInt("age"));
					user.setSimpleinfo(rs.getString("simpleinfo"));
					break;
				}				
			}
			
			return user;
			
		} catch (SQLException e) {
			System.out.println("fail to creat ResultSet instance");
			e.printStackTrace();
		}
		return user;
	}
	
	public HashSet<User> selectbySex(String sex)//�����Ա����
	{
		pts = null;
		HashSet<User> userset = new HashSet<User>();
		try {
			pts = con.prepareStatement("select * from "+database);
			ResultSet rs = pts.executeQuery();

			while(rs.next())
			{
				if(sex.equals(rs.getString("sex"))){
					int num = rs.getInt("num");
					String name = rs.getString("name");
					String psw = rs.getString("password");
					//String sex = rs.getString("sex");
					int age = rs.getInt("age");
					String simpleinfo = rs.getString("simpleinfo");
					
					userset.add(new User(num,name,psw,sex,age,simpleinfo));
				}					
			}
			
			return userset;
			
		} catch (SQLException e) {
			System.out.println("fail to creat ResultSet instance");
			e.printStackTrace();
		}
		
		return userset;
	}
	
	public HashSet<User> selectbyAge(int age)//�����������
	{
		pts = null;
		HashSet<User> userset = new HashSet<User>();
		try {
			pts = con.prepareStatement("select * from "+database);
			ResultSet rs = pts.executeQuery();

			while(rs.next())
			{
				if(age == rs.getInt("age")){
					int num = rs.getInt("num");
					String name = rs.getString("name");
					String psw = rs.getString("password");
					String sex = rs.getString("sex");
					String simpleinfo = rs.getString("simpleinfo");
					
					userset.add(new User(num,name,psw,sex,age,simpleinfo));
				}			
			}
			
			return userset;
			
		} catch (SQLException e) {
			System.out.println("fail to creat ResultSet instance");
			e.printStackTrace();
		}
		
		return userset;
	}
	
	public HashSet<User> fuzzySelect(User user)//���ݲ������Ķ����Ϣ��������������������User��������
	{
		HashSet<User> userset = new HashSet<User>();
		HashSet<User> set1 = new HashSet<User>();
		HashSet<User> set2 = new HashSet<User>();
		HashSet<User> set3 = new HashSet<User>();
		
		if(! (user.getName().equals("")) )//�����ֲ�Ϊ�յ�ʱ��
			set1 = selectbyName(user.getName());
		if(user.getAge() != -1 )//�����䲻��Ĭ��ֵ��ʱ��
			set2 = selectbyAge(user.getAge());
		if(! (user.getSex().equals("")))//���Ա�Ϊ�յ�ʱ��
			set3 = selectbySex(user.getSex());

		if( !set1.isEmpty() && !set2.isEmpty() && !set3.isEmpty())//��3������Ϊ��
		{
			userset.clear();
			userset.addAll(set1);
			userset.retainAll(set2);//ȡ����
			set1.clear();
			set2.clear();
			set1.addAll(userset);
			set1.retainAll(set3);
		}
		else if(set1.isEmpty() && !set2.isEmpty() && !set3.isEmpty())//ֻ��set1�ǿ�
		{
			userset.clear();
			userset.addAll(set2);
			userset.retainAll(set3);
			return userset;
		}
		else if(!set1.isEmpty() && set2.isEmpty() && !set3.isEmpty()){//ֻ��set2�ǿ�
			userset.clear();
			userset.addAll(set1);
			userset.retainAll(set3);
			return userset;
		}
		else if(!set1.isEmpty() && !set2.isEmpty() && set3.isEmpty()){//ֻ��set3Ϊ��
			userset.clear();
			userset.addAll(set1);
			userset.retainAll(set2);
			return userset;
		}
		else if (!set1.isEmpty() && set2.isEmpty() && set3.isEmpty()) {//ֻ��set1û��
			return set1;
		}
		else if(set1.isEmpty() && !set2.isEmpty() && set3.isEmpty()){//ֻ��set2û��
			return set2;
		}
		else if (set1.isEmpty() && set2.isEmpty() && !set3.isEmpty()) {//ֻ��set3û��
			return set3;
		}
		return userset;
	}
	
	
	public Connection getConnection(){
		Connection con = null;
			try {
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "205613");
				return con;
			} catch (SQLException e) {
				System.out.println("fail to get connection");
				e.printStackTrace();
			}
			return con;			
	}
	
	
	public void releaseConnection(){
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println("fail to close connection");
			e.printStackTrace();
		}
	}

	public boolean exist(String name)//�����û����Ƿ����
	{
		if(selectbyName(name).isEmpty())
			return false;
		else
			return true;
	}
	
	public boolean exist(int num)//������Ƿ����
	{
		if(selectbyNum(num).getNum() == -1)
			return false;
		else {
			return true;
		}
	}
	
	public boolean checkPsw(int num,String psw)//����û������Ƿ���ȷ
	{
		User find = selectbyNum(num);
		
		if(find.getPswd().equals(psw))
			return true;
		else
			return false;
		
	}
	
//	public static void main(String[] args) {
//		User user1 = new User(2002,"zhangsan","iamkiller","male",20);
//		User user2 = new User(2001,"lisi","ikiller","female",28,"hehe");
//		JDBCtool jt = new JDBCtool("UserData");
//		jt.addUser(user1);
//		jt.addUser(user2);
//		jt.showUserData();
//		HashSet<User>select = jt.selectbySex("m");
//		if(select.isEmpty())
//		{
//			System.out.println("there is no such User");
//		}
//		else{
//			Iterator<User> iu=select.iterator();
//			while(iu.hasNext())
//			{
//				User temp = iu.next();
//				temp.showinfo();
//			}
//		}
//		
//		//jt.deleteUser(user2);
//		jt.releaseConnection();
//	}

}
