package com.ryd.server.stocktrader.swing.frame;

import com.ryd.basecommon.protocol.protobuf.DiyNettyMessage;
import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.business.model.StAccount;
import com.ryd.business.model.StPosition;
import com.ryd.business.model.StStock;
import com.ryd.business.model.StStockConfig;
import com.ryd.server.stocktrader.swing.common.ClientConstants;
import com.ryd.server.stocktrader.swing.common.ListToArray;
import com.ryd.server.stocktrader.swing.listener.QuoteListListener;
import com.ryd.server.stocktrader.swing.listener.StockSearchListener;
import com.ryd.server.stocktrader.swing.service.impl.MessageServiceImpl;
import com.ryd.server.stocktrader.utils.TestParamBuilderUtil;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * <p>标题:</p>
 * <p>描述:</p>
 * 包名：com.ryd.server.stocktrader.swing.frame
 * 创建人：songby
 * 创建时间：2016/5/7 14:27
 */
public class MonitorFrame extends JFrame {

    public static String[] columnName = {"股票代码", "股票名称", "现价", "今开", "昨收", "最高", "最低","成交量","成交额"};


    private static MonitorFrame monitorFrame;

    public static MonitorFrame instance() {
        if (monitorFrame == null)
            monitorFrame = new MonitorFrame();
        return monitorFrame;
    }

    GridBagLayout g = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
    Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸

    public MonitorFrame() {
        super("监控信息");
        addComponent();

    }

    public void addComponent() {

        createTopPanel();
        createMiddlePanel();
        createBottomPanel();

        JPanel panelContainer = new JPanel();

        setSize(screenSize.width, screenSize.height-30);
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
    }

    JPanel topPanel,middlePanel,bottomPanel;

    JTable table2;

    JTextField stCode;
    JButton search;

    public void createTopPanel() {
        topPanel = new JPanel();

    }

    public void createMiddlePanel() {

        middlePanel = new JPanel();
        middlePanel.setBorder(BorderFactory.createTitledBorder("股票行情"));
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));

        //-------------------------------------------------------------2
        JPanel panel4 = new JPanel();
        panel4.setLayout(new BoxLayout(panel4, BoxLayout.X_AXIS));

        JLabel stCodeLab = new JLabel("股票代码:");
        panel4.add(stCodeLab);
        stCode = new JTextField();
        stCode.setMaximumSize(new Dimension(200, 30));
        panel4.add(stCode);
        search = new JButton("查询");

        panel4.add(search);


        JButton stockInfoButton = new JButton("股票队列详细");
        stockInfoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table2.getSelectedRow();
                if (selectedRow != -1) {
                    String stockCode = (String) table2.getValueAt(selectedRow, 0);
                    StStockConfig st = ClientConstants.stStockConfigMapKeyCode.get(stockCode);
                    getData(st.getId());
                } else {
                    JOptionPane.showMessageDialog(null, "请选择对应股票", "提示",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel4.add(stockInfoButton);

        panel4.add(Box.createRigidArea(new Dimension(screenSize.width-380,20)));

        middlePanel.add(panel4);

        table2 = new JTable();
        JScrollPane scrollPane2 = new JScrollPane(table2);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        StockSearchListener searchListener = new StockSearchListener(stCode, table2, search);
        search.addActionListener(searchListener);

        middlePanel.add(scrollPane2);
        //-------------------------------------------------------------2
    }

    public void createBottomPanel() {

        JButton recordButton = new JButton("监控队列");
        recordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 getData("");
            }
        });


        JButton refreshButton = new JButton("刷新");
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                DiyNettyMessage.NettyMessage.Builder builder = TestParamBuilderUtil.login(ClientConstants.stAccount.getAccountNum(), ClientConstants.stAccount.getPassword());
                MessageServiceImpl.sendMessage(builder.build());
            }
        });

        JButton closeButton = new JButton("退出");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        bottomPanel = new JPanel();

        bottomPanel .setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        JPanel buttonPanel = new JPanel();

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        buttonPanel.add(recordButton);

        buttonPanel.add(Box.createHorizontalGlue());

        buttonPanel.add(refreshButton);

        buttonPanel.add(closeButton);

        bottomPanel .add(Box.createVerticalStrut(10));

        bottomPanel .add(buttonPanel);

        bottomPanel .add(Box.createVerticalStrut(10));
    }

    public void panelAdd(JPanel panel, GridBagLayout g, GridBagConstraints c, JComponent jc, int x, int y, int gw, int gh) {
        c.gridx = x;
        c.gridy = y;
        c.anchor = GridBagConstraints.WEST;
        c.gridwidth = gw;
        c.gridheight = gh;
        panel.add(jc, c);
    }

    public void open() {

        DefaultTableModel tableModel2 = new DefaultTableModel(ListToArray.monitorStockListToArray(ClientConstants.monitorStockInfoList),columnName);
        table2.setModel(tableModel2);

        StAccount acc = ClientConstants.stAccount;

        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setGroupingUsed(false);

        setVisible(true);
        LoginFrame.instance().dispose();

    }

    public void getData(String stockId){
        DiyNettyMessage.NettyMessage.Builder builderb = TestParamBuilderUtil.getStockInfoBuilder(ApplicationConstants.NETTYMESSAGE_ID_SINGLESTOCKTRADEQUEUE, ApplicationConstants.STOCK_QUOTETYPE_SELL,stockId);
        MessageServiceImpl.sendMessage(builderb.build());

        DiyNettyMessage.NettyMessage.Builder builders = TestParamBuilderUtil.getStockInfoBuilder(ApplicationConstants.NETTYMESSAGE_ID_SINGLESTOCKTRADEQUEUE, ApplicationConstants.STOCK_QUOTETYPE_BUY, stockId);
        MessageServiceImpl.sendMessage(builders.build());

        DiyNettyMessage.NettyMessage.Builder builderr = TestParamBuilderUtil.getStockInfoBuilder(ApplicationConstants.NETTYMESSAGE_ID_SINGLESTOCKTRADERECORD, 0, stockId);
        MessageServiceImpl.sendMessage(builderr.build());
    }
}
