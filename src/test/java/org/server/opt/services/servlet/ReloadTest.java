package org.server.opt.services.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.junit.Test;
import org.server.opt.util.LinuxCommandUtil;

public class ReloadTest {
	private static final String RELOAD = "cmd /c dir";
	
	public void test(){
		InputStream in = LinuxCommandUtil.nativeProcess(RELOAD);
		if(in == null){
			System.out.println("reload cmd process error!");
			return;
		}else{
			BufferedReader read = new BufferedReader(new InputStreamReader(in , Charset.forName("GBK")));
			String line = null;
			try {
				while ((line = read.readLine()) != null) {
					System.out.println(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				if(read != null){
					try {
						read.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				if(in != null){
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	} 
}
