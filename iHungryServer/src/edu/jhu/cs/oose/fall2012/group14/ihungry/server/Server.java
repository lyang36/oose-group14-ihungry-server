package edu.jhu.cs.oose.fall2012.group14.ihungry.server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import edu.jhu.cs.oose.fall2012.group14.ihungry.database.DBOperator;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.CommunicationProtocol;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.InternetUtilImpl;
import edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame.MessageReactor;
import edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame.ServerModel;

class doComms implements Runnable {
	private Socket server;
    private String line,input;
    MessageReactor reactor;
    InternetUtilImpl internet;
    

    doComms(Socket server, MessageReactor reactor) {
    	this.server = server;
    	this.reactor = reactor;
    	internet = new InternetUtilImpl();
    	try {
			internet.setSocket(server);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void run () {
    	input="";
    	try {
    		input = internet.receiveMessage();
    		reactor.reactToMsg(input, internet);
    		server.close();
    	} catch (Exception ioe) {
    		System.out.println("IOException on socket listen: " + ioe);
    		ioe.printStackTrace();
    	}
    }
}


public class Server implements ServerModel{

	private static int port=4444, maxConnections=100;
	MessageReactor msreactor;
	int threadNum = 0;
	
	
	/**
	 *  Listen for incoming connections and handle them
	 */
	@Override
	public void run() {
	    try{
	      ServerSocket listener = new ServerSocket(CommunicationProtocol.SERVER_PORT);
	      Socket server;
	      System.out.println("Start Listening...");
	      
	      while(true){
	    	  server = listener.accept();
			  threadNum ++;
			  if(threadNum >= maxConnections){
				  threadNum = 0;
			  }
			  try {
				msreactor = msreactor.getClass().newInstance();
				
				DBOperator op = null;
				msreactor.setOperater(op);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			  doComms conn_c= new doComms(server, msreactor);
			  Thread t = new Thread(conn_c);
			  t.start();
			  System.out.println("Connection " + threadNum +" starts...");
	      }
	    } catch (IOException ioe) {
	      System.out.println("IOException on socket listen: " + ioe);
	      ioe.printStackTrace();
	    }
	}


	@Override
	public void setMessageReactor(MessageReactor msl) {
		msreactor = msl;
	}
	
	public static void main(String args[]){
		Server server = new Server();
		server.setMessageReactor(new MessageReactorImpl());
		server.run();
	}
}
