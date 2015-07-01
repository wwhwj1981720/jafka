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

package com.sohu.jafka.mx;

import java.util.concurrent.atomic.AtomicLong;

import com.sohu.jafka.utils.Pool;
import com.sohu.jafka.utils.Utils;

/**
 * @author adyliu (imxylz@gmail.com)
 * @since 1.0
 */
public class ConsumerTopicStat implements ConsumerTopicStatMBean, IMBeanName {

    private static final Pool<String, ConsumerTopicStat> instances = new Pool<String, ConsumerTopicStat>();

    private final AtomicLong numCumulatedMessagesPerTopic = new AtomicLong(0);

    public long getMessagesPerTopic() {
        return numCumulatedMessagesPerTopic.get();
    }

    public void recordMessagesPerTopic(int nMessages) {
        numCumulatedMessagesPerTopic.addAndGet(nMessages);
    }

    public String getMbeanName() {
        return mBeanName;
    }

    private String mBeanName;

    public static ConsumerTopicStat getConsumerTopicStat(String topic) {
        ConsumerTopicStat stat = instances.get(topic);
        if (stat == null) {
            stat = new ConsumerTopicStat();
            stat.mBeanName = "jafka:type=jafka.ConsumerTopicStat." + topic;
            if (instances.putIfNotExists(topic, stat) == null) {
                Utils.registerMBean(stat);
            } else {
                stat = instances.get(topic);
            }
        }
        return stat;
    }
}
