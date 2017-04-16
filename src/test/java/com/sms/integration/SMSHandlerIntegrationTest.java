package com.sms.integration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sms.TestConfig;
import com.sms.constants.ResponseStatus;
import com.sms.handler.impl.SMSHandlerImpl;
import com.sms.transfer.manager.TransferManager;
import com.sms.user.manager.UserManager;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class)
public class SMSHandlerIntegrationTest {
	@Autowired
	private UserManager userManager;
	@Autowired
	TransferManager transferManager;
	@Autowired
	SMSHandlerImpl sMSHandlerImpl;
	
	@Test
	public void testValidSendCommand() {
		String deviceId = "device1";
		String sendRequest = "send-100-abc";
		String userName = "abc";
		Mockito.when(userManager.getUserNameForDeviceId(deviceId)).thenReturn(userName);
		Mockito.when(userManager.existsUser(userName)).thenReturn(true);
		Mockito.when(userManager.getBalance(userName)).thenReturn(new BigDecimal(200));
		Assert.assertEquals(ResponseStatus.OK, sMSHandlerImpl.handleSmsRequest(sendRequest, deviceId));
		
	}
	
	@Test
	public void testUserNotExistSendCommand() {
		String deviceId = "device1";
		String sendRequest = "send-100-abc";
		String userName = "abc";
		Mockito.when(userManager.getUserNameForDeviceId(deviceId)).thenReturn(userName);
		Mockito.when(userManager.existsUser(userName)).thenReturn(false);
		Assert.assertEquals(ResponseStatus.ERR_NO_USER, sMSHandlerImpl.handleSmsRequest(sendRequest, deviceId));
	}

	@Test
	public void testInvalidCommandSendCommand() {
		String deviceId = "device1";
		String sendRequest = "send-100-";
		String userName = "abc";
		Mockito.when(userManager.getUserNameForDeviceId(deviceId)).thenReturn(userName);
		Mockito.when(userManager.existsUser(userName)).thenReturn(true);
		Mockito.when(userManager.getBalance(userName)).thenReturn(new BigDecimal(200));
		Assert.assertEquals(ResponseStatus.ERR_UNKNOWN_COMMAND, sMSHandlerImpl.handleSmsRequest(sendRequest, deviceId));
	}
	
	@Test
	public void testInsuficientFundSendCommand() {
		String deviceId = "device1";
		String sendRequest = "send-100-abc";
		String userName = "abc";
		Mockito.when(userManager.getUserNameForDeviceId(deviceId)).thenReturn(userName);
		Mockito.when(userManager.existsUser(userName)).thenReturn(true);
		Mockito.when(userManager.getBalance(userName)).thenReturn(new BigDecimal(99));
		Assert.assertEquals(ResponseStatus.ERR_INSUFFICIENT_FUNDS, sMSHandlerImpl.handleSmsRequest(sendRequest, deviceId));
	}

	@Test
	public void testValidBalanceCommand() {
		String deviceId = "device1";
		String sendRequest = "BALANCE";
		String userName = "abc";
		Mockito.when(userManager.getUserNameForDeviceId(deviceId)).thenReturn(userName);
		Mockito.when(userManager.existsUser(userName)).thenReturn(true);
		Mockito.when(userManager.getBalance(userName)).thenReturn(new BigDecimal(100));
		Assert.assertEquals("100", sMSHandlerImpl.handleSmsRequest(sendRequest, deviceId));
	}
	
	@Test
	public void testInValidUserBalanceCommand() {
		String deviceId = "device1";
		String sendRequest = "BALANCE";
		String userName = "abc";
		Mockito.when(userManager.getUserNameForDeviceId(deviceId)).thenReturn(userName);
		Mockito.when(userManager.existsUser(userName)).thenReturn(false);
		Mockito.when(userManager.getBalance(userName)).thenReturn(new BigDecimal(100));
		Assert.assertEquals(ResponseStatus.ERR_NO_USER, sMSHandlerImpl.handleSmsRequest(sendRequest, deviceId));
	}
	
	@Test
	public void testValidSingleRecipientTotalSendCommand() {
		String deviceId = "device1";
		String sendRequest = "TOTAL-SENT-FFRITZ";
		String userName = "FFRITZ";
		List<BigDecimal> totalAmount = new ArrayList<>();
		totalAmount.add(new BigDecimal(100));
		Mockito.when(userManager.getUserNameForDeviceId(deviceId)).thenReturn(userName);
		Mockito.when(userManager.existsUser(userName)).thenReturn(true);
		Mockito.when(transferManager.getAllTransactions(userName, "FFRITZ")).thenReturn(totalAmount);
		Assert.assertEquals("100", sMSHandlerImpl.handleSmsRequest(sendRequest, deviceId));
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
		Mockito.when(userManager.getUserNameForDeviceId(deviceId)).thenReturn(userName);
		Mockito.when(userManager.existsUser("FFRITZ")).thenReturn(true);
		Mockito.when(userManager.existsUser("MSMITH")).thenReturn(true);
		Mockito.when(transferManager.getAllTransactions(userName, "FFRITZ")).thenReturn(totalAmountFfritz);
		Mockito.when(transferManager.getAllTransactions(userName, "MSMITH")).thenReturn(totalAmountMsmith);
		Assert.assertEquals("200,400", sMSHandlerImpl.handleSmsRequest(sendRequest, deviceId));
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
		Mockito.when(userManager.getUserNameForDeviceId(deviceId)).thenReturn(userName);
		Mockito.when(userManager.existsUser("FFRITZ")).thenReturn(false);
		Mockito.when(userManager.existsUser("MSMITH")).thenReturn(true);
		Mockito.when(transferManager.getAllTransactions(userName, "FFRITZ")).thenReturn(totalAmountFfritz);
		Mockito.when(transferManager.getAllTransactions(userName, "MSMITH")).thenReturn(totalAmountMsmith);
		Assert.assertEquals(ResponseStatus.ERR_NO_USER, sMSHandlerImpl.handleSmsRequest(sendRequest, deviceId));
	}
}
