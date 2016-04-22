package com.ryd.cache.service.impl;

import com.ryd.basecommon.util.FileUtils;
import com.ryd.cache.service.ICacheService;
import com.ryd.cache.util.RedisConnectionUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * <p>Title: 缓存实现类-Redis版本</p>
 * <p>Description: <<p>
 * <p>Created by jessen on 2015/9/11.</p>
 * @version 1.0
 */
@Service
public class RedisCacheServiceImpl implements ICacheService {
    private static final Logger logger = LoggerFactory.getLogger(RedisCacheServiceImpl.class);

    private static JedisPool jedisPool = null;

    static {
        jedisPool = RedisConnectionUtil.getPool();
    }

    public JedisPool getJedis() {
        return jedisPool;
    }

    @Override
    public boolean setStringByKey(String key, String str) {
        this.setObjectByKey(key, str);
        return true;
    }

    @Override
    public boolean setStringByKey(String key, String field, String str) {
        Jedis jedis = null;
        try {
            jedis = getJedis().getResource();
            jedis.hsetnx(key, field, str);
        } catch (Exception e) {
            logger.error("setStringByKey String key, String field, String str", e);
            return false;
        } finally {
            RedisConnectionUtil.returnResource(getJedis(), jedis);
        }
        return true;
    }

    @Override
    public boolean setStringByKey(String key, String field, String str, int time) {
        Jedis jedis = null;
        try {
            jedis = getJedis().getResource();
            jedis.hsetnx(key, field, str);
            jedis.expire(key, time);
        } catch (Exception e) {
            logger.error("setStringByKey String key, String field, String str, int time", e);
            return false;
        } finally {
            RedisConnectionUtil.returnResource(getJedis(), jedis);
        }
        return true;
    }

    @Override
    public boolean setStringByKey(String key, String str, int time) {
        this.setObjectByKey(key, str, time);
        return true;
    }

    @Override
    public boolean setObjectByKey(String key, Object obj) {
        Jedis jedis = null;
        try {
            jedis = getJedis().getResource();
            if (obj==null) {
                jedis.del(FileUtils.objectToByte(key));
            } else {
                jedis.set(FileUtils.objectToByte(key), FileUtils.objectToByte(obj));
            }
        } catch (Exception e) {
            logger.error("setObjectByKey String key, Object obj", e);
            return false;
        } finally {
            RedisConnectionUtil.returnResource(getJedis(), jedis);
        }

        return true;
    }

    @Override
    public boolean setObjectByKey(String key, Object obj, int time) {
        Jedis jedis = null;
        try {
            jedis = getJedis().getResource();
            if (obj==null) {
                jedis.del(FileUtils.objectToByte(key));
            } else {
                jedis.set(FileUtils.objectToByte(key), FileUtils.objectToByte(obj));
                jedis.expire(FileUtils.objectToByte(key), time);
            }
        } catch (Exception e) {
            logger.error("setObjectByKey String key, Object obj, int time", e);
            return false;
        } finally {
            RedisConnectionUtil.returnResource(getJedis(), jedis);
        }
        return true;
    }

    @Override
    public boolean setObjectByKey(String key, Object field, Object value, int time) {
        Jedis jedis = null;
        try {
            jedis = getJedis().getResource();
            jedis.hset(FileUtils.objectToByte(key), FileUtils.objectToByte(field), FileUtils.objectToByte(value));
            jedis.expire(FileUtils.objectToByte(key), time);
        } catch (Exception e) {
            logger.error("setObjectByKey String key, Object field, Object value, int time", e);
            return false;
        } finally {
            RedisConnectionUtil.returnResource(getJedis(), jedis);
        }
        return true;
    }

    @Override
    public long incrOrDecrByKey(String key, int num) {
        long currentNum = 0;
        Jedis jedis = null;
        try {
            jedis = getJedis().getResource();
            currentNum = jedis.incrBy(key, num);
        } catch (Exception e) {
            logger.error("incrOrDecrByKey String key, int num", e);
            return currentNum;
        } finally {
            RedisConnectionUtil.returnResource(getJedis(), jedis);
        }
        return currentNum;
    }

    @Override
    public String getStringByKey(String key, String _default) {
        Jedis jedis = null;
        try {
            jedis = getJedis().getResource();
            if (jedis.get(key)==null) {
                return _default;
            }
            return jedis.get(key);
        }  catch (Exception e) {
            logger.error("getStringByKey String key, String _default", e);
            return _default;
        } finally {
            RedisConnectionUtil.returnResource(getJedis(), jedis);
        }
    }

    @Override
    public String getStringByKey(String key, String field, String _default) {
        Jedis jedis = null;
        try {
            jedis = getJedis().getResource();
            String value = jedis.hget(key, field);
            if (StringUtils.isBlank(value)) {
                return _default;
            }
            return value;
        }  catch (Exception e) {
            logger.error("getStringByKey String key, String field, String _default", e);
            return _default;
        } finally {
            RedisConnectionUtil.returnResource(getJedis(), jedis);
        }
    }

    @Override
    public Object getObjectByKey(String key, Object _default) {
        Jedis jedis = null;
        try {
            jedis = getJedis().getResource();
            byte[] keyByte = FileUtils.objectToByte(key);
            if (jedis.get(keyByte)==null) {
                return _default;
            }
            return FileUtils.byteToObject(jedis.get(keyByte));
        }  catch (Exception e) {
            logger.error("getStringByKey String key, Object _default", e);
            return _default;
        } finally {
            RedisConnectionUtil.returnResource(getJedis(), jedis);
        }
    }

    @Override
    public Object getObjectByKey(String key, Object field, Object _default) {
        Jedis jedis = null;
        try {
            jedis = getJedis().getResource();
            byte[] keyByte = FileUtils.objectToByte(key);
            byte[] fieldByte = FileUtils.objectToByte(field);
            if (jedis.hget(keyByte, fieldByte)==null) {
                return _default;
            }
            return FileUtils.byteToObject(jedis.hget(keyByte, fieldByte));
        }  catch (Exception e) {
            logger.error("getObjectByKey String key, Object field, Object value, Object _default", e);
            return _default;
        } finally {
            RedisConnectionUtil.returnResource(getJedis(), jedis);
        }
    }

    @Override
    public boolean remove(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis().getResource();
            if (StringUtils.isNotBlank(key)) {
                byte[] keyByte = FileUtils.objectToByte(key);
                jedis.del(keyByte);
                jedis.del(key);
            }
        }  catch (Exception e) {
            logger.error("remove", e);
            return false;
        } finally {
            RedisConnectionUtil.returnResource(getJedis(), jedis);
        }
        return true;
    }

    @Override
    public boolean remove(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = getJedis().getResource();
            if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(field)) {
                jedis.hdel(key, field);
            }
        }  catch (Exception e) {
            logger.error("removeByField string", e);
            return false;
        } finally {
            RedisConnectionUtil.returnResource(getJedis(), jedis);
        }
        return true;
    }

    @Override
    public boolean removeObject(Object key, Object field) {
        Jedis jedis = null;
        try {
            jedis = getJedis().getResource();
            if (key!=null && field != null) {
            byte[] keyByte = FileUtils.objectToByte(key);
            byte[] fieldByte = FileUtils.objectToByte(field);
                jedis.hdel(keyByte, fieldByte);
            }
        }  catch (Exception e) {
            logger.error("removeByField string", e);
            return false;
        } finally {
            RedisConnectionUtil.returnResource(getJedis(), jedis);
        }
        return true;
    }

    @Override
    public boolean remove(String key, Object field) {
        Jedis jedis = null;
        try {
            jedis = getJedis().getResource();
            jedis = getJedis().getResource();
            if (key!=null && field!=null) {
                byte[] keyByte = FileUtils.objectToByte(key);
                byte[] fieldByte = FileUtils.objectToByte(field);
                jedis.hdel(keyByte, fieldByte);
            }
        }  catch (Exception e) {
            logger.error("removeByField object", e);
            return false;
        } finally {
            RedisConnectionUtil.returnResource(getJedis(), jedis);
        }
        return true;
    }

    @Override
    public boolean clearAll() {
        Jedis jedis = null;
        try {
            jedis = getJedis().getResource();

            jedis.flushAll();
        }  catch (Exception e) {
            logger.error("clearAll", e);
            return false;
        } finally {
            RedisConnectionUtil.returnResource(getJedis(), jedis);
        }
        return true;
    }
}
