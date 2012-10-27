package edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame;

import java.io.IOException;
import java.net.Socket;

public interface InternetUtil {
	
	/**
	 * set the socket this util should have
	 * @param so
	 * @throws IOException 
	 */
	public void setSocket(Socket so) throws IOException;
	
	/**
	 * receive a file
	 * @return - the length of the file in bytes
	 */
	public long receiveFile(String filename) throws Exception;
	
	/**
	 * send a file
	 * @return - the length of the file sending in bytes
	 */
	public long sendFile(String filename) throws Exception;
	
	/**
	 * receive messages
	 */
	public String receiveMessage() throws Exception;
	
	/**
	 * send a message
	 */
	public void sendMsg(String msg) throws Exception;
}
