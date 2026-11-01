package com.example.studentmanage.controller;

import com.example.studentmanage.common.Result;
import com.example.studentmanage.service.StatsService;
import com.example.studentmanage.vo.StatsVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "数据统计")
@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @Operation(summary = "首页统计数据：总人数、男女比例、各班级人数")
    @GetMapping
    public Result<StatsVO> stats() {
        return Result.success(statsService.stats());
    }
}
