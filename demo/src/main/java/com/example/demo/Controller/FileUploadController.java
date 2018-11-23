package com.example.demo.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.engine.FileReader;
import com.example.demo.engine.JSHConnector;
import com.jcraft.jsch.JSchException;

@Controller
public class FileUploadController {
    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "C:\\upload\\";

    @RequestMapping("/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
    		Model model) {

        if (file.isEmpty()) {
        	model.addAttribute("message", "Please select a file to upload");
            return "uploadStatus";
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            model.addAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "uploadStatus";
    }

    @RequestMapping("/readAllFile")
    public String uploadStatus() {
        return "readAllFile";
    }
    
   
	
    @RequestMapping("/readfile")
    @ResponseBody
    public String readfile(HttpServletRequest request,HttpSession sess, Model model) throws JSchException, IOException {
    	 
    	JSHConnector jshConnector = new JSHConnector();
    	jshConnector.connect();
    	String ip = request.getRemoteAddr();
    	
    	FileReader fileReader = new FileReader();
    	ArrayList<File> fileList = fileReader.getAllFile(ip, ".dat");
        String result="";
        for(File tempFile : fileList) {
  		  if(tempFile.isFile()) {
  		    String tempPath=tempFile.getParent();
  		    String tempFileName=tempFile.getName();
  		    System.out.println("Path="+tempPath);
  		    System.out.println("FileName="+tempFileName);
  		    
  		    //jshConnector.setCommand("source /home/anaconda3/bin/activate tf;python /home/jwyeom/get_keys.py " + tempFileName + ";");
  		    result+=tempPath+tempFileName + "\t";
  		  }
        }
  	    
        return result;
    }
}
