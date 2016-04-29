package com.ryd.server.stocktrader.swing.listener;

import com.ryd.basecommon.protocol.protobuf.DiyNettyMessage;
import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.server.stocktrader.swing.common.ClientConstants;
import com.ryd.server.stocktrader.swing.frame.QuotePriceJDialog;
import com.ryd.server.stocktrader.swing.service.impl.MessageServiceImpl;
import com.ryd.server.stocktrader.utils.TestParamBuilderUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

/**
 * <p>标题:报价提交按钮监听</p>
 * <p>描述:报价提交按钮监听</p>
 * 包名：com.ryd.stockanalysis.util
 * 创建人：songby
 * 创建时间：2016/4/6 10:12
 */
public class QuotePriceListener extends MouseAdapter implements ActionListener {

	private JTextField textAccountName,textStockId,textAmount,textQuotePrice;
	private JRadioButton buyOrSellBuy,buyOrSellSell;
	private JButton ensure;
	private JButton cancel;


	public QuotePriceListener(JTextField textAccountName,JTextField textStockId,JTextField textQuotePrice,JTextField textAmount,
			JRadioButton buyOrSellBuy, JRadioButton buyOrSellSell, JButton ensure, JButton cancel) {
		this.textAccountName = textAccountName;
		this.textStockId = textStockId;
		this.textQuotePrice = textQuotePrice;
		this.textAmount = textAmount;
		this.buyOrSellSell = buyOrSellSell;
		this.buyOrSellBuy = buyOrSellBuy;
		this.ensure = ensure;
		this.cancel = cancel;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == ensure) {

			DiyNettyMessage.NettyMessage.Builder builder = TestParamBuilderUtil.getQuote(QuotePriceJDialog.instance().stockCode, ClientConstants.stAccount.getId(),
					Double.valueOf(textQuotePrice.getText()), (buyOrSellBuy.isSelected() == true ? 1 : 2), Integer.valueOf(textAmount.getText()));
			MessageServiceImpl.sendMessage(builder.build());

		} else if (e.getSource() == cancel) {
			QuotePriceJDialog.instance().dispose();
		}
		
	}
}
