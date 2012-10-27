package edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame;

import com.mongodb.DBObject;

/**
 * The Database operator
 * @author lyang
 *
 */
public interface DataBaseOperater {
	
	/**
	 * Connect to the DB
	 * @return whether or not successful
	 */
	public boolean connectToDB();
	
	/**
	 * Add a customer
	 * @param customerDoc
	 * @return
	 */
	public boolean addCustomer(DBObject customerDoc);
	
	/**
	 * Signin
	 */
	
	
}
