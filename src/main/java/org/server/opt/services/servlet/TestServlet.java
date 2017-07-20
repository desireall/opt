package org.server.opt.services.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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

public class TestServlet extends HttpServlet{

	//是否有人使用
	public static AtomicBoolean  status = new AtomicBoolean(false);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		resp. setCharacterEncoding("UTF-8");
		
		if(status.get()){
			resp.getWriter().print("其他人启动中 ......  请稍后重试");
			resp.getWriter().flush();
			return;
		}
		
		status.set(true);
		InputStream in = LinuxCommandUtil.nativeProcess(getTestComand());
		if(in == null){
			resp.getWriter().print("test cmd process error!");
			resp.getWriter().flush();
			status.set(false);
			return;
		}else{
			BufferedReader read = new BufferedReader(new InputStreamReader(in , Charset.forName("GBK")));
			String line = null;
			try {
				int i = 0;
				while ((line = read.readLine()) != null) {
//					System.err.println(line);
					resp.getWriter().println(line);
					 i++;
					if(line.contains(CommandConstant.RELOADRESULT) || i >= 20){
						resp.getWriter().println("test sucessfully!");
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				if(read != null){
					read.close();
				}
				
				if(in != null){
					in.close();
				}
			}
			try {
				Thread.sleep(1 * 1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			resp.getWriter().flush();
			status.set(false);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	
	
	public String getTestComand(){
		Properties prop = System.getProperties();
		String curOs = prop.getProperty("os.name");
		if("linux".equalsIgnoreCase(curOs)){
			return CommandConstant.TEST_LINUX;
		}else{
			return CommandConstant.TEST_WIN;
		}
	}
	
}
