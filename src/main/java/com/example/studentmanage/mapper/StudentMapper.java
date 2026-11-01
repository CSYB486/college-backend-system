package com.example.studentmanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.studentmanage.entity.Student;
import com.example.studentmanage.vo.ClazzCount;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 学生 Mapper：基础 CRUD 来自 BaseMapper，统计 SQL 手写
 */
public interface StudentMapper extends BaseMapper<Student> {

    /**
     * 统计每个班级的学生人数（班级表左连接学生表，没有学生的班级也会显示 0）
     */
    @Select("SELECT c.name AS clazz_name, COUNT(s.id) AS num " +
            "FROM clazz c LEFT JOIN student s ON s.clazz_id = c.id " +
            "GROUP BY c.id, c.name " +
            "ORDER BY num DESC")
    List<ClazzCount> countByClazz();
}
