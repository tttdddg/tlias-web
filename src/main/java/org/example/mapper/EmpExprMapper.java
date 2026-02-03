package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.pojo.Emp;

import java.util.List;

@Mapper
public interface EmpExprMapper {
    //批量保存员工工作经历信息
    void insertBatch(Emp emp);

    void deleteByEmpIds(@Param("empIds") List<Integer> empIds);
}
