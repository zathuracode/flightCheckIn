package com.vobi.jms.flight.checkin;

import java.util.UUID;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.vobi.jms.flight.model.Passenger;


/**
 * @author dgomez
 * dgomez@vortexbird.com
 */
public class CheckInApp {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext initialContext = new InitialContext();
		Queue requestQueue = (Queue) initialContext.lookup("queue/requestQueue");

		String url = "tcp://localhost:61616";
		String user = "artemis";
		String password = "artemis";
				
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(url, user, password); JMSContext jmsContext = cf.createContext()){
			
			JMSProducer producer = jmsContext.createProducer();		

			for (int i = 1; i <= 10; i++) {
				Passenger passenger=new Passenger();
				passenger.setId((int)(UUID.randomUUID().getMostSignificantBits() & Integer.MAX_VALUE));
				passenger.setFirstName("Diego");
				passenger.setLastName("Gomez");
				passenger.setPhone("+57 516 882 4629");
				passenger.setEmail("dgomez@ga.com");				
				producer.send(requestQueue, passenger);
			}

		}

	}

}
