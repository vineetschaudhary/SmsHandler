package com.sms;



import org.junit.Assert;
import org.junit.Test;

import com.sms.command.util.CommandUtil;
import com.sms.constants.ResponseStatus;


public class CommandUtilTest {

	private final String _validSendCommand = "send-100-a";
	private final String _validSendCommand_1 = "SEND-100-FFRITZ";
	private final String _invalidSendCommand = "send-100-";
	private final String _invalidSendCommand_1 = "send-";
	private final String _invalidSendCommand_2 = "send";
	
	private final String _validBalanceCommand = "Balance";
	private final String _validBalanceCommand_1 = "BALANCE";
	private final String _invalidBalanceCommand = "BALANCE-";
	
	private final String _validTotalSentCommand = "TOTAL-SENT-FFRITZ";
	private final String _validTotalSentCommand_1 = "TOTAL-SENT-FFRITZ-MSMITH";
	private final String _validTotalSentCommand_2 = "total-sent-FFRITZ-MSMITH";
	private final String _invalidTotalSentCommand = "total-sent-";
	private final String _invalidTotalSentCommand_1 = "total-sent";
	
	
	@Test
	public void testSendCommand() {
		String validReturnedCommand = CommandUtil.getCommand(_validSendCommand);
		String validReturnedCommand_1 = CommandUtil.getCommand(_validSendCommand_1);
		String invalidReturnedCommand = CommandUtil.getCommand(_invalidSendCommand);
		String invalidReturnedCommand_1 = CommandUtil.getCommand(_invalidSendCommand_1);
		String invalidReturnedCommand_2 = CommandUtil.getCommand(_invalidSendCommand_2);
		
		Assert.assertEquals("send", validReturnedCommand);
		Assert.assertEquals("SEND", validReturnedCommand_1);
		Assert.assertEquals(ResponseStatus.ERR_UNKNOWN_COMMAND, invalidReturnedCommand);
		Assert.assertEquals(ResponseStatus.ERR_UNKNOWN_COMMAND, invalidReturnedCommand_1);
		Assert.assertEquals(ResponseStatus.ERR_UNKNOWN_COMMAND, invalidReturnedCommand_2);
		//Assert.assertEquals("total-sent-amount", CommandUtil.isValidCommand("total-sent-amount"));
	}
	
	@Test
	public void testBalanceCommand() {
		String validReturnedCommand = CommandUtil.getCommand(_validBalanceCommand);
		String validReturnedCommand_1 = CommandUtil.getCommand(_validBalanceCommand_1);
		String invalidReturnedCommand = CommandUtil.getCommand(_invalidBalanceCommand);
		String invalidReturnedCommand_1 = CommandUtil.getCommand("");
		
		Assert.assertEquals("Balance", validReturnedCommand);
		Assert.assertEquals("BALANCE", validReturnedCommand_1);
		Assert.assertEquals(ResponseStatus.ERR_UNKNOWN_COMMAND, invalidReturnedCommand);
		Assert.assertEquals(ResponseStatus.ERR_UNKNOWN_COMMAND, invalidReturnedCommand_1);
	}
	
	@Test
	public void testTotalSentCommand() {
		String validReturnedCommand = CommandUtil.getCommand(_validTotalSentCommand);
		String validReturnedCommand_1 = CommandUtil.getCommand(_validTotalSentCommand_1);
		String validReturnedCommand_2 = CommandUtil.getCommand(_validTotalSentCommand_2);
		String invalidReturnedCommand = CommandUtil.getCommand(_invalidTotalSentCommand);
		String invalidReturnedCommand_1 = CommandUtil.getCommand(_invalidTotalSentCommand_1);
		String invalidReturnedCommand_2 = CommandUtil.getCommand("");
		
		Assert.assertEquals("TOTAL-SENT", validReturnedCommand);
		Assert.assertEquals("TOTAL-SENT", validReturnedCommand_1);
		Assert.assertEquals("total-sent", validReturnedCommand_2);
		Assert.assertEquals(ResponseStatus.ERR_UNKNOWN_COMMAND, invalidReturnedCommand);
		Assert.assertEquals(ResponseStatus.ERR_UNKNOWN_COMMAND, invalidReturnedCommand_1);
		Assert.assertEquals(ResponseStatus.ERR_UNKNOWN_COMMAND, invalidReturnedCommand_2);
	}

}
