package edu.jhu.cs.oose.fall2012.group14.ihungry.server;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import edu.jhu.cs.oose.fall2012.group14.ihungry.database.DBOKeyNames;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.CommunicationProtocol;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.InternetUtil;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.InternetUtilImpl;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.MD5;

public class MessageReactorImplTest {
	public void testCommand(String uname, String passwd,String command,
			final String expectedReturnCmd, String supinfo){
		MessageReactorImpl msgReactor = new MessageReactorImpl();
		//check username unexisted
		String input = CommunicationProtocol.construcSendingStr(
				uname, passwd, 
				command, 
				supinfo);
		msgReactor.reactToMsg(input, new InternetUtil(){

			@Override
			public void setSocket(Socket so) throws IOException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public long receiveFile(String filename) throws Exception {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long sendFile(String filename) throws Exception {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public String receiveMessage() throws Exception {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void sendMsg(String msg) throws Exception {
				String cmdString = CommunicationProtocol.getRequestFromReceivedStr(msg);
				System.out.println("SuppliInfo: \n" +
						CommunicationProtocol.getSupinfoFromReceivedStr(msg));
				assertEquals(cmdString, expectedReturnCmd);
			}
			
		});
	}

	@Test
	public void test() {
		//check username unexisted
		testCommand(MD5.getNameMd5("szhao12345"),
				MD5.getMd5("1234"), CommunicationProtocol.CUS_CHECK_UNAME_EXISTED,
				CommunicationProtocol.FALSE, "");
		System.out.println("Check username unexisted succeeded!!!");
		
		//check username existed
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("1234"), CommunicationProtocol.CUS_CHECK_UNAME_EXISTED,
				CommunicationProtocol.TRUE, "");
		System.out.println("Check username existed succeeded!!!");
		
		//check getdoc
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123456"), CommunicationProtocol.CUS_GET_DOCUMENT,
				CommunicationProtocol.PROCESS_SUCCEEDED, "");
		System.out.println("Check getdoc succeeded!!!");
		
		//check getdoc with wrong password
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("fjkd"), CommunicationProtocol.CUS_GET_DOCUMENT,
				CommunicationProtocol.PROCESS_FAILED, "");
		System.out.println("Test getdoc wrong passwd succeeded!!!");
	
		//check get email test
		testCommand(MD5.getNameMd5("lyang"),
				MD5.getMd5("123456"), CommunicationProtocol.CUS_GET_EMAIL,
				CommunicationProtocol.PROCESS_SUCCEEDED, "");
		System.out.println("Test get email succeeded!!!");
		
		//check signup
		JSONObject cusobj = new JSONObject();
		String md5names = MD5.getNameMd5("szhao");
		String md5passwd = MD5.getMd5("123456");
		try {
			cusobj.put(DBOKeyNames.CUS_KEY_ID, md5names);
			cusobj.put(DBOKeyNames.CUS_KEY_REALNAME, "Sun Zhao");
			cusobj.put(DBOKeyNames.CUS_KEY_PASSWD, md5passwd);
			cusobj.put(DBOKeyNames.CUS_KEY_EMAIL, "1334@4444.com");
			cusobj.put(DBOKeyNames.CUS_KEY_PRIMEPHONE, "444-456-7890");
			cusobj.put(DBOKeyNames.CUS_KEY_UNAME, "szhao");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		testCommand(MD5.getNameMd5("szhao"),
				MD5.getMd5("123456"), CommunicationProtocol.CUS_SIGN_UP,
				CommunicationProtocol.PROCESS_SUCCEEDED, cusobj.toString());
		System.out.println("Test Signup succeeded!!!");
		
		//check signup business
		JSONObject busobj = new JSONObject();
		md5names = MD5.getNameMd5("lyangBus");
		md5passwd = MD5.getMd5("111111");
		try {
			busobj.put(DBOKeyNames.BUS_KEY_ID, md5names);
			busobj.put(DBOKeyNames.BUS_KEY_NAME, "Yang And His Friends' Food");
			busobj.put(DBOKeyNames.BUS_KEY_PASSWD, md5passwd);
			busobj.put(DBOKeyNames.BUS_KEY_EMAIL, "1111@beijing.com");
			busobj.put(DBOKeyNames.BUS_KEY_PHONE, "444-888-8888");
			busobj.put(DBOKeyNames.BUS_KEY_UNAME, "lyangBus");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		testCommand(MD5.getNameMd5("lyangBus"),
				MD5.getMd5("111111"), CommunicationProtocol.BUSI_SIGNUP,
				CommunicationProtocol.PROCESS_SUCCEEDED, busobj.toString());
		System.out.println("Test Signup business succeeded!!!");
		
	}

}
