package com.yaoqun.classroom.controller;


import com.yaoqun.classroom.common.Constatnt;
import com.yaoqun.classroom.common.Result;
import com.yaoqun.classroom.common.ResultUtil;
import com.yaoqun.classroom.entity.BuildingRoom;
import com.yaoqun.classroom.entity.CourseArrange;
import com.yaoqun.classroom.service.ICourseArrangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;


@Controller
@RequestMapping("/classroom/course-arrange")
public class CourseArrangeController {

    @Autowired
    private ICourseArrangeService arrangeService;

    @PostMapping("/save")
    public Result save(@RequestHeader(Constatnt.uId)String uId, @RequestBody CourseArrange course) {
        Object object = arrangeService.saveCourseArrange(course,uId);
        return ResultUtil.Success("保存成功", object);
    }

    @PostMapping("/cancle")
    public Result cancle(@RequestBody CourseArrange course) {
        Object object = arrangeService.cancleCourseArrange(course);
        return ResultUtil.Success("取消成功", object);
    }

    @PostMapping("/apply")
    public Result apply(@RequestBody CourseArrange course) {
        Object object = arrangeService.applyCourseArrange(course);
        return ResultUtil.Success("取消成功", object);
    }

    @PostMapping("/return")
    public Result returnCourse(@RequestBody CourseArrange course) {
        Object object = arrangeService.returnCourseArrange(course);
        return ResultUtil.Success("归还成功", object);
    }


    @PostMapping("/list/{page}/{row}")
    public Result list(@RequestHeader(Constatnt.uId)String uId,@PathVariable("page")int page, @PathVariable("row")int row, @RequestBody CourseArrange  course) {
        Object object = arrangeService.listCourses(page,row,course,uId);
        return ResultUtil.Success("查询成功", object);
    }
}
