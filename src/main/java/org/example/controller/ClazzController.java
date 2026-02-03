package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.*;
import org.example.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/clazzs")
@RestController
public class ClazzController {
    @Autowired
    private ClazzService clazzService;

    @GetMapping
    public Result page(ClazzQueryParam clazzQueryParam){
        System.out.println("班级列表查询:");
        log.info("分页查询：{}",clazzQueryParam);
        PageResult<Clazz> pageResult= clazzService.page(clazzQueryParam);
        return Result.success(pageResult);
    }

    @PostMapping
    public Result save(@RequestBody Clazz clazz){
        clazzService.save(clazz);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        Clazz clazz=clazzService.getInfo(id);
        return Result.success(clazz);
    }

    @PutMapping
    public Result update(@RequestBody Clazz clazz){
        clazzService.update(clazz);
        return Result.success();
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids) {
        clazzService.delete(ids);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list(){
        List<Clazz> clazzList=clazzService.findAll();
        return Result.success(clazzList);
    }
}
