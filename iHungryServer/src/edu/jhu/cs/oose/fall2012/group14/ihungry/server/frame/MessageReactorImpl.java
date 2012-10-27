package edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame;


import edu.jhu.pha.idies.lyang.dmvisual.framework.DMvisServerOperater;
import edu.jhu.pha.idies.lyang.dmvisual.framework.InternetUtil;
import edu.jhu.pha.idies.lyang.dmvisual.framework.MessageReactor;
import edu.jhu.pha.idies.lyang.dmvisual.framework.VisParameter;
import edu.jhu.pha.idies.lyang.dmvisual.internet.ServerProtocol;
import edu.jhu.pha.idies.lyang.dmvisual.misc.Parameters;

public class MessageReactorImpl implements MessageReactor{
	
	DMvisServerOperater visoperator = null;
	int threadnum = 0;
	boolean isStopping = false;
	
	
	@Override
	public void reactToMsg(String message, final InternetUtil internet) {
		if(message.contains(ServerProtocol.serverRun)){
			System.out.println("Running the program!!");
			isStopping = false;
			visoperator.startRun();
			(new Thread(){
				public void run(){
					int p = 0;
					while(!visoperator.isFinished()){
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						if(isStopping){
							break;
						}
						
						if(visoperator.getPercentage() != p){
							p = visoperator.getPercentage();
							try {
								internet.sendMsg(ServerProtocol.clientPercentage);
								internet.sendMsg(Integer.toString(p));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						
					}
					if(!isStopping){
						try {
							internet.sendMsg(ServerProtocol.clientImg);
							System.out.println("Sending image...");
							internet.sendFile(visoperator.getImgFile());
							System.out.println("Finished...");
							
							
							internet.sendMsg(ServerProtocol.imgData);
							System.out.println("Sending image Data...");
							internet.sendFile(visoperator.getImgDataFile());
							System.out.println("Finished...");
							
							
							internet.sendMsg(ServerProtocol.healpixData);
							System.out.println("Sending HEALPIX Data...");
							internet.sendFile(visoperator.getHealPixDataFile());
							System.out.println("Finished...");
							
							internet.sendMsg(ServerProtocol.serverFinish);
							//internet.sendMsg(ServerProtocol.serverFinish);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}else{
						System.out.println("Interrupted!");
					}
				}
			}).start();
		}else if(message.contains(ServerProtocol.serverStop)){
			visoperator.stopRun();
			isStopping = true;
		
		}else if(message.contains(ServerProtocol.serverDisconnect)){
			visoperator.stopRun();
			isStopping = true;
			
		}else if(message.contains(ServerProtocol.serverParameter)){
			Parameters par = new Parameters();
			try {
				par.parseString(internet.receiveMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
			visoperator.setParameters(par);
		}else if(message.contains(ServerProtocol.serverFile)){
			//TODO send the healpix data file file
		}else if(message.contains(ServerProtocol.healpixData)){
			try {
				internet.sendMsg(ServerProtocol.healpixData);
				System.out.println("Sending healpix data...");
				internet.sendFile(visoperator.getHealPixDataFile());
				System.out.println("Finished...");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(message.contains(ServerProtocol.imgData)){
			try {
				internet.sendMsg(ServerProtocol.imgData);
				System.out.println("Sending image data...");
				internet.sendFile(visoperator.getImgDataFile());
				System.out.println("Finished...");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(message.contains(ServerProtocol.clientImg)){
			try {
				internet.sendMsg(ServerProtocol.imgData);
				System.out.println("Sending image ...");
				System.out.println(visoperator.getImgFile());
				internet.sendFile(visoperator.getImgFile());
				System.out.println("Finished...");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void setThreadNum(int num) {
		this.threadnum = num;
		if(this.visoperator != null){
			this.visoperator.setThreadNum(num);
		}
	}

	@Override
	public void setOperater(DMvisServerOperater operater) {
		this.visoperator = operater;
	}

}
