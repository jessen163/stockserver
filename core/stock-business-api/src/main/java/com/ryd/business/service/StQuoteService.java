package com.ryd.business.service;

import com.ryd.business.dto.SearchQuoteDTO;
import com.ryd.business.model.StQuote;

import java.util.List;

/**
 * <p>标题:报价Service</p>
 * <p>描述:依赖于股票、账户</p>
 * 包名：com.ryd.business.service
 * 创建人：chenji
 * 创建时间：2016/4/24 10:11
 */
public interface StQuoteService {
    /**
     * 批量保存报价信息
     * @param quoteList
     * @return
     */
    public Integer saveQuoteList(List<StQuote> quoteList);

    /**
     * 撤销报价信息
     * @param quoteList
     * @return
     */
    public Integer updateQuoteList(List<StQuote> quoteList);

    /**
     * 查询单只股票的报价信息(买、卖)
     * @return
     */
    public List<StQuote> findQuoteQueueByStock(SearchQuoteDTO searchQuoteDTO);

    /**
     * 查询单只股票的报价信息(买、卖)
     * @return
     */
    public StQuote findFirstQuoteByStock(SearchQuoteDTO searchQuoteDTO);

    /**
     * 查询报价信息，接收参数
     * @return
     */
    public List<StQuote> findQuoteList(SearchQuoteDTO searchQuoteDTO);

    /**
     * 查询报价信息，接收参数
     * @return
     */
    public List<StQuote> findQuoteList(int pageid, int size);

    /**
     * 天津
     * @param stQuote
     * @return
     */
    public boolean addQuoteToQueue(StQuote stQuote);

    /**
     * 从队列中删除报价，同时修改报价状态
     * @param buyQuote
     * @param sellQuote
     * @return
     */
    public boolean deleteQuoteFromQueue(StQuote buyQuote, StQuote sellQuote);
}