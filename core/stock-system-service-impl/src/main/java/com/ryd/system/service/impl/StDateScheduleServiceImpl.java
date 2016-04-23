package com.ryd.system.service.impl;

import com.ryd.basecommon.common.CacheConstant;
import com.ryd.basecommon.common.Constant;
import com.ryd.basecommon.util.DateUtils;
import com.ryd.basecommon.util.FestivalDateUtil;
import com.ryd.cache.service.ICacheService;
import com.ryd.system.dao.StDateScheduleDao;
import com.ryd.system.model.StDateSchedule;
import com.ryd.system.service.StDateScheduleService;
import com.ryd.system.service.StSystemParamService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>标题:</p>
 * <p>描述:</p>
 * 包名：com.ryd.system.service.impl
 * 创建人：songby
 * 创建时间：2016/4/22 16:38
 */
@Service
public class StDateScheduleServiceImpl implements StDateScheduleService {

    @Autowired
    private StDateScheduleDao stDateScheduleDao;

    @Autowired
    private StSystemParamService stSystemParamService;

    @Autowired
    private ICacheService iCacheService;

    @Override
    public boolean addSchedule(StDateSchedule schedule) {
        return stDateScheduleDao.add(schedule) > 0;
    }

    @Override
    public boolean updateSchedule(StDateSchedule schedule) {
        iCacheService.remove(CacheConstant.CACHEKEY_DATE_SCHEDULE_FESTIVALDAY,null);
        return stDateScheduleDao.update(schedule) > 0;
    }

    @Override
    public boolean deleteSchedule(StDateSchedule schedule) {

        iCacheService.remove(CacheConstant.CACHEKEY_DATE_SCHEDULE_FESTIVALDAY,null);

        return stDateScheduleDao.deleteTById(schedule) > 0;
    }

    @Override
    public boolean getIsFestival() {

        Boolean value = null;

        value = (Boolean) iCacheService.getObjectByKey(CacheConstant.CACHEKEY_DATE_SCHEDULE_FESTIVALDAY, null);
        if (value!=null) {
            return value;
        }

        value = stDateScheduleDao.getScheduleByDate(new Date());

        iCacheService.setObjectByKey(CacheConstant.CACHEKEY_DATE_SCHEDULE_FESTIVALDAY, value);

        return value;
    }

    @Override
    public List<StDateSchedule> getScheduleList(StDateSchedule schedule, int pageIndex, int limit) {
        int offset = (pageIndex - 1)*limit;
        return stDateScheduleDao.getTList(schedule,limit,offset);
    }

    @Override
    public boolean getIsCanTrade() {
        if(Constant.STQUOTE_TRADE_TIMECOMPARE_1.intValue() == getDateAndTimeJudge()){
            return true;
        }
        return false;
    }

    @Override
    public boolean getIsCanQuote() {
        if(getDateAndTimeJudge() == Constant.STQUOTE_TRADE_TIMECOMPARE_1.intValue() ||
                getDateAndTimeJudge() == Constant.STQUOTE_TRADE_TIMECOMPARE_2.intValue()){
            return true;
        }
        return false;
    }

    @Override
    public boolean getDateIsWorkDayJudge() {
        Calendar calNow = Calendar.getInstance();
        //如果当前时间是工作日
        boolean isWorkDay = !getIsFestival();
        return isWorkDay;
    }

    private int getDateAndTimeJudge() {
        //当前时间
        Calendar calNow = Calendar.getInstance();

        String openTime = stSystemParamService.getParamByKey(CacheConstant.CACHEKEY_SYSTEM_CONFIG_OPEN_TIME);
        String restTimeStart = stSystemParamService.getParamByKey(CacheConstant.CACHEKEY_SYSTEM_CONFIG_REST_TIME_START);
        String restTimeEnd = stSystemParamService.getParamByKey(CacheConstant.CACHEKEY_SYSTEM_CONFIG_REST_TIME_END);
        String closeTime = stSystemParamService.getParamByKey(CacheConstant.CACHEKEY_SYSTEM_CONFIG_CLOSE_TIME);
        //如果当前时间是工作日
        if (!getIsFestival()) {

            long tnow = calNow.getTime().getTime();
            //开盘时间
            long t9 = DateUtils.getSetHourTime(openTime).getTime();
            //休盘时间开始
            long t11 = DateUtils.getSetHourTime(restTimeStart).getTime();
            //休盘时间结束
            long t13 = DateUtils.getSetHourTime(restTimeEnd).getTime();
            //收盘时间
            long t15 = DateUtils.getSetHourTime(closeTime).getTime();

            //如果当前时间在上午9：30~11：30之间，或者下午1：00~3：00之间，可以交易和报价
            if ((t9 < tnow && tnow < t11) || (t13 < tnow && tnow < t15)) {
                return Constant.STQUOTE_TRADE_TIMECOMPARE_1;
            } else if ((t11 < tnow && tnow < t13)) {//如果当前时间在11：30~13：00之间只允许报价
                return Constant.STQUOTE_TRADE_TIMECOMPARE_2;
            } else {//之外的时间，不允许报价，不允许交易
                return Constant.STQUOTE_TRADE_TIMECOMPARE_3;
            }
        }
        return Constant.STQUOTE_TRADE_TIMECOMPARE_3;
    }

}
