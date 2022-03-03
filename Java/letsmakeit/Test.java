package letsmakeit;
//本文件只用于做实验，无其他用处

public class Test {

	public static void main(String[] args) {
//		Scanner scanner = new Scanner(System.in);
//		String string = scanner.next();
//		
//		//int num  = Integer.valueOf(string);
//		//System.out.println(num);
//		String reg = "[0-9]+";
//		boolean isNum = string.matches(reg);
//		System.out.println(isNum);
		
//		HashSet<Integer>integers = new HashSet<>();
//		System.out.println(integers.isEmpty());
		
		JDBCtool jt = new JDBCtool("UserData");
		
		jt.updateUser("name", "ereeg", 888);
		jt.releaseConnection();
	}

}
