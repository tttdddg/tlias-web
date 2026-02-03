package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.mapper.StudentMapper;
import org.example.pojo.*;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/students")
@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentMapper studentMapper;

    @GetMapping("/list")
    public Result list(){
        List<Student> stuList=studentService.findAll();
        return Result.success(stuList);
    }

    @GetMapping
    public Result page(StudentQueryParam studentQueryParam){
        PageResult<Student> pageResult=studentService.page(studentQueryParam);
        return Result.success(pageResult);
    }

    @PostMapping
    public Result save(@RequestBody Student student){
        studentService.save(student);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        Student stu=studentService.getInfo(id);
        return Result.success(stu);
    }

    @PutMapping
    public Result update(@RequestBody Student student){
        studentService.update(student);
        return Result.success();
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        studentService.delete(ids);
        return Result.success();
    }

    @PutMapping("/violation/{id}/{score}")
    public Result handleViolation(@PathVariable("id") Integer studentId,
                                  @PathVariable("score") Integer deductScore){
        if(studentId==null){
            return Result.error("学生ID不能为空");
        }
        if(deductScore==null||deductScore<0){
            return Result.error("违纪扣分数值必须是正整数");
        }

        try{
            studentService.handleViolation(studentId,deductScore);
            return Result.success();
        }catch(RuntimeException e){
            return Result.error(e.getMessage());
        }
    }
}