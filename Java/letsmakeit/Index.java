package letsmakeit;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class Index extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField numinput;
	private JPasswordField pswinput;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Index frame = new Index();
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
	public Index() {
		setTitle("Welcome");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbln = new JLabel("Number");
		lbln.setFont(new Font("宋体", Font.PLAIN, 18));
		lbln.setBounds(36, 33, 87, 34);
		contentPane.add(lbln);
		
		JLabel tips = new JLabel("");
		tips.setFont(new Font("宋体", Font.PLAIN, 14));
		tips.setBounds(36, 144, 356, 41);
		contentPane.add(tips);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDBCtool jt = new JDBCtool("UserData");
				String numt = numinput.getText();
				String psw = new String(pswinput.getPassword());
				if(numt.equals("") || psw.equals(""))
					tips.setText("please complete ' number and password ' ");
				else{
					if(numt.matches("[0-9]+")){
						if(jt.exist(Integer.valueOf(numt)))
						{
							if(jt.checkPsw(Integer.valueOf(numt), psw))//登陆成功
							{
								MainFrame mf = new MainFrame();
								mf.setVisible(true);
								dispose();
								jt.releaseConnection();
							}
							else //密码错误
							{
								tips.setText("password is not match for the account");
							}
						}
						else//用户名不存在
						{
							tips.setText("this Account doesn't exist");
						}
					}
					else{
						tips.setText("please input positive digits in Line \"Number\"");
					}
					
				}
				
			}
		});
		btnLogin.setBounds(53, 195, 93, 23);
		contentPane.add(btnLogin);
		
		JLabel lblOtherwisePleaseChoose = new JLabel("Password");
		lblOtherwisePleaseChoose.setFont(new Font("宋体", Font.PLAIN, 18));
		lblOtherwisePleaseChoose.setBounds(36, 93, 87, 34);
		contentPane.add(lblOtherwisePleaseChoose);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Register rg = new Register();
				rg.setVisible(true);
				dispose();
			}
		});
		btnRegister.setBounds(179, 195, 93, 23);
		contentPane.add(btnRegister);
		
		JButton btnNewButton = new JButton("Exit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton.setBounds(299, 195, 93, 23);
		contentPane.add(btnNewButton);
		
		numinput = new JTextField();
		numinput.setBounds(154, 42, 236, 21);
		contentPane.add(numinput);
		numinput.setColumns(10);
		
		pswinput = new JPasswordField();
		pswinput.setBounds(154, 102, 236, 21);
		contentPane.add(pswinput);
		
		
	}
}
