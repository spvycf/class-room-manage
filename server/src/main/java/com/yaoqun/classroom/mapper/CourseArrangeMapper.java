package com.yaoqun.classroom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaoqun.classroom.entity.CourseArrange;
import com.yaoqun.classroom.entity.CourseArrangeDTO;
import org.apache.ibatis.annotations.Param;


public interface CourseArrangeMapper extends BaseMapper<CourseArrange> {

    Page<CourseArrangeDTO> listCourses(Page<CourseArrangeDTO> page1, @Param("course") CourseArrange course);
}
