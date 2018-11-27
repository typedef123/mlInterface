package com.example.demo.Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.bean.MLModel;
import com.example.demo.engine.CommandExecuter;
import com.example.demo.engine.FileReader;
import com.example.demo.service.IMLModelService;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * @author enffl
 *
 */
@Controller
public class testController {
	String username = "jwyeom";
	String host = "211.116.222.98";
	int port = 22;
	String password = "suresoft0";
	MultipartFile uploadFile;

	Session session = null;
	ChannelExec channel = null;

	private boolean executing = false;
	private String nowFile = "";
	private String nowIp = "";
	private String nowModel = "";
	private LocalDateTime startTest = LocalDateTime.now();


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
		String[] words = null;
		String tempPath = null;
		String tempFileName = null;
		System.out.println("size : " + fileList.size());
		for(File tempFile : fileList) {
			//csv 만들기
			tempPath =tempFile.getParent();
			tempFileName =tempFile.getName();  
			String command = cmdExecuter.inputCommand("python exportCSV.py " + tempPath + '\\' + tempFileName);
			String result = cmdExecuter.execCommand(command);
			System.out.println("result : " +result);
			result=result.substring(result.indexOf("'") + 1, result.lastIndexOf("'"));
			words = result.split("', '");
		}

		ArrayList<File> csvFileList = FileReader.getAllFile(ip, ".csv");
		for(File tempFile : csvFileList) {
			//결과 받기			
			List<Integer> idxList = new ArrayList<Integer>();
			if(tempFile.isFile()) {
				for(int i = 0; i < mlList.size(); i++) {
					MLModel mlmodel = MLModelService.getOneModel(mlList.get(i));

					//현재 진행 정보 저장
					executing = true;
					tempFileName =tempFile.getName();  
					nowFile=tempFileName;
					nowIp = ip;
					nowModel = mlmodel.getName();
					startTest = LocalDateTime.now();



					String[] mlModelFeature = mlmodel.getFeature().split(",");

					String indexList = ""; 
					//index 알아내기
					for(int j = 0; j < mlmodel.getFeature_cnt(); j++)
						idxList.add(Arrays.binarySearch(words, mlModelFeature[j]));
					for(int j = 0; j < mlmodel.getFeature_cnt(); j++) {
						indexList += " " + idxList.get(j);
					}

					//실행
					String cmd = cmdExecuter.inputCommand("python execute.py " + tempPath + " " + tempPath+'\\' + mlmodel.getFile() + " " + tempPath+'\\' + tempFileName + " " + mlmodel.getFeature_cnt() + indexList);
					//String cmd = cmdExecuter.inputCommand("ipconfig");
					String pythonResult = cmdExecuter.execCommand(cmd);
					System.out.println(pythonResult);
					System.out.println("python execute.py " + tempPath + " " + tempPath+'\\' + mlmodel.getFile() + " " + tempPath+'\\' + tempFileName + " " + mlmodel.getFeature_cnt() + indexList);

					//실행완료
					//실행 결과 파일 읽기
					BufferedReader br = null;
					br = Files.newBufferedReader(Paths.get(".\\output.csv"));
					Charset.forName("UTF-8");
					String line = "";
					List<List<String>> ret = new ArrayList<List<String>>();
					while((line = br.readLine()) != null) {
						List<String> tmpList = new ArrayList<String>();
						String array[] = line.split(",");
						tmpList = Arrays.asList(array);
						ret.add(tmpList);
					}
					System.out.println("size : " + ret.size());
					br.close();
					//원래 파일 읽기
					br = Files.newBufferedReader(Paths.get("D:\\\\share\\" + tempFileName));
					Charset.forName("UTF-8");
					line = "";
					List<List<String>> originFile = new  ArrayList<List<String>>();
					while((line = br.readLine()) != null) {
						List<String> tmpList = new ArrayList<String>();
						String array[] = line.split(",");
						tmpList = Arrays.asList(array);
						originFile.add(tmpList);
					}
					System.out.println("size : " + originFile.size());

					br.close();
					BufferedWriter bufWriter = Files.newBufferedWriter(Paths.get("D:\\\\share\\" + tempFileName));
					int k = 0;
					boolean space = false;
					if(k < originFile.size()/2) {
						space = true;
					}
					for(List<String> newLine : originFile) {
						List<String> list = newLine;

						for(String data: list) {
							bufWriter.write(data);
							bufWriter.write(",");
						}
						if(k == 0) {
							bufWriter.write("LABEL");
						} else if(space == true && k % 2 == 1) {
							bufWriter.write("");
						} else if((space == false && k < 10 || space==true && k < 24)) {
							bufWriter.write("0");
						} else if(space == true && k > 24){
							bufWriter.write(ret.get((k-24)/2).get(0));
						}
						k++;
						bufWriter.newLine();
					}
					bufWriter.close();
				}
			}
		}
		executing=false;
	}


	/**
	 * @param model
	 * @return MAIN PAGE
	 * 현재 실행 정보를 담아 리턴
	 */
	@RequestMapping("/main")
	public String getNow(Model model) {
		model.addAttribute("executing", executing);
		model.addAttribute("nowFile", nowFile);
		model.addAttribute("nowIp", nowIp);
		model.addAttribute("nowModel", nowModel);
		model.addAttribute("startTest", startTest);
		return "main";
	}
}
