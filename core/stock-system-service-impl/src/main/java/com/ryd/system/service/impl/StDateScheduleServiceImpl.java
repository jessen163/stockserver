package com.ryd.system.service.impl;

import com.ryd.basecommon.common.Constant;
import com.ryd.basecommon.util.DateUtils;
import com.ryd.basecommon.util.FestivalDateUtil;
import com.ryd.system.dao.StDateScheduleDao;
import com.ryd.system.model.StDateSchedule;
import com.ryd.system.service.StDateScheduleService;
import com.ryd.system.service.StSystemParamService;
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
    String openTime="",restTimeStart="",restTimeEnd="",closeTime="";
    List<Date> workday = null;
    List<Date> festival = null;

    public StDateScheduleServiceImpl(){
        openTime = stSystemParamService.getParamByKey(Constant.SYSPARAM_OPEN_TIME);
        restTimeStart = stSystemParamService.getParamByKey(Constant.SYSPARAM_REST_TIME_START);
        restTimeEnd = stSystemParamService.getParamByKey(Constant.SYSPARAM_REST_TIME_END);
        closeTime = stSystemParamService.getParamByKey(Constant.SYSPARAM_CLOSE_TIME);
        festival = stDateScheduleDao.getScheduleByType(Constant.DATE_SCHEDULE_FESTIVALDAY);
        workday = stDateScheduleDao.getScheduleByType(Constant.DATE_SCHEDULE_SEPCIAL_WORKDAY);
    }

    @Override
    public boolean addSchedule(StDateSchedule schedule) {
        return stDateScheduleDao.add(schedule) > 0;
    }

    @Override
    public boolean updateSchedule(StDateSchedule schedule) {
        return stDateScheduleDao.update(schedule) > 0;
    }

    @Override
    public boolean deleteSchedule(String id) {
        StDateSchedule schedule = new StDateSchedule();
        schedule.setId(id);
        return stDateScheduleDao.deleteTById(schedule) > 0;
    }

    @Override
    public List<Date> getScheduleByType(Short type) {
        return stDateScheduleDao.getScheduleByType(type);
    }

    @Override
    public List<StDateSchedule> getScheduleList(StDateSchedule schedule, int pageIndex, int limit) {
        int offset = (pageIndex - 1)*limit;
        return stDateScheduleDao.getTList(schedule,limit,offset);
    }

    @Override
    public int getDateAndTimeJudge() {
        //当前时间
        Calendar calNow = Calendar.getInstance();

        //如果当前时间是工作日
        if (FestivalDateUtil.isWorkDay(calNow.getTime(), workday, festival)) {

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

    @Override
    public boolean getDateIsWorkDayJudge() {
        Calendar calNow = Calendar.getInstance();
        //如果当前时间是工作日
        return FestivalDateUtil.isWorkDay(calNow.getTime(), workday, festival);
    }
}
