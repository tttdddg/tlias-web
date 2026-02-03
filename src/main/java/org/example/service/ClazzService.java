package org.example.service;

import org.example.pojo.Clazz;
import org.example.pojo.ClazzQueryParam;
import org.example.pojo.Emp;
import org.example.pojo.PageResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface ClazzService {
    PageResult<Clazz> page(ClazzQueryParam clazzQueryParam);

    void save(Clazz clazz);

    Clazz getInfo(Integer id);

    void update(Clazz clazz);

    void delete(List<Integer> ids);

    List<Clazz> findAll();
}
