package com.example.demo.engine;

import java.io.IOException;
import java.io.InputStream;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class JSHConnector {
	Session session = null;
	ChannelExec channel = null;
	String username = "jwyeom";
	String host = "211.116.222.98";
	int port = 22;
	String password = "suresoft0";
	ChannelExec channelExec;
	
	public void connect() throws JSchException {
		JSch jsch = new JSch();
        // 2. 세션 객체를 생성한다 (사용자 이름, 접속할 호스트, 포트를 인자로 준다.) 
        session = jsch.getSession(username, host, port);
     
        // 3. 패스워드를 설정한다.
        session.setPassword(password);
     
        // 4. 세션과 관련된 정보를 설정한다.
        java.util.Properties config = new java.util.Properties();
        // 4-1. 호스트 정보를 검사하지 않는다.
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
     
        // 5. 접속한다.
        session.connect();
     
        // 6. sftp 채널을 연다.
        channel = (ChannelExec) session.openChannel("exec");
     
        // 8. 채널을 SSH용 채널 객체로 캐스팅한다
        channelExec = (ChannelExec) channel;
        System.out.println("==> Connected to" + host);
	}
	
	public void setCommand(String command) throws IOException, JSchException {
		channelExec.setCommand(command);
		//channelExec.setCommand("source /home/anaconda3/bin/activate tf;python /home/jwyeom/get_keys.py " + tempFileName + ";");
	
		int BUFFER_SIZE = 256;
		InputStream inputStream = channel.getInputStream(); // <- 일반 출력 스트림
		byte[] buffer = new byte[256];
		final InputStream errStream = channel.getErrStream();
		channelExec.connect();
		String result = "";
	
		while(true) {
			while (inputStream.available() > 0) {
				int i = inputStream.read(buffer, 0, BUFFER_SIZE);
				if (i < 0) {
					break;
				}
				result += new String(buffer, 0, i);
				System.out.println(result);
			}
				  
			while (errStream.available() > 0) {
				int i = errStream.read(buffer, 0, BUFFER_SIZE);
				if (i > 0) {
				// System.err.println(new String(buffer, 0, i));
				}
			}
				  
			if (channel.isClosed()) {
				if (inputStream.available() > 0 || errStream.available() > 0) {
					continue;
				}
				break;
			}
			  
		}
		final int exitStatus = channel.getExitStatus();
		System.out.println("exitStatus : " + exitStatus);
	}
}
