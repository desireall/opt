package org.server.opt.util;
import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletResponse;

import com.jcraft.jsch.Channel;  
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;  
import com.jcraft.jsch.JSchException;  
import com.jcraft.jsch.Session;  
  
  
  
public class ShellUtils {  
    private static JSch jsch;  
    private static Session session;  
  
      
    /** 
     * 连接到指定的IP 
     *  
     * @throws JSchException 
     */  
    public static void connect(String user, String passwd, String host) throws JSchException {  
        jsch = new JSch();  
        session = jsch.getSession(user, host, 22);  
        session.setPassword(passwd);  
          
        java.util.Properties config = new java.util.Properties();  
        config.put("StrictHostKeyChecking", "no");  
        session.setConfig(config);  
          
        session.connect();  
    }  
  
    /** 
     * 执行相关的命令 
     * @throws JSchException  
     */  
    public static void execCmd(String command, String user, String passwd, String host) throws JSchException {  
        connect(user, passwd, host);
          
        BufferedReader reader = null;  
        Channel channel = null; 
  
        try {  
        	String page_message = null;
                channel = session.openChannel("exec");  
                ((ChannelExec) channel).setCommand(command);  
                  
                channel.setInputStream(null);  
                ((ChannelExec) channel).setErrStream(System.err);  
  
                channel.connect();  
                InputStream in = channel.getInputStream();  
                
                 //会出现 重复读取
                reader = new BufferedReader(new InputStreamReader(in));  
                String buf = null;  
                while ((buf = reader.readLine()) != null) {  
                    System.out.println(buf);  
                }  
                
                
//                byte[] tmp=new byte[1024];
//                while(true){
//                  while(in.available()>0){
//                    int i=in.read(tmp, 0, 1024);
//                    if(i<0)break;
//                    page_message=new String(tmp, 0, i);
//                    System.out.print(page_message);
//                  }
//                  if(channel.isClosed()){
//                    if(in.available()>0) continue; 
//                    System.out.println("exit-status: "+channel.getExitStatus());
//                    break;
//                  }
//                  try{Thread.sleep(1000);}catch(Exception ee){}
//                }
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (JSchException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                reader.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
            channel.disconnect();  
            session.disconnect();  
        }  
    }  
     
    
    /** 
     * 执行相关的命令 
     * @throws JSchException  
     */  
    public static void execCmdTest(String command, String user, String passwd, String host, HttpServletResponse resp) throws JSchException {  
        connect(user, passwd, host);
          
        BufferedReader reader = null;  
        Channel channel = null; 
  
        try {  
                channel = session.openChannel("exec");
                ((ChannelExec) channel).setCommand(command);  
                  
                channel.setInputStream(null);  
                ((ChannelExec) channel).setErrStream(System.err);  
  
                channel.connect();  
                InputStream in = channel.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));  
                String buf = null;  
                while ((buf = reader.readLine()) != null) {  
//                    System.out.println(buf);
                	resp.getWriter().println(buf);
                }  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (JSchException e) {  
            e.printStackTrace();  
        } finally {
        	LinuxCommandUtil.closeStream(reader);
            channel.disconnect();  
            session.disconnect();  
        }  
    }
    
}  