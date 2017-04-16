package com.sms.constants;

/**
 * Holds all the commands.
 * 
 * @author veenit
 *
 */
public enum CommandEnum {
	BALANCE("BALANCE"),
	TOTAL_SENT("TOTAL-SENT"),
	SEND("SEND");

	public String commandName;
	
	CommandEnum(String commandName){
		this.commandName = commandName;
	}
	
	public String getValue(){
		return this.commandName;
	}
}
