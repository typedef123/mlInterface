package com.example.demo.service;

import java.util.List;

import com.example.demo.bean.MLModel;

public interface IMLModelService {
	public List<MLModel> findAll();
	
	public void saveAndFlush(MLModel mlModel);
	
	public void delete(int id);
	
	public MLModel findOne(int id);
	
	public void save(MLModel mlModel);
	
	public List<MLModel> find(String modelName, String file, String feature, String start_time, String end_time);
	
	public MLModel getOneModel(int id);
}
