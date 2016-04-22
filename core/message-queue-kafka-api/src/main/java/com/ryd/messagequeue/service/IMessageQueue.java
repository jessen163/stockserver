package com.ryd.messagequeue.service;

import com.ryd.messagequeue.dto.MessageQueueDTO;

/**
 * <p>Title: 消息队列接口</p>
 * <p>Description: <<p>
 * <p>Created by jessen on 2015/9/6.</p>
 * @version 1.0
 */
public interface IMessageQueue {
    /**
     * 发送字节
     * @param data
     * @return
     */
    public boolean sendMessage(String topic, byte[] data);

    /**
     * 发送字符串
     * @param str
     * @return
     */
    public boolean sendMessage(String topic, String str);

    /**
     * 发送消息实体
     * @param messageQueueDTO
     * @return
     */
    public boolean sendMessage(String topic, MessageQueueDTO messageQueueDTO);
}
