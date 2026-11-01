package com.example.studentmanage.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.studentmanage.entity.Student;
import com.example.studentmanage.mapper.StudentMapper;
import com.example.studentmanage.vo.StatsVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 首页统计业务
 */
@Service
@RequiredArgsConstructor
public class StatsService {

    private final StudentMapper studentMapper;

    public StatsVO stats() {
        StatsVO vo = new StatsVO();
        vo.setTotal(studentMapper.selectCount(null));
        vo.setBoyCount(studentMapper.selectCount(
                new LambdaQueryWrapper<Student>().eq(Student::getGender, 1)));
        vo.setGirlCount(studentMapper.selectCount(
                new LambdaQueryWrapper<Student>().eq(Student::getGender, 2)));
        vo.setClazzStats(studentMapper.countByClazz());
        return vo;
    }
}
