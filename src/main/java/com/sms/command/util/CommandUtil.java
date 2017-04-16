package com.sms.command.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.sms.constants.ResponseStatus;

/**
 * This class is the utility class providing utility methods to validate command 
 * and return the command to get the actual command executor.
 * 
 * 	@author veenit kumar
 *	@since 15-04-2017
 */
public class CommandUtil {
	/**
	 * Pattern to get the exact command name.
	 */
	private static StringBuffer validCommands = new StringBuffer("\\bsend\\b")
			.append("|\\bbalance\\b")
			.append("|\\btotal-sent\\b");
	/**
	 * Pattern to validate the commands.
	 */
	private static StringBuffer commandPatterns = new StringBuffer("BALANCE$")
			.append("|total-sent-\\w[a-zA-Z]*.{0,}")
			.append("|send-\\d[0-9]*-\\w[a-zA-Z]*.{0,}");
	
	private final static Pattern validCommandPattern = Pattern.compile(validCommands.toString(),Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
	private final static Pattern commandPattern = Pattern.compile(commandPatterns.toString(),Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
	
	/**
	 * Validate the use input against the patterns.
	 * 
	 * @param command smsContent sent from user device.
	 * @return true or false
	 */
	public static boolean isValidCommand(String command){
		if(StringUtils.isEmpty(command)){
			return false;
		}
		
		Matcher validCommandMatcher = validCommandPattern.matcher(command);
		if(validCommandMatcher.find()){
			Matcher matcher = commandPattern.matcher(command);
			return matcher.find();
		}
		return false;
	}
	
	/**
	 * Retrieve the command name from smsContent sent from user device.
	 * for example - 'sent-100-username' will return sent as command name.
	 * 
	 * @param command smsContent sent from user device
	 * @return command name.
	 */
	public static String getCommand(String command){
		if(isValidCommand(command)){
			Matcher validCommandMatcher = validCommandPattern.matcher(command);
			if(validCommandMatcher.find()){
				return validCommandMatcher.group();
			}
		}
		return ResponseStatus.ERR_UNKNOWN_COMMAND;
	}
}
