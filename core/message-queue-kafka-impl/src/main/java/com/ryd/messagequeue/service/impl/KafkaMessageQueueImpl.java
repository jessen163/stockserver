package com.ryd.messagequeue.service.impl;


import com.ryd.basecommon.util.BaseUtils;
import com.ryd.messagequeue.dto.MessageQueueDTO;
import com.ryd.messagequeue.service.IMessageQueue;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
* <p>Title: 消息队列实现类-kafka</p>
* <p>Description: <<p>
* <p>Created by jessen on 2015/9/6.</p>
*
* @version 1.0
*/
@Service
public class KafkaMessageQueueImpl implements IMessageQueue {

    private static Logger logger = LoggerFactory.getLogger(KafkaMessageQueueImpl.class);

    public static Producer<String,byte[]> producer = null;

    public static Producer getInstance(){
        return producer;
    }

    static {
        Properties props = new Properties();
//        "192.168.10.81:9092,192.168.10.81:9093,192.168.10.81:9094"
        props.put("metadata.broker.list", BaseUtils.getProValByKey("kafka.properties", "metadata.broker.list", "192.168.10.81:9092"));
        props.put("serializer.class", "kafka.serializer.DefaultEncoder");
//        props.put("partitioner.class", "example.producer.SimplePartitioner");
        props.put("request.required.acks", "1");

        ProducerConfig config = new ProducerConfig(props);

        producer = new Producer<String,byte[]>(config);
    }

    @Override
    public boolean sendMessage(String topic, byte[] data) {
        logger.debug("topic:{},data:{}",topic, data);
        Producer producer = getInstance();
        try {
            KeyedMessage<String, byte[]> message = new KeyedMessage<String, byte[]>(topic, data);
            producer.send(message);
        } catch (Exception e) {
            logger.error("sendMessage:", e);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean sendMessage(String topic, String str) {
        return sendMessage(topic, str.getBytes());
    }

    @Override
    public boolean sendMessage(String topic, MessageQueueDTO messageQueueDTO) {
        // TODO 待实现
        return false;
    }
}
