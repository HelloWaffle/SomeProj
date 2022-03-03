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
	private String database;//数据库的名称
	
	//创建对象的时候就启动连接
	public JDBCtool(String database){
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			System.out.println("数据库驱动成功");
			con = getConnection();
			this.database = database;
//		} catch (ClassNotFoundException e) {
//			System.out.println("数据库驱动失败");
//			e.printStackTrace();
//		}
		
	}
	
	public void showUserData()//遍历所有用户信息
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
	
	public boolean addUser(User user)//向数据库中添加用户信息
	{
		pts = null;
		if(selectbyNum(user.getNum()).getNum() == -1)//要求用户的编号存在唯一性
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
	
	public void updateUser(String changelab,String newinfo,int number)//更新用户数据
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
	
	public void deleteUser(User user)//删除指定用户信息
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
	
	
	public void deleteUserbynum(int num)//根据编号删除用户
	{
		pts = null;
		try {
			pts = con.prepareStatement("delete from "+database+" where num="+num);
			pts.executeUpdate();//删除完成
		} catch (SQLException e) {
			System.out.println("fail to delete this user");
			e.printStackTrace();
		}
		
	}
	
	

	public HashSet<User> selectbyName(String name)//根据名字查找
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
	
	
	public User selectbyNum(int num)//根据编号查找
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
	
	public HashSet<User> selectbySex(String sex)//根据性别查找
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
	
	public HashSet<User> selectbyAge(int age)//根据年龄查找
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
	
	public HashSet<User> fuzzySelect(User user)//根据不完整的多个信息查找最大满足给予条件的User对象数据
	{
		HashSet<User> userset = new HashSet<User>();
		HashSet<User> set1 = new HashSet<User>();
		HashSet<User> set2 = new HashSet<User>();
		HashSet<User> set3 = new HashSet<User>();
		
		if(! (user.getName().equals("")) )//当名字不为空的时候
			set1 = selectbyName(user.getName());
		if(user.getAge() != -1 )//当年龄不是默认值的时候
			set2 = selectbyAge(user.getAge());
		if(! (user.getSex().equals("")))//当性别不为空的时候
			set3 = selectbySex(user.getSex());

		if( !set1.isEmpty() && !set2.isEmpty() && !set3.isEmpty())//当3个都不为空
		{
			userset.clear();
			userset.addAll(set1);
			userset.retainAll(set2);//取交集
			set1.clear();
			set2.clear();
			set1.addAll(userset);
			set1.retainAll(set3);
		}
		else if(set1.isEmpty() && !set2.isEmpty() && !set3.isEmpty())//只有set1是空
		{
			userset.clear();
			userset.addAll(set2);
			userset.retainAll(set3);
			return userset;
		}
		else if(!set1.isEmpty() && set2.isEmpty() && !set3.isEmpty()){//只有set2是空
			userset.clear();
			userset.addAll(set1);
			userset.retainAll(set3);
			return userset;
		}
		else if(!set1.isEmpty() && !set2.isEmpty() && set3.isEmpty()){//只有set3为空
			userset.clear();
			userset.addAll(set1);
			userset.retainAll(set2);
			return userset;
		}
		else if (!set1.isEmpty() && set2.isEmpty() && set3.isEmpty()) {//只有set1没空
			return set1;
		}
		else if(set1.isEmpty() && !set2.isEmpty() && set3.isEmpty()){//只有set2没空
			return set2;
		}
		else if (set1.isEmpty() && set2.isEmpty() && !set3.isEmpty()) {//只有set3没空
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

	public boolean exist(String name)//检查该用户名是否存在
	{
		if(selectbyName(name).isEmpty())
			return false;
		else
			return true;
	}
	
	public boolean exist(int num)//检查编号是否存在
	{
		if(selectbyNum(num).getNum() == -1)
			return false;
		else {
			return true;
		}
	}
	
	public boolean checkPsw(int num,String psw)//检查用户密码是否正确
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
