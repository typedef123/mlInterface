package com.example.demo.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.bean.MLModel;
import com.example.demo.engine.FileUploader;
import com.example.demo.service.IMLModelService;

@Controller
public class ModelController {
	@Autowired
	IMLModelService MLModelService;
	 
    private static String UPLOADED_FOLDER = "C:\\upload\\";
   
    
	@RequestMapping("/modelManager")
	public String model(Model model) {
		List<MLModel> mlModelList = (List<MLModel>) MLModelService.findAll();
		model.addAttribute("MLModelList", mlModelList);
		model.addAttribute("localDateTimeFormat", DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
		return "modelManager";
	}
	
	@RequestMapping("/addFile")
	@ResponseBody
	public MLModel addFile(@RequestParam("modelName") String modelName,
			@RequestParam(value="file") MultipartFile file,
			@RequestParam("feature") String feature,
			@RequestParam("description") String description,
    		Model model) {

      /*  if (file.isEmpty()) {
        	model.addAttribute("message", "Please select a file to upload");
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
        */
		
		FileUploader fileupload = new FileUploader();
		fileupload.uploadFile(file);
		MLModel mlModel = new MLModel();
		mlModel.setName(modelName);
		mlModel.setFeature(feature);
		int feature_cnt = feature.split(",").length;
		mlModel.setFeature_cnt(feature_cnt);
		mlModel.setRegister_Time(LocalDateTime.now());
		mlModel.setFile(UPLOADED_FOLDER + file.getOriginalFilename());
		mlModel.setDescription(description);
		MLModelService.saveAndFlush(mlModel);
		model.addAttribute("localDateTimeFormat", DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        return mlModel;
	}
	
	@RequestMapping("/editModel")
	public String editModel(@RequestParam("name") String modelName,
			@RequestParam(value="id") int id,
			@RequestParam("feature") String feature,
			@RequestParam("description") String description,
			Model model) {
		MLModel mlModel = MLModelService.getOneModel(id);
		mlModel.setFeature(feature);
		mlModel.setName(modelName);
		int feature_cnt = feature.split(",").length;
		mlModel.setFeature_cnt(feature_cnt);
		mlModel.setRegister_Time(LocalDateTime.now());
		mlModel.setDescription(description);
		MLModelService.save(mlModel);
		return "editModel";
	}
	
	@RequestMapping("/searchModel")
	@ResponseBody
	public List<MLModel> searchModel(@RequestParam(value="subject", defaultValue="") String subject,
			@RequestParam(value="name", defaultValue="") String name,
			@RequestParam(value="start_time", defaultValue="1990-01-01") String start_time,
			@RequestParam(value="end_time", defaultValue="2999-12-31") String end_time) {
		String modelName ="";
		String file ="";
		String feature ="";
		if(subject.equals("model")) {
			modelName=name;
		} else if(subject.equals("file")) {
			file=name;
		} else if(subject.equals("feature")){
			feature=name;
		}
		List<MLModel> mlModel = MLModelService.find(modelName, file, feature, start_time, end_time); 
		return mlModel;
	}
	
	@RequestMapping("/deleteModel")
	@ResponseBody
	public void deleteModel(@RequestParam("id") int id) {
		MLModelService.delete(id);
	}
	
	@RequestMapping("/getModel")
	@ResponseBody
	public MLModel getModel(@RequestParam("id") int id) {
		MLModel mlModel = MLModelService.getOneModel(id);
		return mlModel;
	}
}
