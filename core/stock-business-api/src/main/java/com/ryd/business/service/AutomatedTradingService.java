package com.ryd.business.service;

import com.ryd.business.dto.AutomatedTradingDTO;

/**
 * 自动化交易服务接口
 * Created by chenji on 2016/5/3.
 */
public interface AutomatedTradingService {
    /**
     * 自动化交易
     * @param automatedTradingDTO
     * @return
     */
    public boolean addAutomatedTradingByStock(AutomatedTradingDTO automatedTradingDTO);
}
