package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.MLModelRepository;
import com.example.demo.bean.MLModel;

@Service
public class MLModelService implements IMLModelService{
	@Autowired
	private MLModelRepository mlModelRepository;
	
	@Override
	public List<MLModel> findAll() {
		// TODO Auto-generated method stub
		List<MLModel> mlModelList = (List<MLModel>) mlModelRepository.findAll();
		return mlModelList;
	}

	@Override
	public void saveAndFlush(MLModel mlModel) {
		// TODO Auto-generated method stub
		mlModelRepository.saveAndFlush(mlModel);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		mlModelRepository.deleteById(id);
	}

	@Override
	public MLModel findOne(int id) {
		// TODO Auto-generated method stub
		MLModel mlModel = mlModelRepository.getOne(id);
		return mlModel;
	}

	@Override
	public void save(MLModel mlModel) {
		// TODO Auto-generated method stub
		mlModelRepository.save(mlModel);
	}

	@Override
	public List<MLModel> find(String modelName, String file, String feature, String start_time, String end_time) {
		// TODO Auto-generated method stub
		return mlModelRepository.find(modelName, file, feature, start_time, end_time);
	}
	
	@Override
	public MLModel getOneModel(int id) {
		// TODO Auto-generated method stub
		MLModel mlModel = mlModelRepository.getOneModel(id);
		return mlModel;
	}
}
