package org.server.opt.services.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.server.opt.util.CommandConstant;
import org.server.opt.util.LinuxCommandUtil;

public class RestartServlet extends HttpServlet {

	//是否有人使用
	public static AtomicBoolean  use = new AtomicBoolean(false);
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");

		if(use.get()){
			resp.getWriter().print("其他人重启中 ...... 请稍后重试");
			resp.getWriter().flush();
			return;
		}
		
		use.set(true);
		String compileStr = req.getParameter("compile");
		boolean compile = false;

		if (compileStr != null && compileStr.length() != 0) {
			compile = Boolean.valueOf(compileStr);
		}

		resp.getWriter().println("参数: compile: " + compile);

		String line = null;
		if (compile) {
			// 编译
			Process pro = LinuxCommandUtil.nativeProcessByProcessBuilder(CommandConstant.COMPILE,
					CommandConstant.SHELL_FILE_DIR);
			if (pro == null) {
				resp.getWriter().println("compile cmd process error!");
				resp.getWriter().flush();
				use.set(false);
				return;
			} else {
				resp.getWriter().println("compile cmd processing --------------------------");
			}

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(pro.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(pro.getErrorStream()));

			try {
				pro.waitFor();
				boolean error = true;
				while ((line = stdError.readLine()) != null) {
					if (error) {
						resp.getWriter().println("ERROR:--------begin-----------");
					}
					resp.getWriter().println(line);
					if (error) {
						resp.getWriter().println("ERROR:---------end----------");
						error = false;
					}
				}

				while ((line = stdInput.readLine()) != null) {
					resp.getWriter().println(line);
					if (line.contains(CommandConstant.COMPILESUCCESS)) {
						resp.getWriter().println("compile sucessfully!");
						break;
					} else if (line.contains(CommandConstant.COMPILEFAIL)) {
						resp.getWriter().println("compile fail!");
						resp.getWriter().flush();
						use.set(false);
						return;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				LinuxCommandUtil.closeStream(stdInput);
				LinuxCommandUtil.closeStream(stdError);
			}
		}

		// -------------------restart
		Process pro = LinuxCommandUtil.nativeProcessByProcessBuilder(CommandConstant.RESTART,
				CommandConstant.SHELL_FILE_DIR);
		if (pro == null) {
			resp.getWriter().println("restart cmd process error!");
			resp.getWriter().flush();
			use.set(false);
			return;
		} else {
			resp.getWriter().println("-----start restart  ----------------");
		}

		BufferedReader stdInput = new BufferedReader(new InputStreamReader(pro.getInputStream()));
		BufferedReader stdError = new BufferedReader(new InputStreamReader(pro.getErrorStream()));
		try {
			pro.waitFor();
			boolean error = true;
			while ((line = stdError.readLine()) != null) {
				if (error) {
					resp.getWriter().println("ERROR:--------begin-----------");
				}
				resp.getWriter().println(line);
				if (error) {
					resp.getWriter().println("ERROR:---------end----------");
					error = false;
				}
			}

			while ((line = stdInput.readLine()) != null) {
				resp.getWriter().println(line);
				if (line.contains(CommandConstant.RESTARTSUCCESS)) {
					resp.getWriter().println("restart server sucessfully!");
					resp.getWriter().flush();
					use.set(false);
					return;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			LinuxCommandUtil.closeStream(stdInput);
			LinuxCommandUtil.closeStream(stdError);
			use.set(false);
		}
		// TODO 默认重启成功
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
