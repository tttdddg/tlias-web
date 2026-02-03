package org.example.pojo;

import lombok.Data;

@Data
public class ViolationRequest {
    // 目标学生的ID（必须）
    private Integer  studentId;
    // 前端输入的违纪扣分数值（必须为正整数）
    private Integer deductScore;
}