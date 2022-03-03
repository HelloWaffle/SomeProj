package letsmakeit;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

public class Register extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField numtext;
	private JTextField nametext;
	private JPasswordField pswtext;
	private JTextField sinfotext;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register frame = new Register();
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
	public Register() {
		setTitle("Registing");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 508, 335);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPleaseCompleteThe = new JLabel("please complete the information:");
		lblPleaseCompleteThe.setFont(new Font("宋体", Font.PLAIN, 14));
		lblPleaseCompleteThe.setBounds(23, 22, 271, 15);
		contentPane.add(lblPleaseCompleteThe);
		
		JLabel lblNumber = new JLabel("Number:");
		lblNumber.setFont(new Font("宋体", Font.PLAIN, 14));
		lblNumber.setBounds(23, 67, 54, 15);
		contentPane.add(lblNumber);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("宋体", Font.PLAIN, 14));
		lblName.setBounds(23, 92, 54, 15);
		contentPane.add(lblName);
		
		JLabel tips = new JLabel("");
		tips.setBounds(185, 198, 239, 29);
		contentPane.add(tips);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("宋体", Font.PLAIN, 14));
		lblPassword.setBounds(23, 118, 75, 15);
		contentPane.add(lblPassword);
		
		JLabel lblSex = new JLabel("Sex:");
		lblSex.setFont(new Font("宋体", Font.PLAIN, 14));
		lblSex.setBounds(23, 148, 54, 15);
		contentPane.add(lblSex);
		
		JLabel lblAge = new JLabel("Age:");
		lblAge.setFont(new Font("宋体", Font.PLAIN, 14));
		lblAge.setBounds(23, 173, 54, 15);
		contentPane.add(lblAge);
		
		JLabel lblSimpleIntroduction = new JLabel("Simple Introduction:");
		lblSimpleIntroduction.setFont(new Font("宋体", Font.PLAIN, 14));
		lblSimpleIntroduction.setBounds(185, 67, 145, 15);
		contentPane.add(lblSimpleIntroduction);
		
		numtext = new JTextField();
		numtext.setBounds(87, 64, 66, 21);
		contentPane.add(numtext);
		numtext.setColumns(10);
		
		nametext = new JTextField();
		nametext.setBounds(87, 89, 66, 21);
		contentPane.add(nametext);
		nametext.setColumns(10);
		
		pswtext = new JPasswordField();
		pswtext.setBounds(87, 115, 66, 21);
		contentPane.add(pswtext);
		
		JRadioButton sex1 = new JRadioButton("Male");
		sex1.setSelected(true);
		sex1.setBounds(62, 144, 54, 23);
		contentPane.add(sex1);
		
		JRadioButton sex2 = new JRadioButton("Female");
		sex2.setBounds(118, 144, 66, 23);
		contentPane.add(sex2);
		
		ButtonGroup sexgroup = new ButtonGroup();
		sexgroup.add(sex1);
		sexgroup.add(sex2);
		
		JComboBox<Integer> agebox = new JComboBox<Integer>();
		Integer[] in = new Integer[100];
		
		for(int i = 1;i <= 100 ;i++){
			in[i-1] = i;
		}
		agebox.setModel(new DefaultComboBoxModel<Integer>(in));
		//agebox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40"}));
		agebox.setBounds(84, 170, 82, 18);
		contentPane.add(agebox);
		
		sinfotext = new JTextField();
		sinfotext.setBounds(185, 89, 297, 99);
		contentPane.add(sinfotext);
		sinfotext.setColumns(10);
		
		JButton btnFinish = new JButton("Finish");
		btnFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String numt = numtext.getText();
				String name = nametext.getText();
				String pwd =new String(pswtext.getPassword());
				String sex = "Male";
				int age = (int) agebox.getSelectedItem();
				String simpleinfo = sinfotext.getText();
				
				if(sex2.isSelected())
					sex = "Female";
				
				//先检查信息是否填写完毕
				if(numt.equals("") || name.equals("") || pwd.equals(""))
					tips.setText("Infomation is not completed!");
				else{
					//用正则表达式判断数字
					String reg = "[0-9]+";
					if(!numtext.getText().matches(reg))
					{
						tips.setText("Num must be Positive Digits");
					}
					else{
						JDBCtool rjt = new JDBCtool("UserData");
						int num = Integer.valueOf(numt);
						if(rjt.exist(num))//检查编号是否重复
							tips.setText("this num is already used,please input another num");
						else {
							User register = new User(num,name,pwd,sex,age,simpleinfo);
							
							rjt.addUser(register);
							
							
							Index relog = new Index();
							relog.setVisible(true);
							dispose();
						}
						rjt.releaseConnection();
						
					}
				}
				
				
				
			}
		});
		btnFinish.setBounds(101, 237, 93, 23);
		contentPane.add(btnFinish);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnCancel.setBounds(320, 237, 93, 23);
		contentPane.add(btnCancel);
		
		
	}
}
