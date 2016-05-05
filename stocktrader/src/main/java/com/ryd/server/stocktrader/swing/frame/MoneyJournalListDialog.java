package com.ryd.server.stocktrader.swing.frame;

import com.ryd.business.model.StMoneyJournal;
import com.ryd.business.model.StQuote;
import com.ryd.server.stocktrader.swing.common.ClientConstants;
import com.ryd.server.stocktrader.swing.common.ListToArray;
import com.ryd.server.stocktrader.swing.listener.QuoteCancelListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * <p>标题:报价列表</p>
 * <p>描述:报价列表</p>
 * 包名：com.ryd.stockanalysis.util
 * 创建人：songby
 * 创建时间：2016/4/6 10:12
 */
public class MoneyJournalListDialog extends JDialog {

	public static String[] columnName = {"帐户", "股票代码","股票名称","报价", "交易数量", "类型", "交易金额", "佣金","印花税","交易时间"};

	private static MoneyJournalListDialog moneyJournalListDialog;

	JTable table;

	public static MoneyJournalListDialog instance() {
		if (moneyJournalListDialog == null)
			moneyJournalListDialog = new MoneyJournalListDialog();
		return moneyJournalListDialog;
	}

	public MoneyJournalListDialog() {
		super(MainFrame.instance(), "资金流水列表", true);
		setLayout(new BorderLayout());
		setSize(800, 450);
		setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		addComponent();
	}


	public void addComponent(){

		JPanel panelContainer = new JPanel();
		panelContainer.setLayout(new BoxLayout(panelContainer, BoxLayout.Y_AXIS));

		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		
		table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		panel1.add(scrollPane);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

		JButton jcloseButton = new JButton("退出");
		jcloseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moneyJournalListDialog.dispose();
			}
		});

		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(jcloseButton);

		panelContainer.add(panel1, BorderLayout.CENTER);
		panelContainer.add(buttonPanel,BorderLayout.SOUTH);

        add(panelContainer);
	}

	public void open() {
		setTableData();
		setVisible(true);
	}

	public void setTableData(){

		List<StMoneyJournal> stMoneyJournals = ClientConstants.stMoneyJournalList;
		table.removeAll();
		DefaultTableModel tableModel = new DefaultTableModel(ListToArray.journalListToArray(stMoneyJournals), columnName);
		table.setModel(tableModel);
		hideColumn(table,7);
	}

	/**
	 * 隐藏表格中的某一列
	 * @param table  表格
	 * @param index  要隐藏的列 的索引
	 */
	protected void hideColumn(JTable table,int index){
		TableColumn tc= table.getColumnModel().getColumn(index);
		tc.setMaxWidth(0);
		tc.setPreferredWidth(0);
		tc.setMinWidth(0);
		tc.setWidth(0);
		table.getTableHeader().getColumnModel().getColumn(index).setMaxWidth(0);
		table.getTableHeader().getColumnModel().getColumn(index).setMinWidth(0);
	}
}