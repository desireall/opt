package org.server.opt.services.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.server.opt.util.CommandConstant;
import org.server.opt.util.LinuxCommandUtil;

public class StartBatServlet extends HttpServlet {

	// 是否有人使用
	public static AtomicBoolean status = new AtomicBoolean(false);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");

//		if (status.get()) {
//			resp.getWriter().print("其他人启动中 ......  请稍后重试");
//			resp.getWriter().flush();
//			return;
//		}
		Properties prop = System.getProperties();
		String curOs = prop.getProperty("os.name");
		if(!"linux".equalsIgnoreCase(curOs)){
			resp.getWriter().print("该功能 linux 不支持");
			resp.getWriter().flush();
		}
		

		// -------------------restart

		Process pro = LinuxCommandUtil.nativeProcessByProcessBuilder(CommandConstant.TEST_CMD,
				CommandConstant.TEST_FILE_DIR);
//		Process pro = LinuxCommandUtil.nativeProcessTest(CommandConstant.TEST_CMD);
		if (pro == null) {
			resp.getWriter().println("start cmd process error!");
			resp.getWriter().flush();
			return;
		} else {
			resp.getWriter().println("-----start----------------");
		}

		BufferedReader stdInput = new BufferedReader(new InputStreamReader(pro.getInputStream() , Charset.forName("GBK")));
		BufferedReader stdError = new BufferedReader(new InputStreamReader(pro.getErrorStream(), Charset.forName("GBK")));
		String line = null;
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
		resp.getWriter().flush();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
