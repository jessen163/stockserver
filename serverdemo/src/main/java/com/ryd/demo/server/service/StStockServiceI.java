package com.ryd.demo.server.service;

import com.ryd.demo.server.bean.StStock;

import java.util.List;

/**
 * 股票处理ServiceI
 * Created by Administrator on 2016/4/11.
 */
public interface StStockServiceI {
    /**
     * 查询股票信息
     * @return
     */
    public List<StStock> findStockList();
}
