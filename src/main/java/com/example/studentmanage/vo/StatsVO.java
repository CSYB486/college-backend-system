package com.example.studentmanage.vo;

import lombok.Data;

import java.util.List;

/**
 * 首页统计数据
 */
@Data
public class StatsVO {

    /** 学生总数 */
    private Long total;

    /** 男生数 */
    private Long boyCount;

    /** 女生数 */
    private Long girlCount;

    /** 各班人数 */
    private List<ClazzCount> clazzStats;
}
