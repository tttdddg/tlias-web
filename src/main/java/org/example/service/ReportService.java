package org.example.service;

import org.example.pojo.ClazzOption;
import org.example.pojo.JobOption;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
public interface ReportService {
    JobOption getEmpJobData();

    List<Map<String,Object>> getEmpGenderData();

    ClazzOption countClazzNumData();

    List<Map<String, Object>> countStudentDegreeData();
}
