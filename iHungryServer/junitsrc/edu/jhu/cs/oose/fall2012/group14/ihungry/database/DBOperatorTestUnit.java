package edu.jhu.cs.oose.fall2012.group14.ihungry.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.MD5;


public class DBOperatorTestUnit {

	@Test
	public void test() {
		DBOperator dboperator = new DBOperator();
		dboperator.connectToDB();
		String md5names = null;
		String md5passwd = null;
		DBObject busobj = new BasicDBObject();
		md5names = MD5.getNameMd5("No1Res");
		md5passwd = MD5.getMd5("123456");
		busobj.put(DBOKeyNames.BUS_KEY_ID, md5names);
		busobj.put(DBOKeyNames.BUS_KEY_NAME, "No1Res");
		busobj.put(DBOKeyNames.BUS_KEY_PASSWD, md5passwd);
		busobj.put(DBOKeyNames.BUS_KEY_EMAIL, "11@22.com");
		busobj.put(DBOKeyNames.BUS_KEY_PHONE, "333-444-5555");
		busobj.put(DBOKeyNames.BUS_KEY_UNAME, "No1Res");
		
		try {
			assertEquals(dboperator.checkBusiUnameExisted(MD5.getNameMd5("fjlkjalk")), false);
			System.out.println("Check busi uname not existed succeeded");
			
			assertEquals(dboperator.checkUserUnameExisted(MD5.getNameMd5("fjlkjalk")), false);
			System.out.println("Check user uname not existed succeeded");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			fail("check not existed");
		}
		
		
		dboperator.addBusiness(busobj);
		try {
			String gn = (String) dboperator.getBusiness(md5names, md5passwd).get(DBOKeyNames.BUS_KEY_ID); 
			System.out.println(gn);
			assertEquals(gn, md5names);
			System.out.println("Add and get Business succeeded!");
			
			assertEquals(dboperator.checkBusiUnameExisted(md5names), true);
			System.out.println("Check busi uname existed succeeded");
			
			busobj.put(DBOKeyNames.BUS_KEY_EMAIL, "11@2212.com");
			dboperator.addBusiness(busobj);
			 gn = (String) dboperator.getBusiness(md5names, md5passwd).get(DBOKeyNames.BUS_KEY_EMAIL); 
			System.out.println(gn);
			assertEquals(gn, "11@2212.com");
			System.out.println("Change Business succeeded!");
			
		} catch (Exception e) {
			e.printStackTrace();
			fail("busi");
		}
		
		
		DBObject cusobj = new BasicDBObject();
		md5names = MD5.getNameMd5("lyang");
		md5passwd = MD5.getMd5("123456");
		cusobj.put(DBOKeyNames.CUS_KEY_ID, md5names);
		cusobj.put(DBOKeyNames.CUS_KEY_REALNAME, "Lin Yang");
		cusobj.put(DBOKeyNames.CUS_KEY_PASSWD, md5passwd);
		cusobj.put(DBOKeyNames.CUS_KEY_EMAIL, "333@ppp.com");
		cusobj.put(DBOKeyNames.CUS_KEY_PRIMEPHONE, "123-456-7890");
		cusobj.put(DBOKeyNames.CUS_KEY_UNAME, "lyang");
		dboperator.addCustomer(cusobj);
		
		try {
			String gn = (String) dboperator.getCustomer(md5names, md5passwd).get(DBOKeyNames.CUS_KEY_ID); 
			System.out.println(gn);
			assertEquals(gn, md5names);
			System.out.println("Add and get customer succeeded!");
			
			assertEquals(dboperator.checkUserUnameExisted(md5names), true);
			System.out.println("Check user uname existed succeeded");
			
			
			cusobj.put(DBOKeyNames.BUS_KEY_EMAIL, "888@2212.com");
			dboperator.addCustomer(cusobj);
			 gn = (String) dboperator.getCustomer(md5names, md5passwd).get(DBOKeyNames.CUS_KEY_EMAIL); 
			System.out.println(gn);
			assertEquals(gn, "888@2212.com");
			System.out.println("Change Customer succeeded!");
			
		} catch (Exception e) {
			e.printStackTrace();
			fail("user");
		}
		
	}

}
