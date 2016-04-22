package com.ryd.system.service;

import com.ryd.system.model.StDateSchedule;

import java.util.Date;
import java.util.List;

/**
 * <p>标题:特殊时间配置-节假日Service</p>
 * <p>描述:特殊时间配置-节假日Service</p>
 * 包名：com.ryd.system.service
 * 创建人：songby
 * 创建时间：2016/4/22 16:36
 */
public interface StDateScheduleService {


    /**
     * 添加日期
     * @param schedule
     * @return
     */
    public boolean addSchedule(StDateSchedule schedule);

    /**
     * 修改日期
     * @param schedule
     * @return
     */
    public boolean updateSchedule(StDateSchedule schedule);

    /**
     * 删除日期
     * @param id
     * @return
     */
    public boolean deleteSchedule(String id);

    /**
     * 根据类型查询
     * @param type
     * @return
     */
    public List<Date> getScheduleByType(Short type);

    /**
     * 分页查询
     * @param schedule
     * @param pageIndex
     * @param limit
     * @return
     */
    public List<StDateSchedule> getScheduleList(StDateSchedule schedule, int pageIndex, int limit);

    /**
     * 判断交易日交易时间区间
     * @return
     */
    public int getDateAndTimeJudge();

    /**
     * 当前时间是否是工作日
     * @return
     */
    public boolean getDateIsWorkDayJudge();
}
