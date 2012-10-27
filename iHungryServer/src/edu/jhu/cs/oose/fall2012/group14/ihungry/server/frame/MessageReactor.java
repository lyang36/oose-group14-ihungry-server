package edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame;

import java.io.DataOutputStream;

/**
 * listener the messages, and do the correct operation
 * @author lyang
 *
 */
public interface MessageReactor {
	
	/**
	 * deal with the message
	 * @param message - 
	 */
	public void reactToMsg(String message, InternetUtil internet);
	
	/**
	 * setting up the thread number of the current thread
	 * @param num
	 */
	public void setThreadNum(int num);
	
	/**
	 * setting up the server operator
	 * @param operater
	 */
	public void setOperater(DMvisServerOperater operater);
}
