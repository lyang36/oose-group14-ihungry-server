package edu.jhu.cs.oose.fall2012.group14.ihungry.server;

import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.CommunicationProtocol;
import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.InternetUtil;
import edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame.DataBaseOperater;
import edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame.MessageReactor;


public class MessageReactorImpl implements MessageReactor{
	
	int threadnum = 0;
	boolean isStopping = false;
	
	
	@Override
	public void reactToMsg(String message, final InternetUtil internet) {
		try {
			System.out.println(message);
			internet.sendMsg(CommunicationProtocol.construcSendingStr(
					CommunicationProtocol.getUnameFromReceivedStr(message), 
					CommunicationProtocol.getPasswdFromReceivedStr(message), 
					CommunicationProtocol.NO_SUCH_COMMAND, ""));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setOperater(DataBaseOperater operater) {
		
	}

}
