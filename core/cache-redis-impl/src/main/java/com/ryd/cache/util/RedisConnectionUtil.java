package com.ryd.cache.util;

import com.ryd.basecommon.util.BaseUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 * <p>Title: Redis连接池工具类</p>
 * <p>Description: <<p>
 * <p>Created by jessen on 2015/9/11.</p>
 * @version 1.0
 */
public class RedisConnectionUtil {
    private static JedisPool pool = null;
    private Jedis jedis;

    /**
     * 构建redis连接池
     *
     * @return JedisPool
     */
    public static JedisPool getPool() {
        if (pool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
            //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
            config.setMaxActive(500);
            //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            config.setMaxIdle(5);
            //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
            config.setMaxWait(1000 * 100);
            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);

            String redisIp = BaseUtils.getProValByKey("redis.properties", "redis.ip", "192.168.10.81");
            Integer redisPort = Integer.parseInt(BaseUtils.getProValByKey("redis.properties", "redis.port", "6379"));
//            pool = new JedisPool(config, "192.168.10.81", 6379);
            pool = new JedisPool(config, redisIp, redisPort);
        }
        return pool;
    }

    /**
     * 返还到连接池
     *
     * @param pool
     * @param redis
     */
    public static void returnResource(JedisPool pool, Jedis redis) {
        if (redis != null) {
            try {
                pool.returnResource(redis);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public static String get(String key){
        String value = null;

        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }

        return value;
    }

    public static void main(String[] args) {
        String value = null;
//
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
//
//            value = jedis.get(key);
            jedis.set("hallListMap3d01adf0-47ae-11e5-a2d5-000c2946bda510","test3");
            System.out.println(get("hallListMap3d01adf0-47ae-11e5-a2d5-000c2946bda510"));
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Jedis jedis = new Jedis("localhost");
//        Pipeline pipeline = jedis.pipelined();
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < 100000; i++) {
//            pipeline.set("p" + i, "p" + i);
//        }
//        List<Object> results = pipeline.syncAndReturnAll();
//        long end = System.currentTimeMillis();
//        System.out.println("Pipelined SET: " + ((end - start)/1000.0) + " seconds");
//        jedis.disconnect();
    }
}
