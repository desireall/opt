package org.server.opt.util;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class LinuxCommandUtil {
	
	
	/**
	 * 本地执行
	 */
	public static Process nativeProcessTest(String cmds) {
		Process pro = null;
		try {
			pro = Runtime.getRuntime().exec(cmds);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return pro;
	}
	
	
	
	/**
	 * 本地执行
	 */
	public static InputStream nativeProcess(String cmds) {
		Process pro = null;
		try {
			pro = Runtime.getRuntime().exec(cmds);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		try {
			pro.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
		
		InputStream in = pro.getInputStream();
		return in;
	}
	
	/**
	 * 
	 * @param cmds
	 * @return
	 */
	public static Process nativeProcessByProcessBuilder(String cmds , String filePath) {
		ProcessBuilder pro = new ProcessBuilder(cmds);
		pro.directory(new File(filePath));
		Process value = null;
		try {
			value = pro.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	
	
	
	
    /**
     * 远程连接 执行 命令
     * @param host   
     * @param userName
     * @param password
     * @param cmds      
     * @param filePath   脚本文件位置
     */
	public static void remoteProcess(String host , String userName , String password , String cmds , String filePath) {
		
		
	}
	
	
	
    public static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (Exception e) {
            }
        }
    }
}
