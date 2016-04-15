package com.ryd.demo.swing.listener;

import com.ryd.protocol.NettyMessage;
import com.ryd.demo.swing.common.ClientConstants;
import com.ryd.demo.swing.service.impl.MessageServiceImpl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

/**
 * <p>标题:报价列表监听</p>
 * <p>描述:报价列表监听</p>
 * 包名：com.ryd.stockanalysis.util
 * 创建人：songby
 * 创建时间：2016/4/6 10:12
 */
public class QuoteListListener extends MouseAdapter implements ActionListener {

	public QuoteListListener() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {

			NettyMessage msg = new NettyMessage();
			msg.setMsgObj(ClientConstants.stAccount);
			msg.setMsgType(ClientConstants.STQUOTE_PRICE_LIST);

			MessageServiceImpl.sendMessage(msg);

	}
}
