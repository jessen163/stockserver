package com.ryd.business.mybatis;

import com.ryd.business.model.StQuote;

public interface StQuoteMapper {
    int insert(StQuote record);

    int insertSelective(StQuote record);
}