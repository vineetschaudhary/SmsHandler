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
import com.sms.command.BalanceCommand;
import com.sms.constants.ResponseStatus;
import com.sms.user.manager.UserManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class)
public class BalanceCommandUnitTest {
	@Autowired
	UserManager userManager;
	@Autowired
	BalanceCommand balanceCommand;
	
	@Test
	public void testValidBalanceCommand() {
		String deviceId = "device1";
		String sendRequest = "BALANCE";
		String userName = "abc";
		when(userManager.getUserNameForDeviceId(deviceId)).thenReturn(userName);
		when(userManager.existsUser(userName)).thenReturn(true);
		when(userManager.getBalance(userName)).thenReturn(new BigDecimal(100));
		assertEquals("100", balanceCommand.execute(sendRequest, deviceId));
	}
	
	@Test
	public void testInValidUserBalanceCommand() {
		String deviceId = "device1";
		String sendRequest = "BALANCE";
		String userName = "abc";
		when(userManager.getUserNameForDeviceId(deviceId)).thenReturn(userName);
		when(userManager.existsUser(userName)).thenReturn(false);
		when(userManager.getBalance(userName)).thenReturn(new BigDecimal(100));
		assertEquals(ResponseStatus.ERR_NO_USER, balanceCommand.execute(sendRequest, deviceId));
	}

}
