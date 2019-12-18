package com.yaoqun.classroom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaoqun.classroom.common.ResultCode;
import com.yaoqun.classroom.common.ResultException;
import com.yaoqun.classroom.entity.CourseArrange;
import com.yaoqun.classroom.entity.CourseArrangeDTO;
import com.yaoqun.classroom.mapper.CourseArrangeMapper;
import com.yaoqun.classroom.service.ICourseArrangeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;


@Slf4j
@Service
@Transactional
public class CourseArrangeServiceImpl extends ServiceImpl<CourseArrangeMapper, CourseArrange> implements ICourseArrangeService {

    @Autowired
    private CourseArrangeMapper arrangeMapper;

    @Override
    public boolean checkExistCource(String id) {
        QueryWrapper<CourseArrange> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(CourseArrange::getRoomId, id)
                .eq(CourseArrange::getStatus, "0");
        return count(wrapper) != 0;


    }

    @Override
    public Object saveCourseArrange(CourseArrange course, String uId) {
        String uuid = IdWorker.get32UUID();
        String roomId = course.getRoomId();
        String courseName = course.getCourseName();
        String type = course.getType();
        LocalDate date = course.getDate();
        LocalTime startTime = course.getStartTime();
        LocalTime endTime = course.getEndTime();
        if (StringUtils.isEmpty(uId)) {
            throw new ResultException(ResultCode.PARAMER_EXCEPTION, "用户为空");
        }
        if (StringUtils.isEmpty(roomId)) {
            throw new ResultException(ResultCode.PARAMER_EXCEPTION, "教室未选择");
        }
        if (StringUtils.isEmpty(courseName)) {
            throw new ResultException(ResultCode.PARAMER_EXCEPTION, "课程或预约名称为空");
        }
        if (StringUtils.isEmpty(type)) {
            throw new ResultException(ResultCode.PARAMER_EXCEPTION, "课程时间类型为空");
        }
        if (null == date && "8".equals(type)) {
            throw new ResultException(ResultCode.PARAMER_EXCEPTION, "临时预约需要选择日期");
        }
        if (null == startTime) {
            throw new ResultException(ResultCode.PARAMER_EXCEPTION, "开始时间未选择");
        }
        if (null == endTime) {
            throw new ResultException(ResultCode.PARAMER_EXCEPTION, "结束时间未选择");
        }
        checkTime(roomId, type, date, startTime, endTime);
        //检测无冲突准备保存
        course.setId(uuid);
        return save(course);



    }

    @Override
    public Object cancleCourseArrange(CourseArrange course) {
        String id = course.getId();
        if (StringUtils.isEmpty(id)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"课程id为空");
        }
        CourseArrange one = getById(id);
        if (null==one){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"课程不存在");
        }
        String status = one.getStatus();
        if ("2".equals(status)) {
         one.setStatus("1");
        }else {
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"非申请状态的课程无法取消");
        }
        return updateById(one);
    }

    @Override
    public Object applyCourseArrange(CourseArrange course) {
        //审批
        String id = course.getId();
        String status = course.getStatus();
        if (!"0".equals(status) || !"3".equals(status) || "5".equals(status)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"审批状态非法");
        }
        CourseArrange one = getById(id);
        if (null==one){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"课程不存在");
        }
        String loadStatus = one.getStatus();
        if ("2".equals(loadStatus)){
            one.setStatus(status);
        }

        return updateById(one);
    }

    @Override
    public Object returnCourseArrange(CourseArrange course) {
        //归还
        String id = course.getId();
        CourseArrange one = getById(id);
        if (null==one){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"课程不存在");
        }
        one.setStatus("4");

        return updateById(one);
    }

    @Override
    public Object listCourses(int page, int row, CourseArrange course, String uId) {
        Page<CourseArrangeDTO> page1 = new Page<>(page, row);
        return  arrangeMapper.listCourses(page1,course);


    }


    private void checkTime(String roomId, String type, LocalDate date, LocalTime startTime, LocalTime endTime) {
        QueryWrapper<CourseArrange> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<CourseArrange> lambda = wrapper.lambda();
        QueryWrapper<CourseArrange> wrapper2 = new QueryWrapper<>();
        LambdaQueryWrapper<CourseArrange> lambda2 = wrapper2.lambda();
        //获取预约时间的星期几
        String dayOfWeek;
        //临时预约
        if ("8".equals(type)) {
            //获取预约时间的星期几取时间上限
            dayOfWeek = date.getDayOfWeek().getValue() + "";
            lambda.and(i -> i.isNull(CourseArrange::getDate).or().eq(CourseArrange::getDate, date));
            lambda2.and(i -> i.isNull(CourseArrange::getDate).or().eq(CourseArrange::getDate, date));
        } else {
            dayOfWeek = type;
        }
        lambda.in(CourseArrange::getType, "0", dayOfWeek)
                .le(CourseArrange::getEndTime, startTime)
                .orderByDesc(CourseArrange::getEndTime)
                .last("limit 1");

        lambda2.in(CourseArrange::getType, "0", dayOfWeek)
                .le(CourseArrange::getStartTime, endTime)
                .orderByDesc(CourseArrange::getStartTime)
                .last("limit 1");

        CourseArrange startCourse = getOne(wrapper);
        CourseArrange endCourse = getOne(wrapper2);
        //比较空档期
        if (null != startCourse && null != endCourse) {
            //比较中间的差值空间
            LocalTime endTime1 = startCourse.getEndTime();
            LocalTime startTime1 = endCourse.getStartTime();
            boolean after = endTime.isAfter(startTime1);
            boolean before = endTime1.isAfter(startTime);
            if (after || before) {
                throw new ResultException(ResultCode.PARAMER_EXCEPTION, "课程时间存在冲突，可预约的课程时间为" + endTime1 + "--" + startTime1);
            }
        } else if (startCourse == null) {
            LocalTime startTime1 = endCourse.getStartTime();
            if (endTime.isAfter(startTime1)) {
                throw new ResultException(ResultCode.PARAMER_EXCEPTION, "课程时间存在冲突，下一课程的开始时间为" + startTime1);
            }
        } else {
            LocalTime endTime1 = startCourse.getEndTime();
            if (endTime1.isAfter(startTime)) {
                throw new ResultException(ResultCode.PARAMER_EXCEPTION, "课程时间存在冲突，上一课程的结束时间为" + endTime1);
            }
        }


    }
}
