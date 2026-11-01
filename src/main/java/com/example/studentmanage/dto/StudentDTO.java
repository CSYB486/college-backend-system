package com.example.studentmanage.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 新增/修改学生时的请求参数
 */
@Data
public class StudentDTO {

    @NotBlank(message = "学号不能为空")
    @Size(max = 20, message = "学号最多 20 个字符")
    private String studentNo;

    @NotBlank(message = "姓名不能为空")
    @Size(max = 20, message = "姓名最多 20 个字符")
    private String name;

    @NotNull(message = "性别不能为空")
    @Min(value = 1, message = "性别只能是 1(男) 或 2(女)")
    @Max(value = 2, message = "性别只能是 1(男) 或 2(女)")
    private Integer gender;

    @Min(value = 1, message = "年龄不合法")
    @Max(value = 150, message = "年龄不合法")
    private Integer age;

    @Size(max = 20, message = "手机号最多 20 个字符")
    private String phone;

    @NotNull(message = "班级不能为空")
    private Long clazzId;
}
