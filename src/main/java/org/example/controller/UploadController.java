package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Result;
import org.example.utils.AliyunOSSOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
public class UploadController {
    //本地存储方案（实际开发中不推荐）
//    @PostMapping("/upload") // 前端页面访问时写"localhost:8080/upload"
//    public Result upload(String name, Integer age, MultipartFile file) {
//        log.info("接收参数:{},{},{}", name, age, file);
//
//        try {
//            // 获取原始文件名
//            String ofn = file.getOriginalFilename();
//
//            // 提取文件扩展名
//            String extension = ofn.substring(ofn.lastIndexOf("."));
//
//            // 生成新的文件名
//            String nfn = UUID.randomUUID().toString() + extension;
//
//            // 保存路径
//            String pathName = "D:/images/";
//
//            // 确保目录存在
//            File dir = new File(pathName);
//            if (!dir.exists()) {
//                dir.mkdirs();
//            }
//
//            // 保存文件
//            file.transferTo(new File(pathName + nfn));
//
//            log.info("文件上传成功: {}", pathName + nfn);
//            return Result.success("上传成功");
//
//        } catch (IOException e) {
//            log.error("文件上传失败", e);
//            return Result.error("文件上传失败: " + e.getMessage());
//        } catch (Exception e) {
//            log.error("系统错误", e);
//            return Result.error("系统错误");
//        }
//    }

    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        log.info("文件上传：{}",file.getOriginalFilename());
        try {
            //将文件交给OSS存储管理
            String url = aliyunOSSOperator.upload(file.getBytes(), file.getOriginalFilename());
            log.info("文件上传OSS成功：{}", url);

            return Result.success(url);
        } catch (Exception e) {
            log.error("文件上传失败", e);
            return Result.error("上传失败");
        }
    }
}