package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.mapper.StudentMapper;
import org.example.pojo.ClazzQueryParam;
import org.example.pojo.PageResult;
import org.example.pojo.Student;
import org.example.pojo.StudentQueryParam;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = Exception.class, noRollbackFor = IllegalArgumentException.class)
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private final StudentMapper studentMapper;

    public StudentServiceImpl(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Transactional
    @Override
    public List<Student> findAll(){
        return studentMapper.findAll();
    }

    @Override
    public PageResult<Student> page(StudentQueryParam studentQueryParam){
        PageHelper.startPage(studentQueryParam.getPage(),studentQueryParam.getPageSize());

        List<Student> studentList=studentMapper.list(studentQueryParam);
        Page<Student> p=(Page<Student>) studentList;
        return new PageResult<Student>(p.getTotal(),p.getResult());
    }

    @Transactional
    @Override
    public void save(Student student){
        studentMapper.insert(student);
    }

    @Override
    public Student getInfo(Integer id){
        return studentMapper.getById(id);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void update(Student student){
        studentMapper.updateById(student);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void delete(List<Integer> ids){
        studentMapper.deleteByIds(ids);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void handleViolation(Integer studentId, Integer deductScore){
        Student stu=studentMapper.getById(studentId);
        if(stu==null){
             throw new RuntimeException("目标学生不存在");
        }

        Short currentViolationCount = stu.getViolationCount() == null ? 0 : stu.getViolationCount();
        Short currentViolationScore = stu.getViolationScore() == null ? 0 : stu.getViolationScore();

        stu.setViolationCount((short) (currentViolationCount + 1));
        stu.setViolationScore((short) (currentViolationScore + deductScore));

        studentMapper.updateById(stu);
    }
}
