package com.sms.command;

/**
 * Functional interface to commands. 
 * All new commands need to implement this interface and implement execute method.
 * 
 * 	@author Veenit Kumar
 *	@since 15-04-2017
 */
public interface SMSCommand {
	/**
	 * Execute user request.
	 * 
	 * @param smsCommand sent from user device.
	 * @param userDeviceId user device id to retrieve username.
	 * @return	response.
	 */
	public String execute(String smsCommand, String userDeviceId);
}
