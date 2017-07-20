package org.server.opt.services.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.server.opt.util.CommandConstant;
import org.server.opt.util.ShellUtils;

import com.jcraft.jsch.JSchException;

public class RemoteShellServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");

		try {
			ShellUtils.execCmdTest(CommandConstant.START_208, CommandConstant.USERNAME_208, 
					CommandConstant.PASSWORD_208, CommandConstant.IP_208, resp);
		} catch (JSchException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	
	
}
