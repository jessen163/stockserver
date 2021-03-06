package com.ryd.server.stocktrader.swing.listener;

import com.ryd.basecommon.protocol.protobuf.DiyNettyMessage;
import com.ryd.business.model.StAccount;
import com.ryd.server.stocktrader.swing.common.ClientConstants;
import com.ryd.server.stocktrader.swing.frame.QuoteListDialog;
import com.ryd.server.stocktrader.swing.service.impl.MessageServiceImpl;
import com.ryd.server.stocktrader.utils.TestParamBuilderUtil;

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
			String quoteId = (String) table.getValueAt(selectedRow,8);

			DiyNettyMessage.NettyMessage.Builder builder = TestParamBuilderUtil.getRevoke(quoteId, ClientConstants.stAccount.getId());
			MessageServiceImpl.sendMessage(builder.build());
			QuoteListDialog.instance().dispose();
		}else{
			JOptionPane.showMessageDialog(null, "请选择撤单报价", "提示",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
