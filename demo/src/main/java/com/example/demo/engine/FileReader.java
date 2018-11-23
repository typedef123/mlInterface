package com.example.demo.engine;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
public class FileReader {
	
	public static ArrayList<File> getAllFile(String ip, String format) {
		String path="\\\\" + ip + "\\" + "share";
		File dirFile=new File(path);
		File []fileList=dirFile.listFiles();
		ArrayList<File> resultFile = new ArrayList<File>(); 
		for(File tempFile : fileList) {
		  if(tempFile.getName().endsWith(format) == true) {
			  resultFile.add(tempFile);
		    String tempPath=tempFile.getParent();
		    String tempFileName=tempFile.getName();
		    System.out.println("Path="+tempPath);
		    System.out.println("FileName="+tempFileName);
		  }
		}
		return resultFile;
	}
	
	public static String getIP() {
		InetAddress local;
		try {
		    local = InetAddress.getLocalHost();
		    String ip = local.getHostAddress();
		    System.out.println("local ip : "+ip);
		    return ip;
		} catch (UnknownHostException e1) {
		    e1.printStackTrace();
		}
		return null;
	}
}
