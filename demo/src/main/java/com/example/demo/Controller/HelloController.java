package com.example.demo.Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.bean.MLModel;
import com.example.demo.engine.FileReader;
import com.example.demo.engine.JSHConnector;
import com.example.demo.service.IMLModelService;
import com.jcraft.jsch.JSchException;

@Controller
public class HelloController {
	@Autowired
	IMLModelService MLModelService;
	
	@RequestMapping("/hello")
	public String index(HttpServletRequest request,HttpSession sess, Model model) throws JSchException {
		List<MLModel> mlModelList = (List<MLModel>) MLModelService.findAll();

		List<String> currentFileList = new ArrayList<String>();
		
/*		JSHConnector jshConnector = new JSHConnector();
    	jshConnector.connect();*/
    	String ip = request.getRemoteAddr();
    	
    	FileReader fileReader = new FileReader();
    	ArrayList<File> fileList = fileReader.getAllFile(ip, ".dat");
        for(File tempFile : fileList) {
  		  if(tempFile.isFile()) {
  		    String tempPath=tempFile.getParent();
  		    String tempFileName=tempFile.getName();
  		    System.out.println("Path="+tempPath);
  		    System.out.println("FileName="+tempFileName);
  		    
  		    currentFileList.add(tempFileName);
  		    
  		    //jshConnector.setCommand("source /home/anaconda3/bin/activate tf;python /home/jwyeom/get_keys.py " + tempFileName + ";");
  		  }
        }
        
        model.addAttribute("fileList", currentFileList);
		model.addAttribute("name", "SpringBlog from Millky");
		model.addAttribute("mlModelList", mlModelList);
		return "hello";
	}
	
	
	@RequestMapping("/userList")
	public String userList(Model model) {
		return "userList";
	}
}
