package letsmakeit;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashSet;
import java.util.Iterator;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import javax.swing.JTextArea;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField numtext;
	private JTextField nametext;
	private JTextField updatenum;
	private JTextField editname;
	private JTextField deletenum;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("Welcome to DataBase");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 838, 678);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel QU = new JPanel();
		tabbedPane.addTab("Query UserDataBase", null, QU, null);
		QU.setLayout(null);
		
		JButton btnNewButton = new JButton("EXIT");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton.setBounds(726, 567, 71, 23);
		QU.add(btnNewButton);
		
		Integer[] in = new Integer[101];
		
		for(int i = 0;i <= 100 ;i++){
			in[i] = i;
		}
		JComboBox<Integer> agetbox = new JComboBox<Integer>();
		agetbox.setModel(new DefaultComboBoxModel<Integer>(in));
		agetbox.setBounds(375, 38, 42, 21);
		QU.add(agetbox);
		
		
		JLabel tips = new JLabel("");
		tips.setFont(new Font("宋体", Font.PLAIN, 14));
		tips.setBounds(102, 567, 388, 23);
		QU.add(tips);
		
		JLabel lblNumber = new JLabel("Number:");
		lblNumber.setBounds(125, 10, 54, 15);
		QU.add(lblNumber);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(205, 10, 54, 15);
		QU.add(lblName);
		
		JLabel lblSex = new JLabel("Sex:");
		lblSex.setBounds(290, 10, 54, 15);
		QU.add(lblSex);
		
		JLabel lblAge = new JLabel("Age:");
		lblAge.setBounds(375, 10, 54, 15);
		QU.add(lblAge);
		
		
		JTextArea display = new JTextArea();
		display.setLineWrap(true);
		display.setEditable(false);
		display.setBounds(102, 94, 681, 451);
		QU.add(display);
		
		JRadioButton sex1 = new JRadioButton("Male");
		sex1.setBounds(287, 31, 57, 15);
		QU.add(sex1);
		
		JRadioButton sex2 = new JRadioButton("Female");
		sex2.setBounds(287, 48, 66, 15);
		QU.add(sex2);
		
		ButtonGroup sexgroup = new ButtonGroup();
		sexgroup.add(sex1);
		sexgroup.add(sex2);
		
		JButton btnQueryAll = new JButton("Query All");
		btnQueryAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDBCtool qjt = new JDBCtool("UserData");
				String diString = qjt.getUserData();
				display.setText(diString);
				tips.setText("QueryAll is done");
				qjt.releaseConnection();
			}
		});
		
		JButton btnQuery = new JButton("Query");
		btnQuery.setBounds(623, 567, 93, 23);
		QU.add(btnQuery);
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//获取文本框信息
				String numt = numtext.getText();
				String name = nametext.getText();
				String sex = "";
				int age = (int) agetbox.getSelectedItem();
				if(age == 0)
					age = -1;
				if(sex1.isSelected())
					sex = "Male";
				else if(sex2.isSelected())
					sex = "Female";
				
				//解析信息
				if((! numt.matches("[0-9]+")) && (! numt.equals(""))){//当编号输入不合法
					tips.setText("please input positive digits in line: \"Number\"");
				}
				
				else if(numt.equals("") && name.equals("") && sex.equals("") && age==-1)//当没有输入
					btnQueryAll.doClick();
				
				else{//当部分输入
					User qUser = new User();
					
					qUser.setName(name);
					qUser.setSex(sex);
					qUser.setAge(age);
					JDBCtool qjt = new JDBCtool("UserData");
					
					if(!numt.equals(""))//当有编号输入的时候
					{
						qUser.setNum(Integer.valueOf(numt));
						if(name.equals("") && sex.equals("") && age==-1)//当只有编号输入的时候
							{
							if(!qjt.exist(Integer.valueOf(numt))){
								display.setText("There is no such user(s).");
								tips.setText("Query is done");
							}
							else{
								User check = qjt.selectbyNum(Integer.valueOf(numt));
								display.setText(check.getinfo());
								tips.setText("Query is done");
							}
							
							}
						else{
							User check = qjt.selectbyNum(Integer.valueOf(numt));
							if(!qUser.equals(check)){
							display.setText("There is no such user(s).");
							tips.setText("Query is done");
							}
							else{
							String string = check.getinfo();
							display.setText(string);
							tips.setText("Query is done");
						}
					}		
					}
					//当没有编号输入的时候，使用模糊查找fuzzyselect
					else{
						HashSet<User> qresult = qjt.fuzzySelect(qUser);
						
						if(qresult.isEmpty())
							display.setText("There is no such user(s).");
						else{
							Iterator<User>iu = qresult.iterator();
							String disresult = "";
							while(iu.hasNext())
							disresult += iu.next().getinfo() +'\n';
							
							tips.setText("Query is done");
							display.setText(disresult);
						}
					}
					
					qjt.releaseConnection();
				}
			}
		});
		
		btnQueryAll.setBounds(520, 567, 93, 23);
		QU.add(btnQueryAll);
		
		JLabel lblCondition = new JLabel("Condition:");
		lblCondition.setBounds(10, 41, 71, 15);
		QU.add(lblCondition);
		
		JLabel lblQueryResult = new JLabel("Query Result:");
		lblQueryResult.setBounds(10, 79, 93, 15);
		QU.add(lblQueryResult);
		
		JLabel lblSimpleInformation = new JLabel("Simple Information");
		lblSimpleInformation.setBounds(491, 10, 129, 15);
		QU.add(lblSimpleInformation);
		
		numtext = new JTextField();
		numtext.setBounds(113, 38, 66, 21);
		QU.add(numtext);
		numtext.setColumns(10);
		
		nametext = new JTextField();
		nametext.setBounds(205, 38, 66, 21);
		QU.add(nametext);
		nametext.setColumns(10);
		
		
		
		JButton btnNewButton_1 = new JButton("Clear selection");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sexgroup.clearSelection();
			}
		});
		btnNewButton_1.setBounds(258, 69, 129, 23);
		QU.add(btnNewButton_1);
		
		
		
		JPanel UU = new JPanel();
		tabbedPane.addTab("Update UserDataBase", null, UU, null);
		UU.setLayout(null);
		
		JButton btnExit = new JButton("EXIT");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(716, 567, 81, 23);
		UU.add(btnExit);
		
		JLabel lblInputTheNumber = new JLabel("Please input the number of the User you want to update:");
		lblInputTheNumber.setFont(new Font("宋体", Font.PLAIN, 14));
		lblInputTheNumber.setBounds(10, 29, 396, 28);
		UU.add(lblInputTheNumber);
		
		updatenum = new JTextField();
		updatenum.setBounds(435, 33, 66, 21);
		UU.add(updatenum);
		updatenum.setColumns(10);
		
		JLabel lblPleaseEditThe = new JLabel("Please edit the information you want to update:");
		lblPleaseEditThe.setFont(new Font("宋体", Font.PLAIN, 14));
		lblPleaseEditThe.setBounds(10, 102, 344, 15);
		UU.add(lblPleaseEditThe);
		
		JLabel lblIWantTo = new JLabel("I want to change");
		lblIWantTo.setFont(new Font("宋体", Font.PLAIN, 13));
		lblIWantTo.setBounds(89, 162, 115, 15);
		UU.add(lblIWantTo);
		
		JComboBox<String> selectbox = new JComboBox<String>();
		selectbox.setFont(new Font("宋体", Font.PLAIN, 13));
		selectbox.setModel(new DefaultComboBoxModel<String>(new String[] {"Age", "Name", "Sex", "Simple introduction"}));
		selectbox.setBounds(225, 153, 98, 33);
		UU.add(selectbox);
		
		editname = new JTextField();
		editname.setEditable(false);
		editname.setEnabled(false);
		editname.setBounds(458, 159, 66, 21);
		UU.add(editname);
		editname.setColumns(10);
		
		JComboBox<Integer> editage = new JComboBox<Integer>();
		editage.setModel(new DefaultComboBoxModel<Integer>(in));
		editage.setFont(new Font("宋体", Font.PLAIN, 13));
		editage.setBounds(406, 159, 42, 21);
		UU.add(editage);
		
		JRadioButton rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setEnabled(false);
		rdbtnMale.setBounds(530, 158, 66, 23);
		UU.add(rdbtnMale);
		
		JRadioButton rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.setEnabled(false);
		rdbtnFemale.setBounds(598, 158, 66, 23);
		UU.add(rdbtnFemale);
		
		ButtonGroup sgroup = new ButtonGroup();
		sgroup.add(rdbtnFemale);
		sgroup.add(rdbtnMale);
		
		JTextArea editsim = new JTextArea();
		editsim.setEnabled(false);
		editsim.setEditable(false);
		editsim.setBounds(406, 211, 254, 118);
		UU.add(editsim);
		
		 ItemListener itemListener = new ItemListener() {
		      @Override
		      public void itemStateChanged(ItemEvent arg0) {
		        
		        if(ItemEvent.SELECTED == arg0.getStateChange()){
		          String selectedItem = arg0.getItem().toString();
		          switch (selectedItem) {
				case "Age":
					editage.setEditable(true);
					editage.setEnabled(true);editage.setSelectedIndex(0);
					editname.setEditable(false);
					editname.setEnabled(false);editname.setText("");
					rdbtnFemale.setEnabled(false);
					rdbtnMale.setEnabled(false);
					sgroup.clearSelection();
					editsim.setEnabled(false);
					editsim.setEditable(false);editsim.setText("");
					break;
				case "Name":
					editage.setEditable(false);
					editage.setEnabled(false);editage.setSelectedIndex(0);
					editname.setEditable(true);
					editname.setEnabled(true);editname.setText("");
					rdbtnFemale.setEnabled(false);
					rdbtnMale.setEnabled(false);
					sgroup.clearSelection();
					editsim.setEnabled(false);
					editsim.setEditable(false);editsim.setText("");
					break;
				case "Sex":
					editage.setEditable(false);
					editage.setEnabled(false);editage.setSelectedIndex(0);
					editname.setEditable(false);
					editname.setEnabled(false);editname.setText("");
					rdbtnFemale.setEnabled(true);
					rdbtnMale.setEnabled(true);
					sgroup.clearSelection();
					editsim.setEnabled(false);
					editsim.setEditable(false);
					editsim.setText("");
					break;
				case "Simple introduction":
					editage.setEditable(false);
					editage.setEnabled(false);editage.setSelectedIndex(0);
					editname.setEditable(false);
					editname.setEnabled(false);editname.setText("");
					rdbtnFemale.setEnabled(false);
					rdbtnMale.setEnabled(false);
					sgroup.clearSelection();
					editsim.setEnabled(true);
					editsim.setEditable(true);
					editsim.setText("");
					break;
				default:
					break;
				}
		          
		          //System.out.printf("new selected item : %s%n",selectedItem);
		        }
		        //if(ItemEvent.DESELECTED == arg0.getStateChange()){
		         // String selectedItem = arg0.getItem().toString();
		         // System.out.printf("deselected item : %s%n",selectedItem);
		       // }
		      }
		      };
		      
		      selectbox.addItemListener(itemListener);
		
		JLabel lblTo = new JLabel("To");
		lblTo.setFont(new Font("宋体", Font.PLAIN, 13));
		lblTo.setBounds(352, 162, 32, 15);
		UU.add(lblTo);
		
		
		
		JLabel updatetips = new JLabel("");
		updatetips.setBounds(185, 482, 479, 46);
		UU.add(updatetips);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//先对文本框的输入内容进行解析
				String tochange = updatenum.getText();
				if(!tochange.matches("[0-9]+"))//当输入的不是数字,或者无输入的时候
				{
					updatetips.setText("please input positive digits");
				}
				else//当输入格式正确
				{
					int num = Integer.valueOf(tochange);
					JDBCtool ujt = new JDBCtool("UserData");
					if(!ujt.exist(num))//当该编号不存在
					{
						updatetips.setText("this account doesn't exist, fail to update information");
					}
					else{//当编号存在
						String mode = (String) selectbox.getSelectedItem();//根据不同分支来进行处理
						mode = mode.toLowerCase();
						//System.out.println(mode);
						switch (mode) {
						case "age":
							ujt.updateUser(mode, ""+(Integer)editage.getSelectedItem(), num);
							break;
						case "name":
							ujt.updateUser(mode, editname.getText(), num);
							
							break;
						case "sex":
							String s;
							if(rdbtnFemale.isSelected())
								s = "Female";
							else 
								s = "Male";
							ujt.updateUser(mode, s, num);
							break;
						case "simple introduction":
							ujt.updateUser("simpleinfo", editsim.getText(), num);
							break;
						default:
							break;
						}
						updatetips.setText("Update completed");
					}
					
					
					ujt.releaseConnection();
					
				}
				//其次用switch语句分情况对对象进行更新
			}
		});
		btnUpdate.setFont(new Font("宋体", Font.PLAIN, 14));
		btnUpdate.setBounds(316, 360, 161, 53);
		UU.add(btnUpdate);
		
		
		JPanel DU = new JPanel();
		tabbedPane.addTab("Delete UserData", null, DU, null);
		DU.setLayout(null);
		
		JButton btnExit_1 = new JButton("EXIT");
		btnExit_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit_1.setBounds(710, 567, 87, 23);
		DU.add(btnExit_1);
		
		JLabel lblInputTheNumber_1 = new JLabel("Input the number of the user you want to delete");
		lblInputTheNumber_1.setFont(new Font("宋体", Font.PLAIN, 14));
		lblInputTheNumber_1.setBounds(24, 20, 350, 23);
		DU.add(lblInputTheNumber_1);
		
		deletenum = new JTextField();
		deletenum.setBounds(390, 21, 66, 21);
		DU.add(deletenum);
		deletenum.setColumns(10);
		
		JLabel deletetips = new JLabel("");
		deletetips.setFont(new Font("宋体", Font.PLAIN, 18));
		deletetips.setBounds(118, 169, 544, 158);
		DU.add(deletetips);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String delnumt = deletenum.getText();
				if(!delnumt.matches("[0-9]+"))
				{
					deletetips.setText("please input a positive number!");
				}
				else{
					int delnum = Integer.valueOf(delnumt);
					JDBCtool delt = new JDBCtool("UserData");
					if(delt.exist(delnum))
					{
						delt.deleteUserbynum(delnum);
						deletetips.setText("delete success");
					}
					else{
						deletetips.setText("this number doesn't exist, fail to delete.");
					}
					delt.releaseConnection();
				}
			}
		});
		btnDelete.setFont(new Font("宋体", Font.PLAIN, 14));
		btnDelete.setBounds(549, 20, 182, 49);
		DU.add(btnDelete);
		
	}
}
