package com.masters.finalexercise.common.service;

import com.masters.finalexercise.common.model.BaseModel;
import com.masters.finalexercise.exceptions.RecordNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BaseService<T extends BaseModel> {

    Page<T> findAll(Integer pageNo, Integer pageSize, String sortBy);

    T findById(Long id) throws RecordNotFoundException;

    T save(T entity);

    T delete(Long id) throws RecordNotFoundException;

}
