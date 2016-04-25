package com;

import com.ryd.basecommon.util.UUIDUtils;
import com.ryd.business.dao.StAccountDao;
import com.ryd.business.dao.StStockConfigDao;
import com.ryd.business.model.StAccount;
import com.ryd.business.model.StStockConfig;
import com.ryd.business.mybatis.StAccountMapper;
import com.ryd.business.service.StAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>标题:</p>
 * <p>描述:</p>
 * 包名：java
 * 创建人：songby
 * 创建时间：2016/4/20 10:42
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class StAccountServiceTest {

    @Autowired
    public StAccountDao stAccountDao;


    @Test
    public void testAddUser(){
        StAccount a = new StAccount();
        a.setId(UUIDUtils.uuidTrimLine());
        a.setAccountName("A");
        a.setAccountNum("1");
        a.setAccountLevel((short) 1);
        a.setAccountType((short)1);
        a.setPassword("123");
        a.setCreatetime(System.currentTimeMillis());
        stAccountDao.add(a);

    }

    @Test
    public void testGetUser(){

        StAccount stAccount = stAccountDao.getStAccountByLogin("12","1234");
        System.out.println(stAccount.getAccountNum());
//        StAccount a = new StAccount();
//        a.setAccountNum("12");
//        List<StAccount> list = stAccountDao.getStAccountList(a,10,0);
//        for(StAccount st : list){
//            System.out.println(st.getAccountName());
//        }
    }

}
