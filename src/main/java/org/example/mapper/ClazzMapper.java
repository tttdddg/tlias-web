package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.Clazz;
import org.example.pojo.ClazzQueryParam;
import org.example.pojo.PageResult;

import java.util.List;
import java.util.Map;

@Mapper
public interface ClazzMapper {
    public List<Clazz> list(ClazzQueryParam clazzQueryParam);

    Long count(@Param("name") String name,
               @Param("begin") String begin,
               @Param("end") String end);

    @Options
    @Insert("insert into clazz(name,room,begin_date, end_date,master_id,subject)\n"+
            "values (#{name},#{room},#{beginDate},#{endDate},#{masterId},#{subject})")
    void insert(Clazz clazz);

    @Select("select id,name,create_time,update_time from clazz where id=#{id}")
    Clazz getById(Integer id);

    void updateById(Clazz clazz);

    void deleteByIds(@Param("ids") List<Integer> ids);

    @Select("select id,name,create_time createTime,update_time updateTime from clazz order by update_time desc")
    List<Clazz> findAll();

//    @MapKey("pos")
    List<Map<String,Object>> countClazzNumData();
}
