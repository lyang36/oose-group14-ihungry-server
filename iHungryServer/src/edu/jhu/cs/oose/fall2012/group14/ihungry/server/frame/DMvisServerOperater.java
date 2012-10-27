package edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame;

import java.awt.Image;

/**
 * This interface operating the server.
 * @author lyang
 */
public interface DMvisServerOperater {
	/**
	 * Start running the program with the given data 
	 */
	public void startRun();
	
	/**
	 * set up the parameters for running the program
	 */
	public void setParameters(VisParameter par);
	
	/**
	 * Force stop running the program
	 */
	public void stopRun();
	
	/**
	 * whether the running is finished
	 * @return true/false
	 */
	public boolean isFinished();
	
	/**
	 * @return the percentage of the process
	 */
	public int getPercentage();
	
	/**
	 * return the image gotten by the dmvis
	 */
	public Image getImage();
	
	/**
	 * return the visdata
	 * @return
	 */
	public VisData getData();
	
	
	/**
	 * return the healpix data file name 
	 */
	public String getHealPixDataFile();
	
	/**
	 * return the image data file name
	 */
	public String getImgDataFile();
	
	/**
	 * return the image file name
	 */
	public String getImgFile();
	
	/**
	 * setting up the thread number
	 */
	public void setThreadNum(int num);
	
	
	

}
