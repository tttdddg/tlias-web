package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.ClazzOption;
import org.example.pojo.JobOption;
import org.example.pojo.Result;
import org.example.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/report")
@RestController
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/empJobData")
    public Result getEmpJobData(){
        log.info("查询员工职位人数");
        JobOption jo=reportService.getEmpJobData();
        return Result.success(jo);
    }

    @GetMapping("/empGenderData")
    public Result getEmpGenderData(){
        log.info("查询员工性别人数");
        List<Map<String,Object>> gl=reportService.getEmpGenderData();
        return Result.success(gl);
    }
    @GetMapping("/studentCountData")
    public Result countClazzNumData(){
        ClazzOption co=reportService.countClazzNumData();
        return Result.success(co);
    }

    @GetMapping("/studentDegreeData")
    public Result countStudentDegreeData(){
        List<Map<String,Object>> sd=reportService.countStudentDegreeData();
        return Result.success(sd);
    }
}
