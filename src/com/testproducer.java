package com;

import java.util.Properties;

import com.sohu.jafka.producer.Producer;
import com.sohu.jafka.producer.ProducerConfig;
import com.sohu.jafka.producer.StringProducerData;
import com.sohu.jafka.producer.serializer.StringEncoder;

public class testproducer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		    Properties props = new Properties();
		    props.put("broker.list", "0:192.168.138.128:9092");
		    props.put("serializer.class", StringEncoder.class.getName());
		    //
		    ProducerConfig config = new ProducerConfig(props);
		    final Producer<String, String> producer = new Producer<String, String>(config);
		    //
		    StringProducerData data = new StringProducerData("demo");
		    for(int i=0;i<1000;i++) {
		        data.add("Hello world #"+i);
		    }
		    //
		    try {
		        long start = System.currentTimeMillis();
		        for (int i = 0; i < 100; i++) {
		            producer.send(data);
		        }
		        long cost = System.currentTimeMillis() - start;
		        System.out.println("send 100000 message cost: "+cost+" ms");
		    } finally {
		        producer.close();
		    }
		}

	}


