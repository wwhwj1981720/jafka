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

package com.sohu.jafka.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;


/**
 * @author adyliu (imxylz@gmail.com)
 * @since 1.0
 */
public class LineMessageReader implements MessageReader {

    private BufferedReader reader;
    boolean first = true;
    public void init(InputStream inputStream, Properties props) {
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public String readMessage() throws IOException {
        if(first) {
            first = false;
            System.out.println("Enter you message and exit with empty string.");
        }
        System.out.print("> ");
        return reader.readLine();
    }

    public void close() {
    }

}
