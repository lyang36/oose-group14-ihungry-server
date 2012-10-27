package edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame;

/**
 * The server
 * @author lyang
 *
 */
public interface ServerModel {
	/**
	 * start run the server
	 */
	public void run();
	
	/**
	 * add a message listener and operator class
	 */
	public void setMessageReactor(MessageReactor msl, DMvisServerOperater visc);
	
	/**
	 * set port
	 * @param port
	 */
	public void setPort(int port);
}
