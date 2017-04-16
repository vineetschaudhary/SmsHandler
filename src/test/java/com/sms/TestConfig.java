package com.sms;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sms.command.BalanceCommand;
import com.sms.command.CommandBuilder;
import com.sms.command.SendCommand;
import com.sms.command.TotalCommand;
import com.sms.handler.impl.SMSHandlerImpl;
import com.sms.transfer.manager.TransferManager;
import com.sms.user.manager.UserManager;

@Configuration
public class TestConfig {
	@Bean
	public UserManager getUserManager(){
		return Mockito.mock(UserManager.class);
	}
	
	@Bean
	public TransferManager getTransferManager(){
		return Mockito.mock(TransferManager.class);
	}
	
	@Bean
	public BalanceCommand getBalanceCommand(){
		return new BalanceCommand();
	}
	
	@Bean
	public SendCommand getSendCommand(){
		return new SendCommand();
	}
	
	@Bean
	public TotalCommand getTotalCommand(){
		return new TotalCommand();
	}
	
	@Bean
	public SMSHandlerImpl getSMSHandlerImpl(){
		return new SMSHandlerImpl();
	}
	
	@Bean
	public CommandBuilder getCommandBuilder(){
		return new CommandBuilder();
	}

}
