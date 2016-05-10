package com.ryd.server.stocktrader.swing.listener;

import com.ryd.business.model.StStock;
import com.ryd.server.stocktrader.swing.common.ClientConstants;
import com.ryd.server.stocktrader.swing.common.ListToArray;
import com.ryd.server.stocktrader.swing.frame.MainFrame;
import com.ryd.server.stocktrader.swing.frame.MonitorFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>标题:</p>
 * <p>描述:</p>
 * 包名：com.ryd.server.stocktrader.swing.listener
 * 创建人：songby
 * 创建时间：2016/5/10 11:19
 */
public class MonitorStockSearchListener  extends MouseAdapter implements ActionListener {
    private JTextField stockCode;
    private JButton search;
    private JTable table;

    public MonitorStockSearchListener(JTextField stockCode,JTable table,
                               JButton search) {
        this.stockCode = stockCode;
        this.table = table;
        this.search = search;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == search) {
            if (stockCode.getText().equals("")) {
                table.setModel(new DefaultTableModel(ListToArray.monitorStockListToArray(ClientConstants.monitorStockInfoList), MonitorFrame.columnName));
            }else{

                StStock sst = ClientConstants.monitorStockInfoMap.get(stockCode.getText());

                if(sst == null){
                    JOptionPane.showMessageDialog(null, "没有这支股票", "提示",
                            JOptionPane.ERROR_MESSAGE);
                }else{
                    List<StStock> list = new ArrayList<StStock>();
                    list.add(sst);

                    table.setModel(new DefaultTableModel(ListToArray.monitorStockListToArray(list), MonitorFrame.columnName));
                }
            }
        }
    }
}
