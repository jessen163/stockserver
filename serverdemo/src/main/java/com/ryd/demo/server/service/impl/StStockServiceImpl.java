package com.ryd.demo.server.service.impl;

import com.ryd.demo.server.bean.StStock;
import com.ryd.demo.server.common.DataConstant;
import com.ryd.demo.server.service.StStockServiceI;

import java.util.ArrayList;
import java.util.List;

/**
 * 股票处理ServiceImpl
 * Created by Administrator on 2016/4/11.
 */
public class StStockServiceImpl implements StStockServiceI {
    @Override
    public List<StStock> findStockList() {
        List<StStock> stStockList = new ArrayList<StStock>();
        for (String k: DataConstant.stockTable.keySet()) {
            stStockList.add(DataConstant.stockTable.get(k));
        }
        return stStockList;
    }
}
