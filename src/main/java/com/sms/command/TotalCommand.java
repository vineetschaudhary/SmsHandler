package com.sms.command;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sms.constants.ResponseStatus;
import com.sms.transfer.manager.TransferManager;
import com.sms.user.manager.UserManager;

/**
 * 	Implementation class to execute Total amount sent to recipients command.
 * 
 * 	@author Veenit Kumar
 *	@since 15-04-2017
 */
@Component
public class TotalCommand implements SMSCommand {
	private static final Logger logger = LoggerFactory.getLogger(TotalCommand.class);
	
	@Autowired
	private UserManager userManager;
	@Autowired
	private TransferManager transferManager;
	
	/**
	 * Implemented execute method to retrieve total amount transferred to recipients. Recipients name will be given along with the command.
	 * For Example: TOTAL-SENT-RECIPIENTNAME1-RECIPIENTNAME2
	 * 
	 * @param smsCommand SMS request sent from user device.
	 * @param userDeviceId user device id.
	 * 
	 * @return response status
	 */
	@Override
	public String execute(String smsCommand, String userDeviceId) {
		String recipient = smsCommand.replaceFirst("TOTAL-SENT-", "");
		String[] recipients = recipient.split("-");
		List<String> summedAmount = new ArrayList<>();
		if(allUsersValid(recipients)){
			String senderUsername = userManager.getUserNameForDeviceId(userDeviceId);
			if(logger.isDebugEnabled()){
				logger.debug(String.format("sender user name:: %s", senderUsername));
			}
			for(String recipientUsername : recipients){
				List<BigDecimal> totalAmount = transferManager.getAllTransactions(senderUsername, recipientUsername);
				summedAmount.add(String.valueOf(totalAmount.stream().reduce(BigDecimal::add).get()));
			}
			return summedAmount.stream().collect(Collectors.joining(","));
		}
		return ResponseStatus.ERR_NO_USER;
	}

	/**
	 * Validate all recipients sent along with command. In case any of the user is invalid return false.
	 * 
	 * @param recipients recipients name.
	 * @return true or false.
	 */
	private boolean allUsersValid(String[] recipients){
		for(String recipient : recipients){
			if(logger.isDebugEnabled()){
				logger.debug(String.format("recipient name :: %s", recipient));
			}
			if(!userManager.existsUser(recipient)){
				return false; 
			}
		}
		return true;
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
