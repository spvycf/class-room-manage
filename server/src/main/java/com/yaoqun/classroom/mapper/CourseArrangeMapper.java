package com.yaoqun.classroom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaoqun.classroom.entity.CourseArrange;
import com.yaoqun.classroom.entity.CourseArrangeDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CourseArrangeMapper extends BaseMapper<CourseArrange> {

    List<CourseArrangeDTO> listCourses(@Param("course") CourseArrange course);

    Page<CourseArrangeDTO> processCourses(Page<CourseArrange> page1, CourseArrange course);
}
