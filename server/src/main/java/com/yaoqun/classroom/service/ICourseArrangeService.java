package com.yaoqun.classroom.service;

import com.yaoqun.classroom.entity.CourseArrange;
import com.baomidou.mybatisplus.extension.service.IService;


public interface ICourseArrangeService extends IService<CourseArrange> {

    boolean checkExistCource(String id);

    Object saveCourseArrange(CourseArrange course, String uId);

    Object cancleCourseArrange(CourseArrange course);

    Object applyCourseArrange(CourseArrange course);

    Object returnCourseArrange(CourseArrange course);

    Object listCourses(int page, int row, CourseArrange course, String uId);
}
