package edu.jhu.cs.oose.fall2012.group14.ihungry.server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame.DMvisServerOperater;
import edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame.MessageReactor;
import edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame.ServerModel;
import edu.jhu.pha.idies.lyang.dmvisual.framework.*;
import edu.jhu.pha.idies.lyang.dmvisual.internet.InternetUtilImpl;
import edu.jhu.pha.idies.lyang.dmvisual.internet.ServerProtocol;
class doComms implements Runnable {
	private Socket server;
    private String line,input;
    MessageReactor reactor;
    InternetUtilImpl internet;
    

    doComms(Socket server, MessageReactor reactor, int thnum) {
    	this.server = server;
    	this.reactor = reactor;
    	this.reactor.setThreadNum(thnum);
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
    		line = "";
    		while(!(line.contains(ServerProtocol.serverDisconnect))){
    			line = internet.receiveMessage();
    			input=input + line;
    			System.out.println("Received "+ line);
    			reactor.reactToMsg(line, internet);
    		}
    		server.close();
    	} catch (Exception ioe) {
    		System.out.println("IOException on socket listen: " + ioe);
    		ioe.printStackTrace();
    	}
    }
}


public class Server implements ServerModel{

	private static int port=4444, maxConnections=10;
	MessageReactor msreactor;
	int threadNum = 0;
	DMvisServerOperater visc;
	
	
	/**
	 *  Listen for incoming connections and handle them
	 */
	@Override
	public void run() {
	    try{
	      ServerSocket listener = new ServerSocket(port);
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
				DMvisServerOperater op = visc.getClass().newInstance();
				msreactor.setOperater(op);
				msreactor.setThreadNum(threadNum);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			  doComms conn_c= new doComms(server, msreactor, threadNum);
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
	public void setMessageReactor(MessageReactor msl,
			DMvisServerOperater visc) {
		this.msreactor = msl;
		this.visc = visc;
	}


	@Override
	public void setPort(int port) {
		this.port = port;
		
	}
}
