package edu.jhu.cs.oose.fall2012.group14.ihungry.database;



import org.bson.types.ObjectId;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

import edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame.DataBaseOperater;
import edu.jhu.cs.oose.project.group14.ihungry.model.Order;

public class DBOperator implements DataBaseOperater{
	public static final String IS_ORDER_NEW_KEY = "isOderNew";
	
	DB myDB;
    private Mongo mongodb = null;	//mongo db
    private DBObject loginObj =null; 
    private BasicDBObject query;
    private DBCollection cusCollection = null;
    private DBCollection busiCollection = null;
    private DBCollection orderCollection = null;
	@Override
	public boolean connectToDB() {
		try {
			mongodb = new Mongo( "localhost" );
			myDB = mongodb.getDB( DBOKeyNames.DATABASE_NAME);
			cusCollection = myDB.getCollection(DBOKeyNames.CUS_COLLECTION_NAME);
			busiCollection = myDB.getCollection(DBOKeyNames.BUS_COLLECTION_NAME);
			orderCollection = myDB.getCollection(DBOKeyNames.ORDER_COLLECTION_NAME);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public boolean authenticate(String myUserName, String myPassword) {
		return true;
	}

	/**
	 * query the query on the collection
	 * @param coll
	 * @param query
	 * @return
	 */
	private DBCursor queryOnCollection(DBCollection coll, BasicDBObject query){
    	DBCursor cur = coll.find(query);
    	return cur;	
	}
	
	
	/**
	 * get the business without the password
	 * @return
	 */
	private DBObject getBusiness_priv(String uname){
		query = new BasicDBObject();
		query.put(DBOKeyNames.BUS_KEY_ID, uname);
		DBCursor cur = queryOnCollection(busiCollection, query);
		if(cur.hasNext()){
			return cur.next();
		}		
		return null;
	}
	
	/**
	 * get the customer without the password
	 * @return
	 */
	private DBObject getCustomer_priv(String uname){
		query = new BasicDBObject();
		query.put(DBOKeyNames.CUS_KEY_ID, uname);
		DBCursor cur = queryOnCollection(cusCollection, query);
		if(cur.hasNext()){
			return cur.next();
		}		
		return null;
	}
	
	@Override
	public boolean checkUserUnameExisted(String uname) {
		if(getCustomer_priv(uname) != null){
			return true;
		}
		return false;
	}


	
	@Override
	public boolean checkBusiUnameExisted(String uname) {
		if(getBusiness_priv(uname) != null){
			return true;
		}
		return false;
	}

	@Override
	public DBObject getCustomer(String uname, String passwd) {
		query = new BasicDBObject();
		query.put(DBOKeyNames.CUS_KEY_ID, uname);
		query.put(DBOKeyNames.CUS_KEY_PASSWD, passwd);
		DBCursor cur = queryOnCollection(cusCollection, query);
		if(cur.hasNext()){
			return cur.next();
		}		
		return null;
	}

	@Override
	public DBObject getBusiness(String busiuname, String passwd) {
		query = new BasicDBObject();
		query.put(DBOKeyNames.BUS_KEY_ID, busiuname);
		query.put(DBOKeyNames.BUS_KEY_PASSWD, passwd);
		DBCursor cur = queryOnCollection(busiCollection, query);
		if(cur.hasNext()){
			return cur.next();
		}		
		return null;
	}

	@Override
	public DBObject getUserOrders(String uname, String passwd, int startInd,
			int endInd) {	
		return null;
	}

	@Override
	public DBObject getUserOrders(String uname, String passwd, int status,
			int startInd, int endInd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBObject getChangedUserOrders(String uname, String passwd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBObject getBusiOrders(String uname, String passwd, int startInd,
			int endInd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBObject getBusiOrders(String uname, String passwd, int status,
			int startInd, int endInd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBObject getChangedBusiOrders(String uname, String passwd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateOrder(DBObject o) {
		//o.put(IS_ORDER_NEW_KEY, true);		
		//orderCollection.update(q, o);
	}

	@Override
	public void addOrder(DBObject o) {
		o.put(IS_ORDER_NEW_KEY, true);		
		orderCollection.insert(o);
		
	}


	
	@Override
	public void addCustomer(DBObject cus) {
		if(checkUserUnameExisted((String) cus.get(DBOKeyNames.CUS_KEY_ID))){
			DBObject origin = getCustomer_priv((String) cus.get(DBOKeyNames.CUS_KEY_ID));
			ObjectId id = (ObjectId) origin.get(DBOKeyNames.OBJ_KEY_ID);
			cus.put(DBOKeyNames.OBJ_KEY_ID, id);
			cusCollection.update(origin, cus);
		}else{
			cusCollection.insert(cus);
		}
	}

	@Override
	public void addBusiness(DBObject bus) {
		if(checkBusiUnameExisted((String) bus.get(DBOKeyNames.BUS_KEY_ID))){
			DBObject origin = getBusiness_priv((String) bus.get(DBOKeyNames.BUS_KEY_ID));
			ObjectId id = (ObjectId) origin.get(DBOKeyNames.OBJ_KEY_ID);
			bus.put(DBOKeyNames.OBJ_KEY_ID, id);
			busiCollection.update(origin, bus);
		}else{
			busiCollection.insert(bus);
		}
	}

	@Override
	public void close() {
		mongodb.close();
	}

}
