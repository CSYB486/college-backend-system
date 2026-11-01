package com.example.studentmanage.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.studentmanage.common.BusinessException;
import com.example.studentmanage.dto.ClazzDTO;
import com.example.studentmanage.entity.Clazz;
import com.example.studentmanage.entity.Student;
import com.example.studentmanage.mapper.ClazzMapper;
import com.example.studentmanage.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 班级相关业务
 */
@Service
@RequiredArgsConstructor
public class ClazzService {

    private final ClazzMapper clazzMapper;
    private final StudentMapper studentMapper;

    /**
     * 查询全部班级（用于下拉框）
     */
    public List<Clazz> list() {
        return clazzMapper.selectList(new LambdaQueryWrapper<Clazz>().orderByAsc(Clazz::getId));
    }

    public void add(ClazzDTO dto) {
        checkName(dto.getName(), null);
        Clazz clazz = new Clazz();
        BeanUtils.copyProperties(dto, clazz);
        clazz.setCreateTime(LocalDateTime.now());
        clazzMapper.insert(clazz);
    }

    public void update(Long id, ClazzDTO dto) {
        if (clazzMapper.selectById(id) == null) {
            throw new BusinessException("班级不存在");
        }
        checkName(dto.getName(), id);
        Clazz clazz = new Clazz();
        BeanUtils.copyProperties(dto, clazz);
        clazz.setId(id);
        clazzMapper.updateById(clazz);
    }

    /**
     * 删除班级：班级下还有学生时不允许删除
     */
    public void delete(Long id) {
        if (clazzMapper.selectById(id) == null) {
            throw new BusinessException("班级不存在");
        }
        Long count = studentMapper.selectCount(
                new LambdaQueryWrapper<Student>().eq(Student::getClazzId, id));
        if (count > 0) {
            throw new BusinessException("该班级下还有学生，不能删除");
        }
        clazzMapper.deleteById(id);
    }

    private void checkName(String name, Long excludeId) {
        LambdaQueryWrapper<Clazz> wrapper = new LambdaQueryWrapper<Clazz>().eq(Clazz::getName, name);
        if (excludeId != null) {
            wrapper.ne(Clazz::getId, excludeId);
        }
        if (clazzMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("班级名称已存在");
        }
    }
}
