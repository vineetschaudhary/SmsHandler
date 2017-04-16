package com.sms.unit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sms.TestConfig;
import com.sms.command.SendCommand;
import com.sms.constants.ResponseStatus;
import com.sms.transfer.manager.TransferManager;
import com.sms.user.manager.UserManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class)
public class SendCommandUnitTest {
	@Autowired
	private UserManager userManager;
	@Autowired
	TransferManager transferManager;
	@Autowired
	SendCommand sendCommand;
	
	@Test
	public void testValidSendCommand() {
		String deviceId = "device1";
		String sendRequest = "send-100-abc";
		String userName = "abc";
		when(userManager.getUserNameForDeviceId(deviceId)).thenReturn(userName);
		when(userManager.existsUser(userName)).thenReturn(true);
		when(userManager.getBalance(userName)).thenReturn(new BigDecimal(200));
		assertEquals(ResponseStatus.OK, sendCommand.execute(sendRequest, deviceId));
		
	}
	
	@Test
	public void testUserNotExistSendCommand() {
		String deviceId = "device1";
		String sendRequest = "send-100-abc";
		String userName = "abc";
		when(userManager.getUserNameForDeviceId(deviceId)).thenReturn(userName);
		when(userManager.existsUser(userName)).thenReturn(false);
		assertEquals(ResponseStatus.ERR_NO_USER, sendCommand.execute(sendRequest, deviceId));
	}

	@Test
	public void testInsuficientFundSendCommand() {
		String deviceId = "device1";
		String sendRequest = "send-100-abc";
		String userName = "abc";
		when(userManager.getUserNameForDeviceId(deviceId)).thenReturn(userName);
		when(userManager.existsUser(userName)).thenReturn(true);
		when(userManager.getBalance(userName)).thenReturn(new BigDecimal(99));
		assertEquals(ResponseStatus.ERR_INSUFFICIENT_FUNDS, sendCommand.execute(sendRequest, deviceId));
	}

}
