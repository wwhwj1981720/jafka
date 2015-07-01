/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sohu.jafka.producer;

import java.util.List;
import java.util.Properties;

import com.sohu.jafka.common.InvalidConfigException;
import com.sohu.jafka.common.annotations.ClientSide;
import com.sohu.jafka.message.CompressionCodec;
import com.sohu.jafka.producer.async.AsyncProducerConfig;
import com.sohu.jafka.producer.async.AsyncProducerConfigShared;
import com.sohu.jafka.utils.Utils;
import com.sohu.jafka.utils.ZKConfig;

/**
 * Configuration for producer
 * @author adyliu (imxylz@gmail.com)
 * @since 1.0
 */
@ClientSide
public class ProducerConfig extends ZKConfig implements SyncProducerConfigShared, AsyncProducerConfigShared {

    private final SyncProducerConfigShared synchConfigShared;

    private final AsyncProducerConfigShared asyncProducerConfigShared;
    /**
     * create config for producer
     * @param props config arguments
     */
    public ProducerConfig(Properties props) {
        super(props);
        synchConfigShared = new SyncProducerConfig(props);
        asyncProducerConfigShared = new AsyncProducerConfig(props);
        check();
    }

    private void check() {
        if (getBrokerList() != null && Utils.getString(props, "partitioner.class", null) != null) {
            throw new InvalidConfigException("partitioner.class cannot be used when broker.list is set");
        }

        // If both broker.list and zk.connect options are specified, throw an exception
        if (getBrokerList() != null && getZkConnect() != null) {
            throw new InvalidConfigException("only one of broker.list and zk.connect can be specified");
        }

        if (getBrokerList() == null && getZkConnect() == null) {
            throw new InvalidConfigException("At least one of zk.connect or broker.list must be specified");
        }
    }

    public Properties getProperties() {
        return props;
    }

    public int getBufferSize() {
        return synchConfigShared.getBufferSize();
    }

    public int getConnectTimeoutMs() {
        return synchConfigShared.getConnectTimeoutMs();
    }

    public int getSocketTimeoutMs() {
        return synchConfigShared.getSocketTimeoutMs();
    }

    public int getReconnectInterval() {
        return synchConfigShared.getReconnectInterval();
    }

    public int getReconnectTimeInterval() {
        return synchConfigShared.getReconnectTimeInterval();
    }

    public int getMaxMessageSize() {
        return synchConfigShared.getMaxMessageSize();
    }

    public int getQueueTime() {
        return asyncProducerConfigShared.getQueueTime();
    }

    public int getQueueSize() {
        return asyncProducerConfigShared.getQueueSize();
    }

    public int getEnqueueTimeoutMs() {
        return asyncProducerConfigShared.getEnqueueTimeoutMs();
    }

    public int getBatchSize() {
        return asyncProducerConfigShared.getBatchSize();
    }

    public String getSerializerClass() {
        return synchConfigShared.getSerializerClass();
    }

    public String getCbkHandler() {
        return asyncProducerConfigShared.getCbkHandler();
    }

    public Properties getCbkHandlerProperties() {
        return asyncProducerConfigShared.getCbkHandlerProperties();
    }

    public String getEventHandler() {
        return asyncProducerConfigShared.getEventHandler();
    }

    public Properties getEventHandlerProperties() {
        return asyncProducerConfigShared.getEventHandlerProperties();
    }

    /**
     * For bypassing zookeeper based auto partition discovery, use this config to pass in
     * static broker and per-broker partition information. Format-
     * 
     * <pre>
     *      brokerid1:host1:port1[:partitions[:autocreatetopic]], brokerid2:host2:port2[:partitions[:autocreatetopic]]
     * </pre>
     */
    public String getBrokerList() {
        return Utils.getString(props, "broker.list", null);
    }

    /** the partitioner class for partitioning events amongst sub-topics */
    public String getPartitionerClass() {
        return Utils.getString(props, "partitioner.class", DefaultPartitioner.class.getName());
    }

    /**
     * This parameter allows you to specify the compression codec for all data generated by
     * this producer. The default is {@link CompressionCodec#NoCompressionCodec}
     * 
     * @see CompressionCodec#NoCompressionCodec
     */
    public CompressionCodec getCompressionCodec() {
        return CompressionCodec.valueOf(Utils.getInt(props, "compression.codec", 0));
    }

    /**
     * This parameter allows you to set whether compression should be turned * on for
     * particular topics
     * 
     * If the compression codec is anything other than NoCompressionCodec,
     * 
     * Enable compression only for specified topics if any
     * 
     * If the list of compressed topics is empty, then enable the specified compression codec
     * for all topics
     * 
     * If the compression codec is NoCompressionCodec, compression is disabled for all topics
     */
    public List<String> getCompressedTopics() {
        return Utils.getCSVList(Utils.getString(props, "compressed.topics", null));
    }

    /**
     * this parameter specifies whether the messages are sent asynchronously or not. Valid
     * values are
     * 
     * <pre>
     *   async: for asynchronous send 
     *    sync: for synchronous send
     * </pre>
     */
    public String getProducerType() {
        return Utils.getString(props, "producer.type", "sync");
    }

    /**
     * The producer using the zookeeper software load balancer maintains a ZK cache that gets
     * updated by the zookeeper watcher listeners. During some events like a broker bounce, the
     * producer ZK cache can get into an inconsistent state, for a small time period. In this
     * time period, it could end up picking a broker partition that is unavailable. When this
     * happens, the ZK cache needs to be updated. This parameter specifies the number of times
     * the producer attempts to refresh this ZK cache.
     */
    public int getZkReadRetries() {
        return Utils.getInt(props, "zk.read.num.retries", 3);
    }

    /**
     * If DefaultEventHandler is used, this specifies the number of times to retry if an error
     * is encountered during send. Currently, it is only appropriate when broker.list points to
     * a VIP. If the zk.connect option is used instead, this will not have any effect because
     * with the zk-based producer, brokers are not re-selected upon retry. So retries would go
     * to the same (potentially still down) broker. (KAFKA-253 will help address this.)
     * <br/><br/>
     * see https://github.com/apache/kafka/commit/d6b1de35f6b9cd5370c7812790fea8e61618f461
     */
    public int getNumRetries() {
        return Utils.getInt(props, "num.retries", 0);
    }
}
