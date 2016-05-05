package com.ryd.server.stocktrader.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
* <p>Title: 时间日志-通知</p>
* <p>Description: <<p>
* <p>Company: SiChuangKeJi</p>
* <p>Created by jessen on 2015/7/21.</p>
*
* @version 1.0
*/
@Aspect
@Component
public class TimeLogAdvice {

    private static Logger logger = LoggerFactory.getLogger(TimeLogAdvice.class);

    public TimeLogAdvice() {
        logger.debug("TimeLogAdvice constuct");
    }

    /**
     * Pointcut
     * 定义Pointcut，Pointcut的名称为aspectjMethod()，此方法没有返回值和参数
     * 该方法就是一个标识，不进行调用* com.tgb.aop.*.find*(..)execution(* com.xyz.service..*.*(..))
     */
    @Pointcut("execution(* com.ryd.*.service.impl.*.*(..))")
    public void aspectjMethod(){
        logger.debug("aspectjMethod");
    };

    /**
     * Before
     * 在核心业务执行前执行，不能阻止核心业务的调用。
     * @param joinPoint
     */
    @Before("aspectjMethod()")
    public void beforeAdvice(JoinPoint joinPoint) {
        logger.debug(joinPoint.getTarget().getClass() + ":" + joinPoint.getSignature().getName() + " start ...");
//        System.out.println(joinPoint.getTarget().getClass() + ":" + joinPoint.getSignature().getName() + " start ...");
//        Object obj[] = joinPoint.getArgs();
//        for(Object o :obj){
//            logger.debug("o:"+o);
//        }
    }

    /**
     * After
     * 核心业务逻辑退出后（包括正常执行结束和异常退出），执行此Advice
     * @param joinPoint
     */
    @After(value = "aspectjMethod()")
    public void afterAdvice(JoinPoint joinPoint) {
        logger.debug(joinPoint.getTarget().getClass()+":"+joinPoint.getSignature().getName()+" end ...");
    }

    /**
     * Around
     * 手动控制调用核心业务逻辑，以及调用前和调用后的处理,
     *
     * 注意：当核心业务抛异常后，立即退出，转向AfterAdvice
     * 执行完AfterAdvice，再转到ThrowingAdvice
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around(value = "aspectjMethod()")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();

        //调用核心逻辑
        Object retVal = pjp.proceed();
        long end = System.currentTimeMillis();
//        logger.debug("耗时：" + (end-start) + "ms");
        if ((end-start)/1000>1) {
            logger.warn(pjp.getTarget().getClass()+":"+pjp.getSignature().getName()+" 耗时超过(待优化)："+(end-start)+"ms");
        }
//        logger.debug(pjp.getSignature().getName()+" 返回值：" + retVal);
        return retVal;
    }

    /**
     * AfterReturning
     * 核心业务逻辑调用正常退出后，不管是否有返回值，正常退出后，均执行此Advice
     * @param joinPoint
     */
    @AfterReturning(value = "aspectjMethod()", returning = "retVal")
    public void afterReturningAdvice(JoinPoint joinPoint, String retVal) {
//        logger.debug(joinPoint.getTarget().getClass()+":"+joinPoint.getSignature().getName()+" 返回值：" + retVal);
    }

    /**
     * 核心业务逻辑调用异常退出后，执行此Advice，处理错误信息
     *
     * 注意：执行顺序在Around Advice之后
     * @param joinPoint
     * @param ex
     */
    @AfterThrowing(value = "aspectjMethod()", throwing = "ex")
    public void afterThrowingAdvice(JoinPoint joinPoint, Exception ex) {
        logger.error(joinPoint.getSignature().getName(), ex);
    }
}
