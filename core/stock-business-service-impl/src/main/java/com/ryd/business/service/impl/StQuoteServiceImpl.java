package com.ryd.business.service.impl;

import com.ryd.business.dto.SearchQuoteDTO;
import com.ryd.business.model.StQuote;
import com.ryd.business.service.StQuoteService;

import java.util.List;

/**
 * <p>标题:报价业务实现类</p>
 * <p>描述:报价业务实现类</p>
 * 包名：com.ryd.business.service.impl
 * 创建人：chenji
 * 创建时间：2016/4/25 10:11
 */
public class StQuoteServiceImpl implements StQuoteService {
    @Override
    public Integer saveQuoteList(List<StQuote> quoteList) {
        Integer quoteFlag = 0;
        // 验证报价参数
        // 验证报价状态（是否可以报价）
//        1、是否节假日getIsFestival 2、是否交易时间
        // 将报价保存到mysql数据库 失败后回滚
        // 将报价加入队列 失败后回滚

        // 将报价放入Kafka 失败后不回滚

        return quoteFlag;
    }

    @Override
    public Integer updateQuoteList(List<StQuote> quoteList) {
        return null;
    }

    @Override
    public List<StQuote> findQuoteList(SearchQuoteDTO searchQuoteDTO) {
        return null;
    }

    @Override
    public List<StQuote> findQuoteQueueByStock(SearchQuoteDTO searchQuoteDTO) {
        // 从缓存中获取
        return null;
    }
}
