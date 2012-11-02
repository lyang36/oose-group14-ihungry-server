package edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame;

import org.json.JSONObject;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import edu.jhu.cs.oose.project.group14.ihungry.model.Order;

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
	 * close connection
	 * @return
	 */
	public void close();
	
	/**
	 * authenicate
	 * @param myUserName
	 * @param myPassword
	 * @return
	 */
	public boolean authenticate(String myUserName, String myPassword);
	
	/**
	 * check whether the username is existed 
	 */
	public boolean checkUserUnameExisted(String uname);
	
	/**
	 * check whether the business uname existed
	 * @param uname
	 * @return
	 */
	public boolean checkBusiUnameExisted(String uname);
	
	/**
	 * get the Customer object
	 * @param uname
	 * @param passwd
	 * @return
	 */
	public DBObject getCustomer(String uname, String passwd);
	
	/**
	 * add the cus, if the cus is already existed, then change it
	 * @param cus
	 */
	public void addCustomer(DBObject cus);
	
	/**
	 * add the bus, if the bus is already existed, then change it
	 * @param bus
	 */
	public void addBusiness(DBObject bus);
	
	/**
	 * get the Business
	 */
	public DBObject getBusiness(String busiuname, String passwd);
	
	/**
	 * get the orders for the user, from newest to the oldest, make them to be unchanged
	 * @param uname
	 * @param passwd
	 * @return
	 */
	public DBObject getUserOrders(String uname, String passwd, int startInd, int endInd);
	
	/**
	 * get the orders for the user, from newest to the oldest, make them to be unchanged
	 * 
	 * @param uname
	 * @param passwd
	 * @param status - 0:unprocessed, <p> 1: processing <p> 2: processed <p> 3: cancelled
	 * @return
	 */
	public DBObject getUserOrders(String uname, String passwd, int status, 
			int startInd, int endInd);
	
	/**
	 * get the changed orders for the user, from newest to the oldest, make them to be unchanged
	 * @param uname
	 * @param passwd
	 * @return
	 */
	public DBObject getChangedUserOrders(String uname, String passwd);
	
	
	/**
	 * get the orders for the busi, make them to be unchanged
	 * @param uname
	 * @param passwd
	 * @return
	 */
	public DBObject getBusiOrders(String uname, String passwd, int startInd, int endInd);
	
	/**
	 * get the orders for the Busi, make them to be unchanged
	 * 
	 * @param uname
	 * @param passwd
	 * @param status - 0:unprocessed, <p> 1: processing <p> 2: processed <p> 3: cancelled
	 * @return
	 */
	public DBObject getBusiOrders(String uname, String passwd, int status, 
			int startInd, int endInd);
	
	/**
	 * get the changed orders for the Busi, from newest to the oldest, make them to be unchanged
	 * @param uname
	 * @param passwd
	 * @return
	 */
	public DBObject getChangedBusiOrders(String uname, String passwd);
	
	/**
	 * update an known order, make it to be changed
	 * @param o
	 */
	public void updateOrder(DBObject o);
	
	public void addOrder(DBObject o);
	
	
}
