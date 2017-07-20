package org.server.opt.services.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.server.opt.util.CommandConstant;
import org.server.opt.util.LinuxCommandUtil;

public class ReloadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");

		Process pro = LinuxCommandUtil.nativeProcessByProcessBuilder(CommandConstant.RELOAD , CommandConstant.SHELL_FILE_DIR);
		if (pro == null) {
			resp.getWriter().print("reload cmd process error!");
			resp.getWriter().flush();
			return;
		}
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(pro.getInputStream()));
		BufferedReader stdError = new BufferedReader(new InputStreamReader(pro.getErrorStream()));
		String line = null;
		
		boolean error = true;
		while ((line = stdError.readLine()) != null) {
			if(error){
			resp.getWriter().println("ERROR:--------begin-----------");
			}
			
			resp.getWriter().println(line);
			
			if(error){
			resp.getWriter().println("ERROR:---------end----------");
			error = false;
			}
		}
		
		while ((line = stdInput.readLine()) != null) {
			resp.getWriter().println(line);
		}
		try {
			pro.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		resp.getWriter().flush();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
