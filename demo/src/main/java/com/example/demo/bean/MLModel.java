package com.example.demo.bean;

import java.time.LocalDateTime;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="Mlmodel")
public class MLModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private LocalDateTime register_Time;
	private String name;
	private String file;
	private int feature_cnt;
	private String feature;
	private String description;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDateTime getRegister_Time() {
		return register_Time;
	}
	public void setRegister_Time(LocalDateTime register_Time) {
		this.register_Time = register_Time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	
	public int getFeature_cnt() {
		return feature_cnt;
	}
	public void setFeature_cnt(int feature_cnt) {
		this.feature_cnt = feature_cnt;
	}
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}

	
}
