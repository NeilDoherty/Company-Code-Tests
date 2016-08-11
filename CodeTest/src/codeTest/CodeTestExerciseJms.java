package codeTest;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.NamingException;

import com.gwservices.server.jms.JMSServer;

public abstract class CodeTestExerciseJms implements MessageListener {

	//This is the queue connection factory name bound in JNDI
	public static final String QUEUE_CONNECTION_FACTORY = "java:/QueueConnectionFactory";
	//This is the queue name bound in JNDI, that messages need to be sent to
	public static final String SEND_QUEUE = "queue/requestQueue";
	//This is the queue name bound in JNDI, that the message listener will need to listen on
	public static final String REPLY_QUEUE = "queue/replyQueue";
	
	private JMSServer _jmsServer;
	public CodeTestExerciseJms(){
		initialize();		
	}
	
	
	
	/**
	 * 
	 * This is the method that will be responsible for establishing a
	 * connection to the request queue and sending an arbitrary TextMessage to.
	 * 
	 * The body of the message should be a simple statement.  This message has 
	 * to be of type TextMessage.
	 * 
	 */
	public abstract void sendMessage();
	
	/**
	 * 
	 * This method will be responsible for establishing a connection to the 
	 * reply queue and registering a message listener for that queue.  The message listener that 
	 * is to be registered will only responsible for being able to handle messages of type TextMessage 
	 * when the message is received by the listener its body should be printed to the console and then 
	 * acknowledged.
	 * 
	 */
	public abstract void registerMessageListener();
		
	private final void initialize(){
		_jmsServer = JMSServer.getInstance();
		_jmsServer.start();
	}
	
	public final void process(){
		registerMessageListener();		
		sendMessage();
		_jmsServer.stop();
	}
	
	static class SubClass extends CodeTestExerciseJms {
		Context context = null;
	
		@Override
		public void onMessage(Message arg0) {

		}

		/**
		 * This is the method that will be responsible for sending a
		 * message to the Jms server.
		 */
		@Override
		public void sendMessage() {
			try {
				ConnectionFactory myConnectionFactory = (javax.jms.ConnectionFactory) context.lookup(QUEUE_CONNECTION_FACTORY);
				Queue mySendQueue = (javax.jms.Queue)context.lookup(SEND_QUEUE);
				Connection connection = myConnectionFactory.createConnection("username", "password");
				Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				MessageProducer producer = session.createProducer(mySendQueue);
		        TextMessage message = session.createTextMessage();
			    message.setText("hello world");
			    producer.send(message);
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (JMSException e) {
		        throw new RuntimeException(e);
		    }	
		}
		
		/**
		 * This is the method that will be responsible for receiving a
		 * message from the Jms server
		 */
		@Override
		public void registerMessageListener() {
			Session session = null;
			try {
			Queue myReplyQueue = (javax.jms.Queue)context.lookup(REPLY_QUEUE);
			ConnectionFactory myConnectionFactory = (javax.jms.ConnectionFactory) context.lookup(QUEUE_CONNECTION_FACTORY);
			Connection connection = myConnectionFactory.createConnection("username", "password");
			MessageConsumer consumer = session.createConsumer(myReplyQueue);
			connection.start();
			Thread.sleep(1000);
			session.close();
			consumer.setMessageListener(null);
			} catch (JMSException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * This is the main method for the subclass which creates an instance of said subclass
		 * to initialize jms functions
		 */
		public static void main(String[] args) {
			SubClass subInstance = new SubClass();
			subInstance.sendMessage();
			subInstance.registerMessageListener();
		}
	}
}