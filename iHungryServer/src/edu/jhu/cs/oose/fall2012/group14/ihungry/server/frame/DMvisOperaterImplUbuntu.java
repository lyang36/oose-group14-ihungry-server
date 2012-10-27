package edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.imageio.ImageIO;

import edu.jhu.pha.idies.lyang.dmvisual.framework.DMvisServerOperater;
import edu.jhu.pha.idies.lyang.dmvisual.framework.VisData;
import edu.jhu.pha.idies.lyang.dmvisual.framework.VisParameter;
import edu.jhu.pha.idies.lyang.dmvisual.misc.Parameters;
public class DMvisOperaterImplUbuntu implements DMvisServerOperater{
    Runtime rt = null;//Runtime.getRuntime();
    Process pr = null;//rt.exec("/Users/lyang/Documents/temp/remote_dmvis.sh");
    int percentage = 0;
    BufferedReader input = null;// new BufferedReader(new InputStreamReader(pr.getInputStream()));
    boolean isfinished = false;
    int threadnum = 0;
    //String server = "lyang@mantiqueira.sdss.pha.jhu.edu";
    String serverprogram = "vglrun /data0/Projects/dmvisserver/dmvis";
    String conffile = "";
    String remotepicfile = "";
    String localpicfile = "";
    String localjpgfile = "";
    String localconffile = "";
    Parameters parameter = new Parameters();
    
    String runnableSH;
    
    String setRunnableBatchFile(){
    	remotepicfile = "/home/lyang/Documents/temp/skymap" + threadnum + ".tga";
    	localpicfile = "/home/lyang/Documents/temp/skymap" + threadnum + ".tga";
    	localjpgfile = "/home/lyang/Documents/temp/skymap" + threadnum + ".jpg";
    	conffile = "/data0/Projects/dmvisserver/config_uni_" + threadnum + ".ini";
    	localconffile = conffile;
    	runnableSH = "/home/lyang/Documents/temp/remote_dmvis" + threadnum + ".sh";
    	parameter.setParameter(Parameters.healpix_file, "/home/lyang/Documents/temp/skyhealpix" + threadnum +".data");
    	parameter.setParameter(Parameters.output_file, "/home/lyang/Documents/temp/skyup_low" + threadnum + ".data");
    	parameter.setParameter(Parameters.picture_file, "/home/lyang/Documents/temp/skymap" + threadnum + ".tga");
    	parameter.saveToFile(localconffile);
    	String batchcommand =  
    	    		serverprogram + " --conf " + conffile + "\n"
    				+ "convert "+ localpicfile + " " + localjpgfile;  
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(runnableSH));
			fos.write(batchcommand.getBytes());
			fos.close();
			Runtime.getRuntime().exec("chmod 755 " + runnableSH).waitFor();
			Thread.sleep(100);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
    	return runnableSH;
    }
   
    
    
	@Override
	public void startRun() {
		rt = Runtime.getRuntime();
		try {
			pr = rt.exec(setRunnableBatchFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		
		(new Thread(){
			public void run(){
				//Character.toChars
                int ch = 0;
                while(ch != -1) {
                	try{
                		ch=input.read();
                	}catch(Exception e){
                		break;
                	}
                	if(ch == -1){
                		break;
                	}
                	System.out.print(Character.toChars(ch)[0]);
                	if(Character.toChars(ch)[0] == '*'){
                		percentage += 2;
                	}
                    
                }
                try {
					pr.waitFor();
					isfinished = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void setParameters(VisParameter par) {
		this.parameter = (Parameters) par;
		
	}

	@Override
	public void stopRun() {
		pr.destroy();
	}

	@Override
	public boolean isFinished() {
		return isfinished;
	}

	@Override
	public int getPercentage() {
		return percentage;
	}

	@Override
	public Image getImage() {
		Image im = null;
		try {
		    // Create a URL for the image's location
			File file = new File(localjpgfile);

		    java.awt.Toolkit.getDefaultToolkit();
			// Get the image
		    im = ImageIO.read(file);//Toolkit.getDefaultToolkit().createImage(url);
		} catch (Exception e) {
		} 
		return im;
	}

	@Override
	public VisData getData() {
		return null;
	}

	@Override
	public String getHealPixDataFile() {
		return (String) parameter.getValue(Parameters.healpix_file);
	}

	@Override
	public String getImgDataFile() {
		return (String) parameter.getValue(Parameters.output_file);
	}

	@Override
	public String getImgFile() {
		return localjpgfile;
	}

	@Override
	public void setThreadNum(int num) {
		this.threadnum = num;
	}
	
	

}
