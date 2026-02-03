package org.example.service;

import org.example.pojo.Emp;
import org.example.pojo.EmpQueryParam;
import org.example.pojo.LoginInfo;
import org.example.pojo.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
public interface EmpService {
//    PageResult<Emp> page(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end);
      PageResult<Emp> page(EmpQueryParam empQueryParam);

      void save(Emp emp);

      void delete(List<Integer> ids);

      Emp getInfo(Integer id);

      void update(Emp emp);

      List<Emp> findAll();

      LoginInfo login(Emp emp);
}
