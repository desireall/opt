package org.server.opt.util;

public class CommandConstant {
	public static final String CHARSET = "UTF-8";
	
	public static final String SHELL_FILE_DIR = "/home/t6Test/yilegameT6/trunk/server/stable";
	
	//199重载
	public static final String RELOAD = "./reloadgame.sh";

	public static final String RELOADRESULT = "end reload gameserver";
	

	public static final String COMPILE = "./compile.sh";
	
	public static final String COMPILESUCCESS = "pack jar complete";

	public static final String COMPILEFAIL = "compile java file have error";
	
	
	//199关闭
	public static final String STOP = "./stop.sh";
	
	//199开启
	public static final String START = "./start.sh";
	
	
	//重启gameserver
	public static final String RESTART = "./restart.sh";
	
	public static final String RESTARTSUCCESS = "game server restart";
	
	public static final String TEST_WIN= "cmd /c dir";
	public static final String TEST_LINUX = "pwd";
	
	
	public static final String TEST_FILE_DIR = "E:\\yilegameT6\\trunk\\dev\\server\\sgame";

	public static final String TEST_CMD = "python E:/yilegameT6/trunk/dev/server/sgame/run.py";
//	public static final String TEST_CMD = "python run.py";
	
	
	
	

	public static final String USERNAME_208 ="test";
	
	public static final String PASSWORD_208 ="test";
	
	public static final String IP_208 ="192.168.1.208";
	
	//-----------------208--------------
	public static final String REMOTE_ENVIRONMENT = "source /etc/profile; ";    // 远程调用 需要在命令之前 添加环境
	
	public static final String UP_ECF_208 = REMOTE_ENVIRONMENT+" sh /home/test/work/cbt3/server/stable/svnupecf.sh";
	
	public static final String STOP_208 = REMOTE_ENVIRONMENT+" sh /home/test/work/cbt3/server/stable/stopgame.sh";

	public static final String START_208 = REMOTE_ENVIRONMENT+" sh /home/test/work/cbt3/server/stable/startgame.sh";
}
