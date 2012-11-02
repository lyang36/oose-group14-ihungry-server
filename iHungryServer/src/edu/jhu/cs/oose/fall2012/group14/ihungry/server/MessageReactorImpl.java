package edu.jhu.cs.oose.fall2012.group14.ihungry.server;

import org.bson.BSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import edu.jhu.cs.oose.fall2012.group14.ihungry.database.DBOKeyNames;
import edu.jhu.cs.oose.fall2012.group14.ihungry.database.DBOperator;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.CommunicationProtocol;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.InternetUtil;
import edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame.DataBaseOperater;
import edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame.MessageReactor;




public class MessageReactorImpl implements MessageReactor{
	
	int threadnum = 0;
	boolean isStopping = false;
	DBOperator dboperator;
	String uname, passwd, supinfo, commandmesg;
	InternetUtil internet = null;
	DBObject supinfoObj;
	
	interface OnJudgeListener{
		public void onTrue();
		
		public void onFalse();
	}
	
	public MessageReactorImpl(){
		dboperator = new DBOperator();
		dboperator.connectToDB();
	}
	
	private DBObject getKeyObj(DBObject target, String key){
		DBObject returnobj = new BasicDBObject();
		if(target != null){
			Object obj = target.get(key);
			returnobj.put(key, obj);
			return returnobj;
		}else{
			return null;
		}
	}
	
	/**
	 * get some info from the customer
	 * @param key
	 * @return
	 */
	private DBObject getCustomerInfo(String key){
		DBObject cus = dboperator.getCustomer(uname, passwd);
		return getKeyObj(cus, key);
	}
	
	/**
	 * get some info from the business
	 * @param key
	 * @return
	 */
	private DBObject getBusinessInfo(String key){
		DBObject bus = dboperator.getBusiness(uname, passwd);
		return getKeyObj(bus, key);
	}
	
	/**
	 * return the string
	 * @param judgeObj - if null, do something; if not do something
	 * @param returnObj - return this obj as the supinfo
	 * @param cmdSuccessStr - if not null, the return commandstr
	 * @param cmdFailStr - if null return commandstr
	 */
	private void returnStringInfo(boolean judge, String returnObj, 
			String cmdSuccessStr, String cmdFailStr, OnJudgeListener onjudge){
		try{
			if(judge){
				if(onjudge != null){
					onjudge.onTrue();
				}
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, cmdSuccessStr, returnObj));
			}else{
				if(onjudge != null){
					onjudge.onFalse();
				}
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, cmdFailStr, ""));
			}
		}catch(Exception e){
			try {
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, cmdFailStr, ""));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * call returnStringInfo(boolean judge, String returnObj, 
			String cmdSuccessStr, String cmdFailStr, OnJudgeListener onjudge){
	 * @param judge
	 * @param returnObj
	 * @param cmdSuccessStr
	 * @param cmdFailStr
	 * @param onjudge
	 */
	private void returnStringInfo(boolean judge, DBObject returnObj, 
			String cmdSuccessStr, String cmdFailStr, OnJudgeListener onjudge){
		returnStringInfo(judge, returnObj==null ? null : returnObj.toString(), 
				cmdSuccessStr, cmdFailStr, onjudge);
	}
	
	@Override
	public void reactToMsg(String input, final InternetUtil internet) {
		commandmesg = CommunicationProtocol.getRequestFromReceivedStr(input);
		uname = CommunicationProtocol.getUnameFromReceivedStr(input);
		passwd = CommunicationProtocol.getPasswdFromReceivedStr(input);
		supinfo = CommunicationProtocol.getSupinfoFromReceivedStr(input);
		this.internet = internet;
		supinfoObj = (DBObject) JSON.parse(supinfo);
		try {
			System.out.println(commandmesg);
			if(commandmesg.contains(CommunicationProtocol.BUSI_LOGIN)){
				returnStringInfo(dboperator.getBusiness(uname, passwd) != null,
						new BasicDBObject(), CommunicationProtocol.LOGIN_SUCCESS, 
						CommunicationProtocol.LOGIN_ERROR, null);
			}else if(commandmesg.contains(CommunicationProtocol.BUSI_CHECK_UNAME_EXISTED)){
				returnStringInfo(dboperator.checkBusiUnameExisted(uname),
						new BasicDBObject(), CommunicationProtocol.TRUE, 
						CommunicationProtocol.FALSE, null);
			}else if(commandmesg.contains(CommunicationProtocol.BUSI_PROCESS_ORDER)){
				//TODO
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
				
			}else if(commandmesg.contains(CommunicationProtocol.BUSI_RETRIVE_CHANGED_ORDERS)){
				//TODO
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
				
			}else if(commandmesg.contains(CommunicationProtocol.BUSI_RETRIVE_ORDERS)){
				//TODO
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
				
			}else if(commandmesg.contains(CommunicationProtocol.BUSI_SIGNUP)){
				returnStringInfo(!dboperator.checkBusiUnameExisted(uname),
						new BasicDBObject(), CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, new OnJudgeListener(){

							@Override
							public void onTrue() {
								DBObject bus;
								bus = supinfoObj;
								dboperator.addBusiness(bus);
							}

							@Override
							public void onFalse() {
							}
					
				});
			}else if(commandmesg.contains(CommunicationProtocol.BUSI_UPDATE_MENU)){
				//TODO
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
			}else if(commandmesg.contains(CommunicationProtocol.BUSI_UPLOAD_MENU)){
				//TODO
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
			}else if(commandmesg.contains(CommunicationProtocol.CUS_CHECK_UNAME_EXISTED)){
				returnStringInfo(dboperator.checkUserUnameExisted(uname),
						new BasicDBObject(), CommunicationProtocol.TRUE, 
						CommunicationProtocol.FALSE, null);
			}else if(commandmesg.contains(CommunicationProtocol.CUS_FIND_RESTAURANT)){
				//TODO
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
			}else if(commandmesg.contains(CommunicationProtocol.CUS_GET_DOCUMENT)){
				returnStringInfo(dboperator.getCustomer(uname, passwd) != null,
						dboperator.getCustomer(uname, passwd), 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						null);
				
			}else if(commandmesg.contains(CommunicationProtocol.CUS_GET_EMAIL)){
				returnStringInfo(dboperator.getCustomer(uname, passwd) != null,
						getCustomerInfo(DBOKeyNames.CUS_KEY_EMAIL), 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						null);
				
			}else if(commandmesg.contains(CommunicationProtocol.CUS_GET_MENU)){
				//TODO
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
			}else if(commandmesg.contains(CommunicationProtocol.CUS_GET_PHONE)){
				returnStringInfo(dboperator.getCustomer(uname, passwd) != null,
						getCustomerInfo(DBOKeyNames.CUS_KEY_PRIMEPHONE), 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						null);
			}else if(commandmesg.contains(CommunicationProtocol.CUS_GET_REALNAME)){
				returnStringInfo(dboperator.getCustomer(uname, passwd) != null,
						getCustomerInfo(DBOKeyNames.CUS_KEY_REALNAME), 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						null);
			}else if(commandmesg.contains(CommunicationProtocol.CUS_RETRIVE_CHANGED_ORDER)){
				//TODO
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
			}else if(commandmesg.contains(CommunicationProtocol.CUS_RETRIVE_ORDER)){
				//TODO
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
			}else if(commandmesg.contains(CommunicationProtocol.CUS_SET_BIRTH)){
				//TODO
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
			}else if(commandmesg.contains(CommunicationProtocol.CUS_SET_EMAIL)){
				final DBObject cus = dboperator.getCustomer(uname, passwd);
				returnStringInfo(cus != null,
						supinfo, 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						new OnJudgeListener(){

							@Override
							public void onTrue() {
								cus.put(DBOKeyNames.CUS_KEY_EMAIL, 
										supinfoObj.get(DBOKeyNames.CUS_KEY_EMAIL));
								dboperator.addCustomer(cus);
							}

							@Override
							public void onFalse() {
								
							}
					
				});
			}else if(commandmesg.contains(CommunicationProtocol.CUS_GET_MENU)){
				//TODO
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
			}else if(commandmesg.contains(CommunicationProtocol.CUS_GET_PHONE)){
				returnStringInfo(dboperator.getCustomer(uname, passwd) != null,
						getCustomerInfo(DBOKeyNames.CUS_KEY_PRIMEPHONE), 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						null);
			}else if(commandmesg.contains(CommunicationProtocol.CUS_RETRIVE_CHANGED_ORDER)){
				//TODO
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
			}else if(commandmesg.contains(CommunicationProtocol.CUS_RETRIVE_ORDER)){
				//TODO
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
			}else if(commandmesg.contains(CommunicationProtocol.CUS_SET_BIRTH)){
				final DBObject cus = dboperator.getCustomer(uname, passwd);
				returnStringInfo(cus != null,
						supinfo, 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						new OnJudgeListener(){

							@Override
							public void onTrue() {
								cus.put(DBOKeyNames.CUS_KEY_BIRTH, 
										supinfoObj.get(DBOKeyNames.CUS_KEY_BIRTH));
								dboperator.addCustomer(cus);
							}

							@Override
							public void onFalse() {
								
							}
					
				});
			}else if(commandmesg.contains(CommunicationProtocol.CUS_SET_PHONE)){
				final DBObject cus = dboperator.getCustomer(uname, passwd);
				returnStringInfo(cus != null,
						supinfo, 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						new OnJudgeListener(){

							@Override
							public void onTrue() {
								cus.put(DBOKeyNames.CUS_KEY_PRIMEPHONE, 
										supinfoObj.get(DBOKeyNames.CUS_KEY_PRIMEPHONE));
								dboperator.addCustomer(cus);
							}

							@Override
							public void onFalse() {
								
							}
					
				});
			}else if(commandmesg.contains(CommunicationProtocol.CUS_SET_REALNAME)){
				final DBObject cus = dboperator.getCustomer(uname, passwd);
				returnStringInfo(cus != null,
						supinfo, 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						new OnJudgeListener(){

							@Override
							public void onTrue() {
								cus.put(DBOKeyNames.CUS_KEY_REALNAME, 
										supinfoObj.get(DBOKeyNames.CUS_KEY_REALNAME));
								dboperator.addCustomer(cus);
							}
							@Override
							public void onFalse() {
								
							}
					
				});
			}else if(commandmesg.contains(CommunicationProtocol.CUS_SIGN_UP)){
				returnStringInfo(!dboperator.checkUserUnameExisted(uname),
						new BasicDBObject(), CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, new OnJudgeListener(){

							@Override
							public void onTrue() {
								DBObject cus;
								cus = supinfoObj;
								dboperator.addCustomer(cus);
							}

							@Override
							public void onFalse() {
							}
					
				});
			}else if(commandmesg.contains(CommunicationProtocol.CUS_SUBMIT_ORDER)){
				//TODO
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
			}else if(commandmesg.contains(CommunicationProtocol.CUS_UPDATE_DOC)){
				final DBObject cus = dboperator.getCustomer(uname, passwd);
				returnStringInfo(cus != null,
						supinfo, 
						CommunicationProtocol.PROCESS_SUCCEEDED, 
						CommunicationProtocol.PROCESS_FAILED, 
						new OnJudgeListener(){

							@Override
							public void onTrue() {
								supinfoObj.put(DBOKeyNames.OBJ_KEY_ID, cus.get(DBOKeyNames.OBJ_KEY_ID));
								dboperator.addCustomer(supinfoObj);
							}

							@Override
							public void onFalse() {
								
							}
					
				});
			}else if(commandmesg.contains(CommunicationProtocol.CUS_UPDATE_ORDER)){
				//TODO
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
			}else{
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						uname, passwd, CommunicationProtocol.NO_SUCH_COMMAND, supinfo));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				internet.sendMsg(CommunicationProtocol.construcSendingStr(
						CommunicationProtocol.getUnameFromReceivedStr(commandmesg), 
						CommunicationProtocol.getPasswdFromReceivedStr(commandmesg), 
						CommunicationProtocol.PROCESS_FAILED, ""));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void setOperater(DataBaseOperater operater) {
		
	}

}
