package com.ryd.server.stocktrader.swing.listener;

import com.ryd.basecommon.protocol.protobuf.DiyNettyMessage;
import com.ryd.business.model.StAccount;
import com.ryd.server.stocktrader.swing.service.impl.MessageServiceImpl;
import com.ryd.server.stocktrader.utils.TestParamBuilderUtil;

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
	private JTextField password;
	private JButton register;
	private JButton ensure;
	private JButton cancel;

	public LoginListener(JTextField jtfUserName, JTextField password,JButton register,
			JButton ensure, JButton cancel) {
		this.jtfUserName = jtfUserName;
		this.password = password;
		this.register = register;
		this.ensure = ensure;
		this.cancel = cancel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ensure || e.getSource() == register) {
			if (jtfUserName.getText().equals("")||password.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "请输入帐号密码信息", "提示",
						JOptionPane.ERROR_MESSAGE);

			}else if(e.getSource() == ensure){
				//登录
				DiyNettyMessage.NettyMessage.Builder builder = TestParamBuilderUtil.login(jtfUserName.getText(), password.getText());
				MessageServiceImpl.sendMessage(builder.build());

			}else if(e.getSource() == register){
				//注册
				DiyNettyMessage.NettyMessage.Builder builder = TestParamBuilderUtil.register("test",jtfUserName.getText(),password.getText());
				MessageServiceImpl.sendMessage(builder.build());
			}
		} else if (e.getSource() == cancel) {
			 System.exit(0);
		}
	}
}
