package com.sms.command;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sms.constants.CommandEnum;

/**
 * 	Build command object and return object based on command name.
 * 
 * 	@author Veenit Kumar
 *	@since 15-04-2017
 */
@Component
public class CommandBuilder {
	@Autowired
	private BalanceCommand balanceCommand;
	@Autowired
	private SendCommand sendCommand;
	@Autowired
	private TotalCommand totalCommand;
	
	private static Map<String, SMSCommand> commands= new HashMap<>();

	/**
	 * Retrieve command based on command name passed.
	 * 
	 * @param commandName command name
	 * @return SMSCommand object.
	 */
	public SMSCommand buildCommand(String commandName){
		initializeCommands();
		return commands.get(commandName);
	}
	
	private void initializeCommands(){
		commands.put(CommandEnum.BALANCE.getValue(), balanceCommand);
		commands.put(CommandEnum.SEND.getValue(), sendCommand);
		commands.put(CommandEnum.TOTAL_SENT.getValue(), totalCommand);
	}
}
