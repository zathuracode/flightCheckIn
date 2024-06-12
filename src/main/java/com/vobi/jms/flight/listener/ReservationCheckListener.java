package com.vobi.jms.flight.listener;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.vobi.jms.flight.model.Passenger;

public class ReservationCheckListener implements MessageListener {

	private String JMSConsumerName;
	
	

	@Override
	public void onMessage(Message message) {
		ObjectMessage objectMessage = (ObjectMessage) message;
		String url = "tcp://localhost:61616";
		String user = "artemis";
		String password = "artemis";		
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(url, user, password); JMSContext jmsContext = cf.createContext()){
			
			
			Passenger passenger = (Passenger) objectMessage.getObject();
			
			System.out.println("JMSConsumerName:"+JMSConsumerName);
			System.out.println(passenger.getId());
			System.out.println(passenger.getFirstName());
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}



	



	public ReservationCheckListener setJMSConsumerName(String jMSConsumerName) {
		JMSConsumerName = jMSConsumerName;
		return this;
	}
	
	
	

}
