package com.ryd.business.dto;

import com.ryd.business.model.StStock;

import java.io.Serializable;
import java.util.List;

/**
 * 详细股票信息-包括股票价格、当日价格行情、成交量
 * Created by chenji on 2016/4/28.
 */
public class StStockDetailDTO implements Serializable {
    private StStock stStock;
    // 股票当日价格行情
    private List<StockPriceDTO> stockPriceDTOList;
    // 股票当日成交量
    private List<StStockTurnoverDTO> stStockTurnoverDTOList;

    public StStock getStStock() {
        return stStock;
    }

    public void setStStock(StStock stStock) {
        this.stStock = stStock;
    }

    public List<StockPriceDTO> getStockPriceDTOList() {
        return stockPriceDTOList;
    }

    public void setStockPriceDTOList(List<StockPriceDTO> stockPriceDTOList) {
        this.stockPriceDTOList = stockPriceDTOList;
    }

    public List<StStockTurnoverDTO> getStStockTurnoverDTOList() {
        return stStockTurnoverDTOList;
    }

    public void setStStockTurnoverDTOList(List<StStockTurnoverDTO> stStockTurnoverDTOList) {
        this.stStockTurnoverDTOList = stStockTurnoverDTOList;
    }

    @Override
    public String toString() {
        return "StStockDetailDTO{" +
                "stStock=" + stStock +
                ", stockPriceDTOList=" + stockPriceDTOList +
                ", stStockTurnoverDTOList=" + stStockTurnoverDTOList +
                '}';
    }
}
