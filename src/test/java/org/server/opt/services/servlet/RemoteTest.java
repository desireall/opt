package org.server.opt.services.servlet;

import org.junit.Test;
import org.server.opt.util.CommandConstant;
import org.server.opt.util.ShellUtils;

import com.jcraft.jsch.JSchException;

public class RemoteTest {

	
	public void remoteTest(){
		try {
			ShellUtils.execCmd(CommandConstant.UP_ECF_208, CommandConstant.USERNAME_208, 
					CommandConstant.PASSWORD_208, CommandConstant.IP_208);
		} catch (JSchException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}
}
