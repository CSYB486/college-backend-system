package com.example.studentmanage.controller;

import com.example.studentmanage.common.Result;
import com.example.studentmanage.dto.ClazzDTO;
import com.example.studentmanage.entity.Clazz;
import com.example.studentmanage.service.ClazzService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "班级管理")
@RestController
@RequestMapping("/api/clazz")
@RequiredArgsConstructor
public class ClazzController {

    private final ClazzService clazzService;

    @Operation(summary = "查询全部班级")
    @GetMapping
    public Result<List<Clazz>> list() {
        return Result.success(clazzService.list());
    }

    @Operation(summary = "新增班级")
    @PostMapping
    public Result<Void> add(@RequestBody @Valid ClazzDTO dto) {
        clazzService.add(dto);
        return Result.success();
    }

    @Operation(summary = "修改班级")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody @Valid ClazzDTO dto) {
        clazzService.update(id, dto);
        return Result.success();
    }

    @Operation(summary = "删除班级（有学生时不允许删除）")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        clazzService.delete(id);
        return Result.success();
    }
}
