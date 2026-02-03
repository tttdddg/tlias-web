package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.anno.Log;
import org.example.pojo.Dept;
import org.example.pojo.Result;
import org.example.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/depts")
@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;
//    @RequestMapping(value = "/depts",method = RequestMethod.GET)
    @GetMapping
    public Result list(){
        System.out.println("查询全部门数据：");
        List<Dept> deptList=deptService.findAll();
        return Result.success(deptList);
    }

    @Log
    @DeleteMapping
//    public Result delete(HttpServletRequest request){
//       String idStr=request.getParameter("id");
//       int id=Integer.parseInt(idStr);
    public Result delete(@RequestParam(value = "id",required = true) Integer deptID){
       System.out.println("根据ID删除部门："+deptID);
       deptService.deleteById(deptID);
       return Result.success();
    }

    @Log
    @PostMapping
    public Result add(@RequestBody Dept dept){
        System.out.println("添加部门："+dept);
        deptService.add(dept);
        return Result.success();
    }

    @GetMapping("/{id}")
//    public Result getInfo(@PathVariable("id") Integer deptId){
    public Result getInfo(@PathVariable Integer id){
        System.out.println("查询部门ID："+id);
        Dept dept=deptService.getById(id);
        return Result.success(dept);
    }

    @Log
    @PutMapping
    public Result update(@RequestBody Dept dept){
        System.out.println("修改部门："+dept);
        deptService.update(dept);
        return Result.success();
    }
}
