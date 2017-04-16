package com.sms.handler.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sms.command.CommandBuilder;
import com.sms.command.SMSCommand;
import com.sms.command.util.CommandUtil;
import com.sms.constants.ResponseStatus;
import com.sms.handler.SMSHandler;
/**
 * 	Handler for user request. It will validate the request and send the appropriate response status back to the user.
 * 
 * 	@author Veenit Kumar
 *	@since 15-04-2017
 */
@Component
public class SMSHandlerImpl implements SMSHandler {
	private static final Logger logger = LoggerFactory.getLogger(SMSHandlerImpl.class);
	
	@Autowired
	CommandBuilder commandBuilder;
	
	/**
	 * Implementation for handleSmsRequest. This method executes user request and returns appropriate response.
	 * 
	 * @param smsContent sms content 
	 * @param senderDeviceId user device id.
	 * @return response status.
	 */
	@Override
	public String handleSmsRequest(String smsContent, String senderDeviceId) {
		String response = null;
		MDC.put("senderDeviceId", senderDeviceId);
		try{
			logger.info(String.format("Sms Content:: %s senderDeviceId:: %s",smsContent,senderDeviceId));
			String smsCommand = response = CommandUtil.getCommand(smsContent).toUpperCase();
			if(!ResponseStatus.ERR_UNKNOWN_COMMAND.equals(smsCommand)){
				SMSCommand command  = commandBuilder.buildCommand(smsCommand);
				response = command.execute(smsContent, senderDeviceId);
			}
			logger.debug(String.format("Returned response:: %s", response));
			
		}catch(Exception exception){
			logger.error(String.format("Exception received from application:: %s", exception.getMessage()));
		}finally{
			MDC.remove("senderDeviceId");
		}
		return response;
	}
}
