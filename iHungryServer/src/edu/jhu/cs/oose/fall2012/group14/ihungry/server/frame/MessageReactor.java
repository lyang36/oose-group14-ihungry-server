package edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame;

import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.InternetUtil;

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
	 * DataBaseOperator is used to operate the database
	 * @param operater
	 */
	public void setOperater(DataBaseOperater operater);
}
