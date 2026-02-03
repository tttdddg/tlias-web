package org.example.pojo;

import lombok.Data;

@Data
public class StudentQueryParam {
    private Integer page=1;
    private Integer pageSize=10;
    private String name;
    private Integer clazzId;
    private Integer degree;
}
