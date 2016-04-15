package com.ryd.demo.swing.listener;

import com.ryd.demo.server.bean.StQuote;
import com.ryd.protocol.NettyMessage;
import com.ryd.demo.swing.common.ClientConstants;
import com.ryd.demo.swing.frame.QuotePriceJDialog;
import com.ryd.demo.swing.service.impl.MessageServiceImpl;

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
			NettyMessage msg = new NettyMessage();

			StQuote quote = new StQuote();
			quote.setAccountId(QuotePriceJDialog.instance().accountId);
			quote.setStockId(QuotePriceJDialog.instance().stockCode);
			quote.setQuotePrice(Double.valueOf(textQuotePrice.getText()));
			quote.setAmount(Integer.valueOf(textAmount.getText()));
			quote.setType((buyOrSellBuy.isSelected()==true?1:2));

			msg.setMsgObj(quote);
			msg.setMsgType(ClientConstants.STQUOTE_PRICE);

			MessageServiceImpl.sendMessage(msg);

		} else if (e.getSource() == cancel) {
			QuotePriceJDialog.instance().dispose();
		}
		
	}
}
