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

package com.sohu.jafka.producer.serializer;

import com.sohu.jafka.message.Message;
import com.sohu.jafka.utils.Utils;

/**
 * UTF-8 bytes encoder
 * 
 * @author adyliu (imxylz@gmail.com)
 * @since 1.0
 * @see StringDecoder
 */
public class StringEncoder implements Encoder<String> {

    public Message toMessage(String event) {
        return new Message(Utils.getBytes(event, "UTF-8"));
    }

}
