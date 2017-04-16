package com.sms.command;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sms.constants.ResponseStatus;
import com.sms.transfer.manager.TransferManager;
import com.sms.user.manager.UserManager;

/**
 * 	Implementation class to execute Send command.
 * 
 * 	@author Veenit Kumar
 *	@since 15-04-2017
 */
@Component
public class SendCommand implements SMSCommand {
	private static final Logger logger = LoggerFactory.getLogger(SendCommand.class);
	
	@Autowired
	private UserManager userManager;
	@Autowired
	private TransferManager transferManager;
	
	/**
	 * Implemented execute method to send money to recipient. Recipient name will be given along with the command.
	 * For Example: SEND-100-RECIPIENTNAME
	 * 
	 * @param smsContent SMS request sent from user device.
	 * @param userDeviceId user device id.
	 * 
	 * @return response status
	 */
	@Override
	public String execute(String smsContent, String userDeviceId) {
		String userName = userManager.getUserNameForDeviceId(userDeviceId);
		if(logger.isDebugEnabled()){
			logger.debug(String.format("user name returned:: %s", userName));
		}
		String[] transferDetails = smsContent.split("-");
		if(userManager.existsUser(userName)){
			BigDecimal amount = new BigDecimal(transferDetails[1]);
			BigDecimal balance = userManager.getBalance(userName);
			if(logger.isDebugEnabled()){
				logger.debug(String.format("requested amount to transfer:: %s  balance in users account:: %s", amount, balance));
			}
			int comparisonResult = balance.compareTo(amount);
			if(comparisonResult == -1){
				return ResponseStatus.ERR_INSUFFICIENT_FUNDS;
			}
			if(logger.isDebugEnabled()){
				logger.debug(String.format("requested transfer details:: recipient name :: %s  transfer amount:: %s", transferDetails[2], amount));
			}
			transferManager.sendMoney(userName, transferDetails[2], amount);
			return ResponseStatus.OK;
		}
		return ResponseStatus.ERR_NO_USER;
	}
	
	/**
	 * @param userManager the userManager to set
	 */
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	/**
	 * @param transferManager the transferManager to set
	 */
	public void setTransferManager(TransferManager transferManager) {
		this.transferManager = transferManager;
	}
	
	
}
