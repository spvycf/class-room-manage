package com.yaoqun.classroom.controller;


import com.yaoqun.classroom.common.Result;
import com.yaoqun.classroom.common.ResultUtil;
import com.yaoqun.classroom.entity.BuildingRoom;
import com.yaoqun.classroom.entity.CourseArrange;
import com.yaoqun.classroom.service.ICourseArrangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;


@Controller
@RequestMapping("/classroom/course-arrange")
public class CourseArrangeController {

    @Autowired
    private ICourseArrangeService arrangeService;

    @PostMapping("/save")
    public Result save(@RequestBody CourseArrange course) {
        Object object = arrangeService.saveCourseArrange(course);
        return ResultUtil.Success("保存成功", object);
    }

}
