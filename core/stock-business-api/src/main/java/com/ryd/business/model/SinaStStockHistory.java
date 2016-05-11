package com.ryd.business.model;

import com.bugull.mongo.BuguEntity;
import com.bugull.mongo.annotations.Entity;

import java.io.Serializable;

/**
 * <p>标题:sina股票信息-历史数据表</p>
 * <p>描述:sina股票信息-历史数据表</p>
 * 包名：com.ryd.basecommon.common
 * 创建人：songby
 * 创建时间：2016/4/21 10:29
 */
@Entity
public class SinaStStockHistory extends SinaStStock implements Serializable, BuguEntity {
}
