package com.example.demo.Controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.bean.MLModel;
import com.example.demo.engine.CommandExecuter;
import com.example.demo.engine.FileReader;
import com.example.demo.service.IMLModelService;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

@Controller
public class testController {
	String username = "jwyeom";
	String host = "211.116.222.98";
	int port = 22;
    String password = "suresoft0";
    MultipartFile uploadFile;

    Session session = null;
    ChannelExec channel = null;
	
    @Autowired
	IMLModelService MLModelService;
	/**
	 * @param file
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws JSchException
	 * 
	 */
	/*@RequestMapping("/readMDF")
	@ResponseBody
	public String[] readMDf(@RequestParam("file") List<MultipartFile> files,
			Model model) throws UnsupportedEncodingException, IOException, JSchException {
		 // 1. JSch 객체를 생성한다.
		for(MultipartFile file : files) {
			uploadFile = MdfFileReader.readFile(file);	
		}
		
		
        
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
        ChannelExec channelExec = (ChannelExec) channel;
        System.out.println("==> Connected to" + host);
        
        channelExec.setCommand("source /home/anaconda3/bin/activate tf;python /home/jwyeom/get_keys.py " + uploadFile.getOriginalFilename() + ";");
   
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
         
        final int exitStatus = channel.getExitStatu           s();
        System.out.println("exitStatus : " + exitStatus);
        result=result.substring(result.indexOf("'") + 1, result.lastIndexOf("'"));
        String[] words = result.split("', '");
        return words;
	}*/
	
    
	/**
	 * @param mlList
	 * @param model
	 * @param request
	 * @param sess
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws JSchException
	 * 
	 * 검증 시작
	 * 1. 공유 폴더 내 dat 파일 불러오기
	 * 2. csv로 떨구기 - exportCSV.py 실행
	 * 3. 공유 폴더 내 csv 파일 불러오기
	 * 3. modelTest 실행
	 */
	@RequestMapping("/execTest")
	@ResponseBody
	public void execTest(@RequestParam(value="mlList", required=false) List<Integer> mlList,
			Model model,HttpServletRequest request,HttpSession sess) throws UnsupportedEncodingException, IOException, JSchException {
		
		CommandExecuter cmdExecuter = new CommandExecuter();
		String ip = request.getRemoteAddr();
		List<String> currentFileList = new ArrayList<String>();

    	FileReader fileReader = new FileReader();
    	ArrayList<File> fileList = fileReader.getAllFile(ip, ".dat");
    	for(File tempFile : fileList) {
    		
    		//csv 만들기
    		String tempPath=tempFile.getParent();
			String tempFileName=tempFile.getName();  
			String command = cmdExecuter.inputCommand("python exportCSV.py " + tempPath + '\\' + tempFileName);
			
			//결과 받기
			String result = cmdExecuter.execCommand(command);
			result=result.substring(result.indexOf("'") + 1, result.lastIndexOf("'"));
			String[] words = result.split("', '");
			List<Integer> idxList = new ArrayList<Integer>();
        	if(tempFile.isFile()) {
				for(int i = 0; i < mlList.size(); i++) {
					MLModel mlmodel = MLModelService.getOneModel(mlList.get(i));
					String[] mlModelFeature = mlmodel.getFeature().split(",");
					System.out.println(mlModelFeature.length);
					System.out.println(mlModelFeature[1]);
					String indexList = "";
					//index 알아내기
					for(int j = 0; j < mlmodel.getFeature_cnt(); j++)
						idxList.add(Arrays.binarySearch(words, mlModelFeature[j]));
					for(int j = 0; j < mlmodel.getFeature_cnt(); j++) {
						indexList += " " + idxList.get(j);
					}
					//실행
					//String cmd = cmdExecuter.inputCommand("python " + mlmodel.getFile() + " " + tempPath+"\\" + tempFileName + " " + mlmodel.getFeature_cnt() + indexList);
					System.out.println("python " + mlmodel.getFile() + " " + tempPath+"\\" + tempFileName + " " + mlmodel.getFeature_cnt() + indexList);
				}
  			}
        	
			System.out.println(result);
			currentFileList.add(tempFileName);
  		    //jshConnector.setCommand("source /home/anaconda3/bin/activate tf;python /home/jwyeom/get_keys.py " + tempFileName + ";");
    	}        
	}
}
