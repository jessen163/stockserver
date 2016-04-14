package com.ryd.demo.swing.listener;

import com.ryd.demo.server.bean.StAccount;
import com.ryd.demo.server.protocol.NettyMessage;
import com.ryd.demo.swing.common.ClientConstants;
import com.ryd.demo.swing.service.impl.MessageServiceImpl;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

/**
 * <p>标题:登录按钮监听</p>
 * <p>描述:登录按钮监听</p>
 * 包名：com.ryd.stockanalysis.util
 * 创建人：songby
 * 创建时间：2016/4/6 10:12
 */
public class LoginListener extends MouseAdapter implements ActionListener {

	private JTextField jtfUserName;
	private JButton ensure;
	private JButton cancel;

	public LoginListener(JTextField jtfUserName,
			JButton ensure, JButton cancel) {
		this.jtfUserName = jtfUserName;
		this.ensure = ensure;
		this.cancel = cancel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jtfUserName || e.getSource() == ensure) {
			if (jtfUserName.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "请输入帐号信息", "提示",
						JOptionPane.ERROR_MESSAGE);

			}else{
				StAccount acc = new StAccount();
				acc.setAccountNumber(jtfUserName.getText());
				//登录
				NettyMessage msg = new NettyMessage();
				msg.setMsgObj(acc);
				msg.setMsgType(ClientConstants.CLIENT_LOGIN);

				MessageServiceImpl.sendMessage(msg);

			}
		} else if (e.getSource() == cancel) {
			 System.exit(0);
		}
	}
}
