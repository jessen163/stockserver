package com.ryd.server.stocktrader.swing.frame;

import com.ryd.business.model.StQuote;
import com.ryd.business.model.StTradeRecord;
import com.ryd.server.stocktrader.swing.common.ClientConstants;
import com.ryd.server.stocktrader.swing.common.ListToArray;

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
public class MonitorListDialog extends JDialog {

	public static String[] columnNameb = {"买入帐户","股票代码", "股票名称","报价", "申报数量","quoteId"};

	public static String[] columnNames = {"卖出帐户","股票代码", "股票名称","报价", "申报数量","quoteId"};

	public static String[] columnNamer = {"买入帐户","卖出帐户","股票代码", "股票名称","报价", "数量","交易时间", "ID"};

	private static MonitorListDialog quoteListDialog;

	JTable table,table2,table3;

	public static MonitorListDialog instance() {
		if (quoteListDialog == null)
			quoteListDialog = new MonitorListDialog();
		return quoteListDialog;
	}

	public MonitorListDialog() {
		super(MainFrame.instance(), "监控队列", true);
		setLayout(new BorderLayout());
		Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
		Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
		setSize(screenSize.width - 10, screenSize.height - 30);
		setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		addComponent();
	}


	public void addComponent(){

		JPanel panelContainer = new JPanel();
		panelContainer.setLayout(new BoxLayout(panelContainer, BoxLayout.Y_AXIS));

		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
		
		table = new JTable();
		JScrollPane scrollPaneb = new JScrollPane(table);
		scrollPaneb.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		table2 = new JTable();
		JScrollPane scrollPanes = new JScrollPane(table2);
		scrollPanes.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		panel1.add(scrollPaneb);
		panel1.add(Box.createHorizontalStrut(10));
		panel1.add(scrollPanes);

		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

		table3 = new JTable();
		JScrollPane scrollPaner = new JScrollPane(table3);
		scrollPaner.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		panel2.add(scrollPaner);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

		JButton jcloseButton = new JButton("退出");
		jcloseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quoteListDialog.dispose();
			}
		});

		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(jcloseButton);

		panelContainer.add(panel1, BorderLayout.NORTH);
		panelContainer.add(panel2, BorderLayout.CENTER);
		panelContainer.add(buttonPanel,BorderLayout.SOUTH);

        add(panelContainer);
	}

	public void open() {
		setTableData();
		setVisible(true);
	}

	public void setTableData(){

		List<StTradeRecord> monitorTradeRecordList = ClientConstants.monitorTradeRecordList;
		DefaultTableModel tableModel = new DefaultTableModel(ListToArray.mrecordListToArray(monitorTradeRecordList), columnNamer);
		table3.setModel(tableModel);
		hideColumn(table3,7);

		List<StQuote> monitorQuoteBuyList = ClientConstants.monitorQuoteBuyList;
		DefaultTableModel tableModel2 = new DefaultTableModel(ListToArray.mquoteListToArray(monitorQuoteBuyList), columnNameb);
		table.setModel(tableModel2);
		hideColumn(table, 5);

		List<StQuote> monitorQuoteSellList = ClientConstants.monitorQuoteSellList;
		DefaultTableModel tableModel3 = new DefaultTableModel(ListToArray.mquoteListToArray(monitorQuoteSellList), columnNames);
		table2.setModel(tableModel3);
		hideColumn(table2,5);
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