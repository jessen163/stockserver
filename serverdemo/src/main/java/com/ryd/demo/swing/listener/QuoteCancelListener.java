package com.ryd.demo.swing.listener;

import com.ryd.demo.server.protocol.NettyMessage;
import com.ryd.demo.swing.common.ClientConstants;
import com.ryd.demo.swing.frame.QuoteListDialog;
import com.ryd.demo.swing.service.impl.MessageServiceImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

/**
 * <p>标题:报价撤单</p>
 * <p>描述:报价撤单监听</p>
 * 包名：com.ryd.stockanalysis.util
 * 创建人：songby
 * 创建时间：2016/4/6 10:12
 */
public class QuoteCancelListener extends MouseAdapter implements ActionListener {

	JTable table;

	public QuoteCancelListener(JTable table) {
          this.table = table;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		int selectedRow = table.getSelectedRow();
		if (selectedRow != -1) {
			String quoteId = (String) table.getValueAt(selectedRow,7);

			NettyMessage msg = new NettyMessage();
			msg.setMsgObj(ClientConstants.stQuoteMap.get(quoteId));
			msg.setMsgType(ClientConstants.STQUOTE_RECALL);

			MessageServiceImpl.sendMessage(msg);

			QuoteListDialog.instance().dispose();
		}else{
			JOptionPane.showMessageDialog(null, "请选择撤单报价", "提示",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
