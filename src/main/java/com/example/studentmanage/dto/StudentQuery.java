package com.example.studentmanage.dto;

import lombok.Data;

/**
 * 学生分页查询参数（通过 URL 参数传递）
 */
@Data
public class StudentQuery {

    /** 页码，从 1 开始 */
    private Long pageNum = 1L;

    /** 每页条数 */
    private Integer pageSize = 10;

    /** 关键字：同时匹配姓名或学号 */
    private String keyword;

    /** 班级 id，为空表示查询全部班级 */
    private Long clazzId;
}
