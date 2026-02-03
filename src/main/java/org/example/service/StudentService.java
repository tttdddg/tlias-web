package org.example.service;

import org.apache.ibatis.annotations.Param;
import org.example.pojo.ClazzQueryParam;
import org.example.pojo.PageResult;
import org.example.pojo.Student;
import org.example.pojo.StudentQueryParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public interface StudentService {
    List<Student> findAll();

    PageResult<Student> page(StudentQueryParam studentQueryParam);

    void save(Student student);

    Student getInfo(Integer id);

    void update(Student student);

    void delete(List<Integer> ids);

    void handleViolation(Integer studentId, Integer deductScore);
}
