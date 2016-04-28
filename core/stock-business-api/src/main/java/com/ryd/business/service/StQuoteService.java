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
     * 通过id查询报价
     * @param quote
     * @return
     */
    public StQuote findQuoteById(StQuote quote);

    /**
     * 报价-客户端
     * @param quote
     * @return
     * @throws Exception
     */
    public Integer saveQuoteList(StQuote quote) throws Exception;

    /**
     * 撤销报价信息-客户端
     * @param quote
     * @return
     */
    public Integer updateWithDrawQuote(StQuote quote);

    /**
     * 批量保存报价信息
     * @param quoteList
     * @return
     */
    public Integer saveQuoteList(List<StQuote> quoteList) throws Exception;

    /**
     * 撤销报价信息
     * @param quoteList
     * @return
     */
    public Integer updateQuoteList(List<StQuote> quoteList);

    /**
     * 修改报价
     * @param quote
     * @return
     */
    public Integer updateQuote(StQuote quote);

    /**
     * 查询报价队列信息，接收参数(队列为有序)
     *
     * 通过股票id查询买卖报价列表
     * 通过买卖类型查询所有股票的报价列表
     * @return
     */
    public List<StQuote> findQuoteQueueByStock(SearchQuoteDTO searchQuoteDTO);

    /**
     * 查询单只股票的最靠前的一条报价信息(买/卖)
     * @return
     */
    public StQuote findFirstQuoteByStock(SearchQuoteDTO searchQuoteDTO);

    /**
     * 查询报价信息，接收参数
     *
     * 通过账户查询报价列表，我的今日报价，历史报价
     *
     * @return
     */
    public List<StQuote> findQuoteList(SearchQuoteDTO searchQuoteDTO);

    /**
     * 查询报价信息中的股票ID（哪些股票包含报价信息）
     * @return
     */
    public List<String> findQuoteStockIdList();

    /**
     * 添加
     * @param stQuote
     * @return
     */
    public boolean addQuoteToQueue(StQuote stQuote);

    /**
     * 从队列中删除报价，同时修改报价状态
     * @param stQuote
     * @return
     */
    public boolean deleteQuoteFromQueue(StQuote stQuote);
}