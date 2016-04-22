package com.ryd.cache.service;

/**
 * <p>Title: 缓存接口</p>
 * <p>Description: <<p>
 * <p>Created by jessen on 2015/9/11.</p>
 * @version 1.0
 */
public interface ICacheService {
    /**
     * 将字符串放入缓存
     * @param key
     * @param str
     * @return
     */
    public boolean setStringByKey(String key, String str);

    /**
     * 将字符串放入缓存-Map关系
     * @param key
     * @param field
     * @param str
     * @return
     */
    public boolean setStringByKey(String key, String field, String str);

    /**
     * 将字符串放入缓存-Map关系-缓存时间
     * @param key
     * @param field
     * @param str
     * @return
     */
    public boolean setStringByKey(String key, String field, String str, int time);

    /**
     * 将字符串放入缓存
     * @param key
     * @param str
     * @param time 单位（秒）
     * @return
     */
    public boolean setStringByKey(String key, String str, int time);

    /**
     * 将对象放入缓存
     * @param key
     * @param obj
     * @return
     */
    public boolean setObjectByKey(String key, Object obj);

    /**
     * 将对象放入缓存
     * @param key
     * @param obj
     * @param time 单位（秒）
     * @return
     */
    public boolean setObjectByKey(String key, Object obj, int time);

    /**
     * 将对象以键值对的方式放入Map格式的缓存
     * @param key Map的键
     * @param field 单行的键
     * @param value 值
     * @param time 时间
     * @return
     */
    public boolean setObjectByKey(String key, Object field, Object value, int time);

    /**
     * 对key增减
     * @param key
     * @param num
     * @return
     */
    public long incrOrDecrByKey(String key, int num);

    /**
     * 通过key获取缓存
     * @param key
     * @param _default
     * @return
     */
    public String getStringByKey(String key, String _default);

    /**
     * 通过key\field获取缓存-Map格式
     * @param key
     * @param field
     * @param _default
     * @return
     */
    public String getStringByKey(String key, String field, String _default);

    /**
     * 通过key获取缓存
     * @param key
     * @param _default
     * @return
     */
    public Object getObjectByKey(String key, Object _default);

    /**
     * 通过key、field获取(Map)缓存
     * @param key
     * @param field
     * @return
     */
    public Object getObjectByKey(String key, Object field, Object _default);

    /**
     * 通过key删除缓存
     * @param key
     * @return
     */
    public boolean remove(String key);

    /**
     * 通过key、field删除缓存
     * @param key
     * @param field
     * @return
     */
    public boolean remove(String key, String field);

    /**
     * 删除Object、Map中的field
     * @param key
     * @param field
     * @return
     */
    public boolean removeObject(Object key, Object field);

    /**
     * 通过key、field删除缓存
     * @param key
     * @param field
     * @return
     */
    public boolean remove(String key, Object field);

    /**
     * 清除缓存
     * @return
     */
    public boolean clearAll();
}
