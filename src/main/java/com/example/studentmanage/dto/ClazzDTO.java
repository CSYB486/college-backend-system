package com.example.studentmanage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 新增/修改班级时的请求参数
 */
@Data
public class ClazzDTO {

    @NotBlank(message = "班级名称不能为空")
    @Size(max = 50, message = "班级名称最多 50 个字符")
    private String name;

    @Size(max = 50, message = "专业最多 50 个字符")
    private String major;
}
