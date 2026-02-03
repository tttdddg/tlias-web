package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.Emp;
import org.example.pojo.EmpQueryParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

//操作员工信息
@Mapper
public interface EmpMapper {
    //原始分页实现
//    @Select("select count(*) from emp e left join dept d on e.dept_id=d.id")
//    public Long count();
//
//    @Select("select e.*,d.name deptName from emp e left join dept d on e.dept_id=d.id "+
//            "order by e.update_time desc limit #{start},#{pageSize}")
//    public List<Emp> list(Integer start,Integer pageSize);

//    @Select("select e.*,d.name deptName from emp e left join dept d on e.dept_id=d.id order by e.update_time desc")//换成XML映射文件
//    public List<Emp> list(String name, Integer gender, LocalDate begin, LocalDate end);
      public List<Emp> list(EmpQueryParam empQueryParam);

      @Options(useGeneratedKeys = true,keyProperty = "id")
      @Insert("insert into emp(username, password, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time)\n" +
              "values (#{username},#{password},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})")
      void  insert(Emp emp);

      void deleteByIds(@Param("ids") List<Integer> ids);

      Emp getById(Integer id);

      void updateById(Emp emp);

      @MapKey("pos")
      List<Map<String, Object>> countEmpJobData();

      @MapKey("name")
      List<Map<String, Object>> countEmpGenderData();

      @Select("select id,name,create_time createTime,update_time updateTime from emp order by update_time desc")
      List<Emp> findAll();

      @Select("select id,username,name from emp where username=#{username} and password=#{password}")
      Emp selectByUserAndPassword(Emp emp);
}
