package com.sms.user.manager;

import java.math.BigDecimal;

/**
 * Retrieve user information
 * 
 */
public interface UserManager {
	boolean existsUser(String username);
	BigDecimal getBalance(String username);
	String getUserNameForDeviceId(String deviceId); 
}
