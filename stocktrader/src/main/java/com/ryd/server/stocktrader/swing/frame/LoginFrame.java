package com.ryd.server.stocktrader.swing.frame;

import com.ryd.server.stocktrader.swing.listener.LoginListener;

import javax.swing.*;
import java.awt.*;

/**
 * <p>标题:客户端登录</p>
 * <p>描述:客户端登录</p>
 * 包名：com.ryd.stockanalysis.util
 * 创建人：songby
 * 创建时间：2016/4/6 10:12
 */
public class LoginFrame extends JFrame{
	private static LoginFrame loginFrame;
	private JPanel outer;
	private JLabel one;
	private JLabel two;
	private JTextField accountNum;
	private JTextField password;
	private JButton loginBtn, cancelBtn,registerBtn;

	public static LoginFrame instance() {
		if (loginFrame == null)
			loginFrame = new LoginFrame();
		return loginFrame;
	}
	
	GridBagLayout g=new GridBagLayout();
	GridBagConstraints c=new GridBagConstraints();

	public LoginFrame() {
		super("登录");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(g);
      
        addComponent();
		LoginListener loginListener = new LoginListener(accountNum, password, registerBtn, loginBtn, cancelBtn);
		loginBtn.addActionListener(loginListener);
		cancelBtn.addActionListener(loginListener);
		registerBtn.addActionListener(loginListener);
		
		setLocationRelativeTo(null);
	}
	
    public void addComponent(){
    	getContentPane().setLayout(null);
		{
			outer = new JPanel();
			getContentPane().add(outer);
			outer.setBounds(41, 34, 313, 194);
			outer.setLayout(null);
			{
				one = new JLabel();
				outer.add(one);
				one.setText("帐号");
				one.setBounds(44, 36, 69, 15);
			}
			{
				accountNum = new JTextField();
				outer.add(accountNum);
				accountNum.setText("");
				accountNum.setBounds(119, 32, 133, 22);
			}
			{
				two = new JLabel();
				outer.add(two);
				two.setText("密码");
				two.setBounds(44, 60, 69, 15);
			}
			{
				password = new JTextField();
				outer.add(password);
				password.setText("");
				password.setBounds(119, 60, 133, 22);
			}
			{
				registerBtn = new JButton("注册");
				outer.add(registerBtn);
				registerBtn.setBounds(10, 150, 60, 25);
			}
			{
				loginBtn = new JButton("登录");
				outer.add(loginBtn);
				loginBtn.setBounds(120, 150, 60, 25);
			}
			{
				cancelBtn = new JButton("退出");
				outer.add(cancelBtn);
				cancelBtn.setBounds(200, 150, 60, 25);
			}
		}
    }

	public void open() {
		accountNum.setText("");
		password.setText("");
		setVisible(true);
	}

	public void exit() {
		System.exit(0);
	}

}