package org.example.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.mapper.EmpExprMapper;
import org.example.mapper.EmpMapper;
import org.example.pojo.*;
import org.example.service.EmpService;
import org.example.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Transactional
@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpExprMapper empExprMapper;

    public EmpServiceImpl(EmpMapper empMapper) {
        this.empMapper = empMapper;
    }

    //    @Autowired
//    private EmpMapper empMapper;
//
//    @Override
//    public PageResult<Emp> page(Integer page, Integer pageSize){
//        Long total=empMapper.count();
//
//        Integer start=(page-1)*pageSize;
//        List<Emp> rows=empMapper.list(start,pageSize);
//
//        return new PageResult<Emp>(total,rows);
//    }
    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam){
        //设置分页参数
        PageHelper.startPage(empQueryParam.getPage(),empQueryParam.getPageSize());

        List<Emp> empList=empMapper.list(empQueryParam);
        Page<Emp> p=(Page<Emp>) empList;
        return new PageResult<Emp>(p.getTotal(),p.getResult());
    }

    @Transactional
    @Override
    public void save(Emp emp){
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.insert(emp);

        List<EmpExpr> exprList=emp.getExprList(); //保存员工工作经历信息
        if(!CollectionUtils.isEmpty(exprList)){
            exprList.forEach(empExpr ->{
                empExpr.setEmpId(emp.getId());
            });
            empExprMapper.insertBatch(emp);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void delete(List<Integer> ids){
        empMapper.deleteByIds(ids);

        empExprMapper.deleteByEmpIds(ids);
    }

    @Override
    public Emp getInfo(Integer id){
        return empMapper.getById(id);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void update(Emp emp){
        //根据ID修改员工基本信息
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);

        //根据ID修改员工工作经历
        //先删除
        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));
        //再添加
        List<EmpExpr> exprList=emp.getExprList();
        if(!CollectionUtils.isEmpty(exprList)){
            exprList.forEach(empExpr -> empExpr.setEmpId(emp.getId()));
            empExprMapper.insertBatch(emp);
        }
    }

    @Transactional
    @Override
    public List<Emp> findAll(){
        return empMapper.findAll();
    }

    @Override
    public LoginInfo login(Emp emp){
        Emp e=empMapper.selectByUserAndPassword(emp);
        if(e!=null){
            Map<String,Object> claims=new HashMap<>();
            claims.put("id",e.getId());
            claims.put("username",e.getUsername());
            String jwt=JwtUtils.generateJwt(claims);

            return new LoginInfo(e.getId(),e.getUsername(),e.getPassword(),e.getName(),jwt);
        }
        return null;
    }
}