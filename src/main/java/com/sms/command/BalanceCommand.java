package com.sms.command;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sms.constants.ResponseStatus;
import com.sms.user.manager.UserManager;

/**
 * Implementation class to execute Balance command.
 * 
 * 	@author Veenit Kumar
 *	@since 15-04-2017
 */
@Component
public class BalanceCommand implements SMSCommand{
	private static final Logger logger = LoggerFactory.getLogger(BalanceCommand.class);
	
	@Autowired
	private UserManager userManager; 
	
	/**
	 * Implemented execute method to retrieve user balance.
	 * 
	 * @param smsCommand sms request sent from user device.
	 * @param userDeviceId user device id.
	 * 
	 * @return response status
	 */
	@Override
	public String execute(String smsCommand, String userDeviceId) {
		String userName = userManager.getUserNameForDeviceId(userDeviceId);
		if(userManager.existsUser(userName)){
			BigDecimal balance = userManager.getBalance(userName);
			if(logger.isDebugEnabled()){
				logger.debug(String.format("Retrieved balance details:: balance:: %s  username:: %s  against sms command:: %s", balance, userName, smsCommand));
			}
			return String.valueOf(balance);
		}
		return ResponseStatus.ERR_NO_USER;
	}

	/**
	 * @param userManager the userManager to set
	 */
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	
	
}
