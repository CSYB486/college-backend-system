package com.example.studentmanage.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.studentmanage.common.BusinessException;
import com.example.studentmanage.common.PageResult;
import com.example.studentmanage.dto.StudentDTO;
import com.example.studentmanage.dto.StudentQuery;
import com.example.studentmanage.entity.Clazz;
import com.example.studentmanage.entity.Student;
import com.example.studentmanage.mapper.ClazzMapper;
import com.example.studentmanage.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 学生相关业务
 */
@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentMapper studentMapper;
    private final ClazzMapper clazzMapper;

    /**
     * 分页查询，支持按姓名/学号搜索、按班级筛选
     */
    public PageResult<Student> page(StudentQuery query) {
        long pageNum = query.getPageNum() == null || query.getPageNum() < 1 ? 1 : query.getPageNum();
        int pageSize = query.getPageSize() == null || query.getPageSize() < 1 || query.getPageSize() > 100
                ? 10 : query.getPageSize();

        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(query.getKeyword())) {
            // 姓名或学号包含关键字
            wrapper.and(w -> w.like(Student::getName, query.getKeyword())
                    .or()
                    .like(Student::getStudentNo, query.getKeyword()));
        }
        if (query.getClazzId() != null) {
            wrapper.eq(Student::getClazzId, query.getClazzId());
        }
        wrapper.orderByDesc(Student::getId);

        Page<Student> page = studentMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        fillClazzName(page.getRecords());
        return PageResult.of(page);
    }

    /**
     * 根据 id 查询
     */
    public Student getById(Long id) {
        Student student = studentMapper.selectById(id);
        if (student == null) {
            throw new BusinessException("学生不存在");
        }
        fillClazzName(List.of(student));
        return student;
    }

    /**
     * 新增学生
     */
    public void add(StudentDTO dto) {
        checkStudentNo(dto.getStudentNo(), null);
        checkClazz(dto.getClazzId());

        Student student = new Student();
        BeanUtils.copyProperties(dto, student);
        student.setCreateTime(LocalDateTime.now());
        studentMapper.insert(student);
    }

    /**
     * 修改学生
     */
    public void update(Long id, StudentDTO dto) {
        if (studentMapper.selectById(id) == null) {
            throw new BusinessException("学生不存在");
        }
        checkStudentNo(dto.getStudentNo(), id);
        checkClazz(dto.getClazzId());

        Student student = new Student();
        BeanUtils.copyProperties(dto, student);
        student.setId(id);
        studentMapper.updateById(student);
    }

    /**
     * 删除学生
     */
    public void delete(Long id) {
        if (studentMapper.deleteById(id) == 0) {
            throw new BusinessException("学生不存在");
        }
    }

    /**
     * 给查询结果补充班级名称：先收集班级 id 一次查出，避免每条记录都查一次数据库
     */
    private void fillClazzName(List<Student> students) {
        if (students == null || students.isEmpty()) {
            return;
        }
        Set<Long> clazzIds = students.stream()
                .map(Student::getClazzId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (clazzIds.isEmpty()) {
            return;
        }
        Map<Long, String> nameMap = clazzMapper.selectByIds(clazzIds).stream()
                .collect(Collectors.toMap(Clazz::getId, Clazz::getName));
        students.forEach(s -> s.setClazzName(nameMap.get(s.getClazzId())));
    }

    /**
     * 校验学号唯一，excludeId 用于修改时排除自己
     */
    private void checkStudentNo(String studentNo, Long excludeId) {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<Student>()
                .eq(Student::getStudentNo, studentNo);
        if (excludeId != null) {
            wrapper.ne(Student::getId, excludeId);
        }
        if (studentMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("学号已存在");
        }
    }

    private void checkClazz(Long clazzId) {
        if (clazzMapper.selectById(clazzId) == null) {
            throw new BusinessException("班级不存在");
        }
    }
}
