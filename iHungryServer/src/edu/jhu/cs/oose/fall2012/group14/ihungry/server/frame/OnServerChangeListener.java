package edu.jhu.cs.oose.fall2012.group14.ihungry.server.frame;

import java.awt.Image;

/**
 * with server information change
 * @author lyang
 *
 */
public interface OnServerChangeListener {
	
	/**
	 * do something when the percentage is changing
	 */
	public void onPercentageChange(int percent);
	
	/**
	 * do something when the running is finished
	 */
	public void onFinish(Image img);
	
	/**
	 * do something when the server get a error
	 */
	public void onError(int errcode);
}
