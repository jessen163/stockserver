package com.ryd.business.model;

import com.bugull.mongo.BuguEntity;
import com.bugull.mongo.annotations.Entity;
import com.bugull.mongo.annotations.Id;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * <p>标题:股票信息-历史数据表</p>
 * <p>描述:股票信息-历史数据表</p>
 * 包名：com.ryd.basecommon.common
 * 创建人：songby
 * 创建时间：2016/4/21 10:29
 */
@Entity
public class StStockHistory extends StStock implements Serializable, BuguEntity{

    private static final long serialVersionUID = -5808387262109584648L;


}