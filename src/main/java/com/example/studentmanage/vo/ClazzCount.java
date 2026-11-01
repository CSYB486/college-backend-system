package com.example.studentmanage.vo;

import lombok.Data;

/**
 * 单个班级的学生人数
 */
@Data
public class ClazzCount {

    private String clazzName;

    private Long num;
}
