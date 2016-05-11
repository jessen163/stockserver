package com.ryd.server.stocktrader.handle;

import com.ryd.business.service.StQuoteService;
import kafka.api.FetchRequest;
import kafka.api.FetchRequestBuilder;
import kafka.api.PartitionOffsetRequestInfo;
import kafka.common.ErrorMapping;
import kafka.common.TopicAndPartition;
import kafka.javaapi.*;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.message.MessageAndOffset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * 监听-Kafka消息
 *
 */
public class SimulationQuoteCustomerService implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(SimulationQuoteCustomerService.class);
    private List<String> m_replicaBrokers = new ArrayList<String>();
    private long a_maxReads;
    private String a_topic;
    private int a_partition;
    List<String> a_seedBrokers;
    private int a_port;
    private StQuoteService stQuoteService;

    public SimulationQuoteCustomerService() {
        m_replicaBrokers = new ArrayList<String>();
    }

    public SimulationQuoteCustomerService(StQuoteService stQuoteService, long a_maxReads, String a_topic, int a_partition, List<String> a_seedBrokers, int a_port) {
        m_replicaBrokers = new ArrayList<String>();
        this.a_maxReads = a_maxReads;
        this.a_topic = a_topic;
        this.a_partition = a_partition;
        this.a_seedBrokers = a_seedBrokers;
        this.a_port = a_port;
        this.stQuoteService = stQuoteService;
    }

    public void run() {
        this.run(this.stQuoteService, this.a_maxReads, this.a_topic, this.a_partition, this.a_seedBrokers, this.a_port);
    }

    /**
     * 启动消息服务器监听方法
     * @param stQuoteService
     * @param a_maxReads 开始读取的记录索引
     * @param a_topic 读取的消息主题
     * @param a_partition 读取的消息分区
     * @param a_seedBrokers 读取的服务器ip集合
     * @param a_port 读取的服务器端口
     * @throws Exception
     */
    public void run(StQuoteService stQuoteService, long a_maxReads, String a_topic, int a_partition, List<String> a_seedBrokers, int a_port) {
        try {
            // 获取指定Topic partition的元数据
            PartitionMetadata metadata = findLeader(a_seedBrokers, a_port, a_topic, a_partition);
            if (metadata == null) {
                logger.debug("Can't find metadata for Topic and Partition. Exiting");
                return;
            }
            if (metadata.leader() == null) {
                logger.debug("Can't find Leader for Topic and Partition. Exiting");
                return;
            }
            String leadBroker = metadata.leader().host();
            String clientName = "Client_" + a_topic + "_" + a_partition;

            SimpleConsumer consumer = new SimpleConsumer(leadBroker, a_port, 100000, 64 * 1024, clientName);
            long readOffset = getLastOffset(consumer, a_topic, a_partition, kafka.api.OffsetRequest.EarliestTime(), clientName);
            if (readOffset < a_maxReads) {
                readOffset = a_maxReads;
            }
            int numErrors = 0;
//            while (a_maxReads > 0) {
        while (true) {
                if (consumer == null) {
                    consumer = new SimpleConsumer(leadBroker, a_port, 100000, 64 * 1024, clientName);
                }
                FetchRequest req = new FetchRequestBuilder().clientId(clientName).addFetch(a_topic, a_partition, readOffset, 100000).build();
                FetchResponse fetchResponse = consumer.fetch(req);

                if (fetchResponse.hasError()) {
                    numErrors++;
                    // Something went wrong!
                    short code = fetchResponse.errorCode(a_topic, a_partition);
                    logger.debug("Error fetching data from the Broker:" + leadBroker + " Reason: " + code);
                    if (numErrors > 5)
                        break;
                    if (code == ErrorMapping.OffsetOutOfRangeCode()) {
                        // We asked for an invalid offset. For simple case ask for
                        // the last element to reset
                        readOffset = getLastOffset(consumer, a_topic, a_partition, kafka.api.OffsetRequest.LatestTime(), clientName);
                        continue;
                    }
                    consumer.close();
                    consumer = null;
                    leadBroker = findNewLeader(leadBroker, a_topic, a_partition, a_port);
                    continue;
                }
                numErrors = 0;

                long numRead = 0;
                for (MessageAndOffset messageAndOffset : fetchResponse.messageSet(a_topic, a_partition)) {
                    long currentOffset = messageAndOffset.offset();
                    if (currentOffset < readOffset) {
                        logger.debug("Found an old offset: " + currentOffset + " Expecting: " + readOffset);
                        continue;
                    }

                    readOffset = messageAndOffset.nextOffset();
                    ByteBuffer payload = messageAndOffset.message().payload();

                    byte[] bytes = new byte[payload.limit()];
                    payload.get(bytes);
                    logger.debug(a_topic + ":" + String.valueOf(messageAndOffset.offset()) + ": " + new String(bytes, "UTF-8") + "-----------------------------------");

                    // 仅处理消息--BaJieNewMessageDTO
//                    SimpleUserChangeInfoDTO simpleUserChangeInfoDTO = (SimpleUserChangeInfoDTO) FileUtils.byteToObject(bytes);
//                    userServiceI.updateUserParamsAsync(simpleUserChangeInfoDTO);
                    logger.debug(a_topic + ":" + String.valueOf(messageAndOffset.offset()) + " end-----------------------------------");
                    numRead++;

                    a_maxReads = messageAndOffset.offset();
                }

//            if (numRead == 0) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException ie) {
//                }
//            }
            }
            if (consumer != null)
                consumer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
//            ChangeUserInfoSchedule.isRunning = false;
        } catch (Exception e) {
            e.printStackTrace();
//            ChangeUserInfoSchedule.isRunning = false;
        }
    }

    /**
     * 获取上次消息的索引
     * @param consumer
     * @param topic
     * @param partition
     * @param whichTime
     * @param clientName
     * @return
     */
    public static long getLastOffset(SimpleConsumer consumer, String topic, int partition, long whichTime, String clientName) {
        TopicAndPartition topicAndPartition = new TopicAndPartition(topic, partition);
        Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfo = new HashMap<TopicAndPartition, PartitionOffsetRequestInfo>();
        requestInfo.put(topicAndPartition, new PartitionOffsetRequestInfo(whichTime, 1));
        OffsetRequest request = new OffsetRequest(requestInfo, kafka.api.OffsetRequest.CurrentVersion(), clientName);
        OffsetResponse response = consumer.getOffsetsBefore(request);

        if (response.hasError()) {
            logger.debug("Error fetching data Offset Data the Broker. Reason: " + response.errorCode(topic, partition));
            return 0;
        }
        long[] offsets = response.offsets(topic, partition);
        return offsets[0];
    }

    /**
     *
     * 找到新的Leader
     *
     * @param a_oldLeader
     * @param a_topic
     * @param a_partition
     * @param a_port
     * @return String
     * @throws Exception
     *             找一个leader broker
     */
    private String findNewLeader(String a_oldLeader, String a_topic, int a_partition, int a_port) throws Exception {
        for (int i = 0; i < 3; i++) {
            boolean goToSleep = false;
            PartitionMetadata metadata = findLeader(m_replicaBrokers, a_port, a_topic, a_partition);
            if (metadata == null) {
                goToSleep = true;
            } else if (metadata.leader() == null) {
                goToSleep = true;
            } else if (a_oldLeader.equalsIgnoreCase(metadata.leader().host()) && i == 0) {
                // first time through if the leader hasn't changed give
                // ZooKeeper a second to recover
                // second time, assume the broker did recover before failover,
                // or it was a non-Broker issue
                //
                goToSleep = true;
            } else {
                return metadata.leader().host();
            }
            if (goToSleep) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                }
            }
        }
        logger.error("Unable to find new leader after Broker failure. Exiting");
        throw new Exception("Unable to find new leader after Broker failure. Exiting");
    }

    /**
     *
     * 找到Partition中的Leader
     *
     * @param a_seedBrokers
     * @param a_port
     * @param a_topic
     * @param a_partition
     * @return
     */
    private PartitionMetadata findLeader(List<String> a_seedBrokers, int a_port, String a_topic, int a_partition) {
        PartitionMetadata returnMetaData = null;
        loop: for (String seed : a_seedBrokers) {
            SimpleConsumer consumer = null;
            try {
                consumer = new SimpleConsumer(seed, a_port, 100000, 64 * 1024, "leaderLookup");
                List<String> topics = Collections.singletonList(a_topic);
                TopicMetadataRequest req = new TopicMetadataRequest(topics);
                TopicMetadataResponse resp = consumer.send(req);

                List<TopicMetadata> metaData = resp.topicsMetadata();
                for (TopicMetadata item : metaData) {
                    for (PartitionMetadata part : item.partitionsMetadata()) {
                        if (part.partitionId() == a_partition) {
                            returnMetaData = part;
                            break loop;
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("Error communicating with Broker [" + seed + "] to find Leader for [" + a_topic + ", " + a_partition + "] Reason: " + e);
            } finally {
                if (consumer != null)
                    consumer.close();
            }
        }
        if (returnMetaData != null) {
            m_replicaBrokers.clear();
            for (kafka.cluster.Broker replica : returnMetaData.replicas()) {
                m_replicaBrokers.add(replica.host());
            }
        }
        return returnMetaData;
    }
}
