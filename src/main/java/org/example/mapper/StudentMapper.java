package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.ClazzQueryParam;
import org.example.pojo.Student;
import org.example.pojo.StudentQueryParam;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {
    @Select("select count(*) from student where clazz_id=#{clazzId}")
    Integer countByClazzId(Integer id);

    @Select("select id,name,create_time createTime,update_time updateTime from student order by update_time desc")
    List<Student> findAll();

    public List<Student> list(StudentQueryParam studentQueryParam);

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into student(name,no,gender,phone,id_card,is_college,address,degree,graduation_date,clazz_id,create_time,update_time)\n"+
            "values (#{name},#{no},#{gender},#{phone},#{idCard},#{isCollege},#{address},#{degree},#{graduationDate},#{clazzId},#{createTime},#{updateTime})")
    void insert(Student student);

    @Select("select id,name,create_time,update_time from student where id=#{id}")
    Student getById(Integer id);

    void updateById(Student student);

//    void handleViolation(@Param("studentId") Integer studentId,
//                        @Param("deductScore") Integer deductScore);

    void deleteByIds(@Param("ids") List<Integer> ids);

    List<Map<String,Object>> countStudentDegreeData();
}
