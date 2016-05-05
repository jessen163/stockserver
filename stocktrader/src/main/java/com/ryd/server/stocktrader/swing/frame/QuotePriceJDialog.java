package com.ryd.server.stocktrader.swing.frame;

import com.ryd.business.model.StStock;
import com.ryd.business.model.StStockConfig;
import com.ryd.server.stocktrader.swing.common.ClientConstants;
import com.ryd.server.stocktrader.swing.listener.QuotePriceListener;

import javax.swing.*;

/**
 * <p>标题:报价</p>
 * <p>描述:报价</p>
 * 包名：com.ryd.stockanalysis.util
 * 创建人：songby
 * 创建时间：2016/4/6 10:12
 */
public class QuotePriceJDialog extends JDialog {

	private static QuotePriceJDialog quotePriceJDialog;

	public String stockId,accountId;

	JTextField textAccountName, textStockName, textAmount, textQuotePrice;
	JRadioButton buyOrSellBuy, buyOrSellSell;

	public static QuotePriceJDialog instance() {
		if (quotePriceJDialog == null)
			quotePriceJDialog = new QuotePriceJDialog();
		return quotePriceJDialog;
	}

	public QuotePriceJDialog() {
		super(MainFrame.instance(), "报价", true);
		setLayout(null);
		setSize(400, 330);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		quotePriceJDialog = this;
		JLabel accountLab = new JLabel("用户帐号");
		JLabel stockCodeLab = new JLabel("股票代码");
		JLabel quotePriceLab = new JLabel("报价");
		JLabel amountLab = new JLabel("股票数量");
		JLabel buyOrSellLab = new JLabel("买或卖");
		
		JButton ensure = new JButton("提交");
		JButton cancel = new JButton("关闭");
		
		textAccountName = new JTextField();
		textStockName = new JTextField();
		textAmount = new JTextField();
		textQuotePrice = new JTextField();

		accountLab.setBounds(60, 30, 69, 15);
		stockCodeLab.setBounds(60, 60, 69, 15);
		quotePriceLab.setBounds(60, 90, 69, 15);
		amountLab.setBounds(60, 120, 69, 15);
		buyOrSellLab.setBounds(60, 150, 69, 15);
	
		textAccountName.setBounds(130, 30, 133, 25);
		textStockName.setBounds(130, 60, 133, 25);
		textQuotePrice.setBounds(130, 90, 133, 25);
		textAmount.setBounds(130, 120, 133, 25);
		
        buyOrSellBuy=new JRadioButton("买入");
        buyOrSellBuy.setBounds(130, 150, 60, 25);
        buyOrSellBuy.setSelected(true);
        
        buyOrSellSell=new JRadioButton("卖出");
        buyOrSellSell.setBounds(190, 150, 60, 25);
        
		ButtonGroup group=new ButtonGroup();
	    group.add(buyOrSellBuy);
	    group.add(buyOrSellSell);
	    
	    ensure.setBounds(115, 205, 70, 25);
		cancel.setBounds(210, 205, 70, 25);


	    QuotePriceListener addRoomListener = new QuotePriceListener(textAccountName, textStockName, textQuotePrice, textAmount,
	    		buyOrSellBuy,buyOrSellSell,ensure,cancel);
	
		ensure.addActionListener(addRoomListener);
		cancel.addActionListener(addRoomListener);

		add(accountLab);
		add(stockCodeLab);
		add(quotePriceLab);
		add(amountLab);
		add(buyOrSellLab);
		add(textStockName);
		add(textAccountName);
		add(textQuotePrice);
		add(textAmount);
		add(buyOrSellBuy);
		add(buyOrSellSell);
		add(ensure);
		add(cancel);
	}

	public void open(String stockCode, int type) {
		textAmount.setText("");
		textQuotePrice.setText("");
		if(type==1) {
			buyOrSellBuy.setSelected(true);
		}else{
			buyOrSellSell.setSelected(true);
		}
		StStockConfig stStock = ClientConstants.stStockConfigMapKeyCode.get(stockCode);
		textAccountName.setText(ClientConstants.stAccount.getAccountName());
		textStockName.setText(stStock.getStockName());
		this.stockId = stStock.getId();
		this.accountId = ClientConstants.stAccount.getId();
		setVisible(true);
	}

}