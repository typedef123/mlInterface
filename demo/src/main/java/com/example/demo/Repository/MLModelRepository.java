package com.example.demo.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.bean.MLModel;
@Repository
public interface MLModelRepository extends JpaRepository<MLModel, Integer>{
	
	@Query(value="select * from MLMODEL where name like %:modelName% and file like %:file% and feature like %:feature% and register_time >= to_timestamp(:start_time, 'YYYY-MM-DD') and register_time <= to_timestamp(:end_time, 'YYYY-MM-DD')" ,nativeQuery = true)
	List<MLModel> find(@Param("modelName") String modelName, @Param("file") String file, @Param("feature") String feature, @Param("start_time") String start_time, @Param("end_time") String end_time);

	@Query(value="select * from MLMODEL where id= :id", nativeQuery=true)
	MLModel getOneModel(@Param("id") int id);
}
