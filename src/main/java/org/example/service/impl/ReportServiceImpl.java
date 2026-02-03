package org.example.service.impl;

import jakarta.annotation.Resource;
import org.example.mapper.ClazzMapper;
import org.example.mapper.EmpMapper;
import org.example.mapper.StudentMapper;
import org.example.pojo.ClazzOption;
import org.example.pojo.JobOption;
import org.example.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private EmpMapper empMapper;
    @Resource
    private StudentMapper studentMapper;
    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    public JobOption getEmpJobData() {
        List<Map<String,Object>> list=empMapper.countEmpJobData();

        List<Object> jobList=list.stream().map(dataMap->dataMap.get("pos")).toList();
        List<Object> dataList=list.stream().map(dataMap->dataMap.get("num")).toList();

        return new JobOption(jobList,dataList);
    }

    @Override
    public List<Map<String,Object>> getEmpGenderData(){
        return empMapper.countEmpGenderData();
    }

    @Override
    @Transactional(readOnly = true)
    public ClazzOption countClazzNumData(){
        List<Map<String,Object>> list=clazzMapper.countClazzNumData();

        List<Object> clazzList=list.stream().map(dataMap->dataMap.get("pos")).toList();
        List<Object> dataList=list.stream().map(dataMap->dataMap.get("num")).toList();

        return new ClazzOption(clazzList,dataList);
    }

    @Override
    public List<Map<String,Object>> countStudentDegreeData(){
        List<Map<String, Object>> originData = studentMapper.countStudentDegreeData();
        //转换为前端ECharts标准格式（name/value）
        return originData.stream()
                .map(originMap -> {
                    Map<String, Object> newMap = new HashMap<>();
                    newMap.put("name", originMap.get("degrees"));
                    newMap.put("value", originMap.get("value"));
                    return newMap;
                })
                .collect(Collectors.toList());
    }
}
