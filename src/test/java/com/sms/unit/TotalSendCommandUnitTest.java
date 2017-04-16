package com.sms.unit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.sms.TestConfig;
import com.sms.command.TotalCommand;
import com.sms.constants.ResponseStatus;
import com.sms.transfer.manager.TransferManager;
import com.sms.user.manager.UserManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class)
public class TotalSendCommandUnitTest {
	@Autowired
	private UserManager userManager;
	@Autowired
	TransferManager transferManager;
	@Autowired
	TotalCommand totalCommand;
	
	@Test
	public void testValidSingleRecipientTotalSendCommand() {
		String deviceId = "device1";
		String sendRequest = "TOTAL-SENT-FFRITZ";
		String userName = "FFRITZ";
		List<BigDecimal> totalAmount = new ArrayList<>();
		totalAmount.add(new BigDecimal(100));
		when(userManager.getUserNameForDeviceId(deviceId)).thenReturn(userName);
		when(userManager.existsUser(userName)).thenReturn(true);
		when(transferManager.getAllTransactions(userName, "FFRITZ")).thenReturn(totalAmount);
		assertEquals("100", totalCommand.execute(sendRequest, deviceId));
	}
	
	@Test
	public void testValidMultipleRecipientTotalSendCommand() {
		String deviceId = "device1";
		String sendRequest = "TOTAL-SENT-FFRITZ-MSMITH";
		String userName = "FFRITZ";
		List<BigDecimal> totalAmountFfritz = new ArrayList<>();
		List<BigDecimal> totalAmountMsmith = new ArrayList<>();
		totalAmountFfritz.add(new BigDecimal(100));
		totalAmountFfritz.add(new BigDecimal(100));
		totalAmountMsmith.add(new BigDecimal(200));
		totalAmountMsmith.add(new BigDecimal(200));
		when(userManager.getUserNameForDeviceId(deviceId)).thenReturn(userName);
		when(userManager.existsUser("FFRITZ")).thenReturn(true);
		when(userManager.existsUser("MSMITH")).thenReturn(true);
		when(transferManager.getAllTransactions(userName, "FFRITZ")).thenReturn(totalAmountFfritz);
		when(transferManager.getAllTransactions(userName, "MSMITH")).thenReturn(totalAmountMsmith);
		assertEquals("200,400", totalCommand.execute(sendRequest, deviceId));
	}
	
	@Test
	public void testInValidRecipientTotalSendCommand() {
		String deviceId = "device1";
		String sendRequest = "TOTAL-SENT-FFRITZ-MSMITH";
		String userName = "FFRITZ";
		List<BigDecimal> totalAmountFfritz = new ArrayList<>();
		List<BigDecimal> totalAmountMsmith = new ArrayList<>();
		totalAmountFfritz.add(new BigDecimal(100));
		totalAmountMsmith.add(new BigDecimal(200));
		when(userManager.getUserNameForDeviceId(deviceId)).thenReturn(userName);
		when(userManager.existsUser("FFRITZ")).thenReturn(false);
		when(userManager.existsUser("MSMITH")).thenReturn(true);
		when(transferManager.getAllTransactions(userName, "FFRITZ")).thenReturn(totalAmountFfritz);
		when(transferManager.getAllTransactions(userName, "MSMITH")).thenReturn(totalAmountMsmith);
		assertEquals(ResponseStatus.ERR_NO_USER, totalCommand.execute(sendRequest, deviceId));
	}
}
