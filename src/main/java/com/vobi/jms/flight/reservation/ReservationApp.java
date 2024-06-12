package com.vobi.jms.flight.reservation;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.vobi.jms.flight.listener.ReservationCheckListener;




public class ReservationApp {
	
	public static void main(String[] args) throws NamingException, InterruptedException {
		InitialContext initialContext = new InitialContext();
		Queue requestQueue = (Queue) initialContext.lookup("queue/requestQueue");

		String url = "tcp://localhost:61616";
		String user = "artemis";
		String password = "artemis";		
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(url, user, password); JMSContext jmsContext = cf.createContext()){		
			JMSConsumer consumer1 = jmsContext.createConsumer(requestQueue);
			JMSConsumer consumer2 = jmsContext.createConsumer(requestQueue);
			consumer1.setMessageListener(new ReservationCheckListener().setJMSConsumerName("consumer1"));
			consumer2.setMessageListener(new ReservationCheckListener().setJMSConsumerName("consumer2"));
			
			Thread.sleep(100000);

		};

	}

}
