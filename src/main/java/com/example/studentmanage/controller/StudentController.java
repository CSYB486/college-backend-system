package com.example.studentmanage.controller;

import com.example.studentmanage.common.PageResult;
import com.example.studentmanage.common.Result;
import com.example.studentmanage.dto.StudentDTO;
import com.example.studentmanage.dto.StudentQuery;
import com.example.studentmanage.entity.Student;
import com.example.studentmanage.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "学生管理")
@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @Operation(summary = "分页查询学生，支持关键字搜索和班级筛选")
    @GetMapping
    public Result<PageResult<Student>> page(@ParameterObject StudentQuery query) {
        return Result.success(studentService.page(query));
    }

    @Operation(summary = "查询学生详情")
    @GetMapping("/{id}")
    public Result<Student> detail(@PathVariable Long id) {
        return Result.success(studentService.getById(id));
    }

    @Operation(summary = "新增学生")
    @PostMapping
    public Result<Void> add(@RequestBody @Valid StudentDTO dto) {
        studentService.add(dto);
        return Result.success();
    }

    @Operation(summary = "修改学生")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody @Valid StudentDTO dto) {
        studentService.update(id, dto);
        return Result.success();
    }

    @Operation(summary = "删除学生")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        studentService.delete(id);
        return Result.success();
    }
}
