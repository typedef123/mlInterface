package com.example.demo.engine;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandExecuter {
	private StringBuffer buffer;
	
	public String inputCommand(String cmd) {
		buffer = new StringBuffer();
		buffer.append("cmd.exe ");
		buffer.append("/c ");
		buffer.append(cmd);
		
		return buffer.toString();
	}
	
	public String execCommand(String cmd) {
		BufferedReader bufferedReader;
		StringBuffer readBuffer;
		Process process;

		try {
			process = Runtime.getRuntime().exec(cmd);
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			String line = null;
			readBuffer = new StringBuffer();
			
			while((line = bufferedReader.readLine()) != null) {
				readBuffer.append(line);
				readBuffer.append("\n");
			}
			
			return readBuffer.toString();
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}
