package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.mapper.ClazzMapper;
import org.example.mapper.StudentMapper;
import org.example.pojo.*;
import org.example.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
public class ClazzServiceImpl implements ClazzService {
    @Autowired
    private ClazzMapper clazzMapper;
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public PageResult<Clazz> page(ClazzQueryParam clazzQueryParam){
        PageHelper.startPage(clazzQueryParam.getPage(),clazzQueryParam.getPageSize());

        List<Clazz> clazzList=clazzMapper.list(clazzQueryParam);
        Page<Clazz> p=(Page<Clazz>) clazzList;
        return new PageResult<Clazz>(p.getTotal(),p.getResult());
    }

    @Transactional
    @Override
    public void save(Clazz clazz){
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.insert(clazz);
    }

    @Override
    public Clazz getInfo(Integer id){
        return clazzMapper.getById(id);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void update(Clazz clazz){
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.updateById(clazz);
    }

//    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void delete(List<Integer> ids){
        for(Integer id:ids){
            // 1. 校验班级是否存在
            Clazz clazz=clazzMapper.getById(id);
            if (clazz == null) {
                throw new RuntimeException("班级ID:" + id + "不存在");
            }
            // 2. 校验班级下是否有学生
            Integer studentCount = studentMapper.countByClazzId(id);
            if (studentCount != null && studentCount > 0) {
                // 3. 有学生则抛自定义异常
                throw new ClazzHasStudentException("对不起,该班级下有学生,不能直接删除");
            }
        }

        clazzMapper.deleteByIds(ids);
    }

    @Transactional
    @Override
    public List<Clazz> findAll(){
        return clazzMapper.findAll();
    }
}
