package com.yaoqun.classroom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaoqun.classroom.common.ResultCode;
import com.yaoqun.classroom.common.ResultException;
import com.yaoqun.classroom.entity.CourseArrange;
import com.yaoqun.classroom.entity.CourseArrangeDTO;
import com.yaoqun.classroom.entity.CourseDTO;
import com.yaoqun.classroom.mapper.CourseArrangeMapper;
import com.yaoqun.classroom.service.ICourseArrangeService;
import com.yaoqun.classroom.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
public class CourseArrangeServiceImpl extends ServiceImpl<CourseArrangeMapper, CourseArrange> implements ICourseArrangeService {

    @Autowired
    private CourseArrangeMapper arrangeMapper;
    @Autowired
    private IUserService userService;

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
        //status判定
        String status= userService.checkUserTpye(uId);

        checkTime(roomId, type, date, startTime, endTime);
        //检测无冲突准备保存
        course.setId(uuid);
        course.setUserId(uId);
        course.setStatus(status);


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
        if (!"0".equals(status) && !"3".equals(status) &&!"5".equals(status)){
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
        one.setStatus("5");

        return updateById(one);
    }

    @Override
    public Object listCourses( CourseArrange course, String uId) {

        //时间查询一周为佳
        LocalDate date = course.getDate();
        if (null==date) {
            //当前时间的一周
            date = LocalDate.now();
        }
        LocalDate monday = date.with(DayOfWeek.MONDAY);
        LocalDate sunday = date.with(DayOfWeek.SUNDAY);
        course.setStartDate(monday);
        course.setEndDate(sunday);
        course.setStatus("0");

        List<CourseArrangeDTO> courses = arrangeMapper.listCourses(course);
        Map<String, List<CourseArrangeDTO>> collect = courses.stream().collect(Collectors.groupingBy(CourseArrangeDTO::getType));
        List<CourseArrangeDTO> everyDayCourse = collect.get("0");
        List<CourseArrangeDTO> tempCourse = collect.get("8");
        Map<LocalDate, List<CourseArrangeDTO>> tempDateCourses = null;
        if (null!=tempCourse){
            tempDateCourses  = tempCourse.stream().collect(Collectors.groupingBy(CourseArrangeDTO::getDate));
        }


        //准备组装
        List<CourseDTO> result = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            CourseDTO dto = new CourseDTO();
            LocalDate day =monday.plusDays(i);
            List<CourseArrangeDTO> list = new ArrayList<>();
            //每天的
            if (null!=everyDayCourse){
                list.addAll(everyDayCourse);
            }
            //自己周的
            List<CourseArrangeDTO> self = collect.get(i + 1+"");
            if (null!=self){
                list.addAll(self);
            }
            //临时的
            if (null!=tempDateCourses){
                List<CourseArrangeDTO> temp = tempDateCourses.get(day);
                if (null!=temp){
                    list.addAll(temp);
                }
            }
            //排个序
            List<CourseArrangeDTO> collect1 = list.stream().sorted(Comparator.comparing(CourseArrangeDTO::getStartTime)).collect(Collectors.toList());
            dto.setDate(day);
            dto.setCourses(collect1);
            result.add(dto);


        }

        return result;




    }

    @Override
    public Object processCourses(CourseArrange course, String uId, int page, int row) {
        Page<CourseArrange> page1 = new Page<>(page, row);
        String status = course.getStatus();
        String roomId = course.getRoomId();
        String userId = course.getUserId();
        QueryWrapper<CourseArrange> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<CourseArrange> lambda = wrapper.lambda();
        if (StringUtils.isNotEmpty(status)){
            lambda.eq(CourseArrange::getStatus,status);
        }
        if (StringUtils.isNotEmpty(roomId)){
            lambda.eq(CourseArrange::getRoomId,roomId);
        }
        if (StringUtils.isNotEmpty(userId)){
            lambda.eq(CourseArrange::getUserId,userId);
        }
        lambda.orderByDesc(CourseArrange::getCreateTime);

        Page<CourseArrangeDTO> date= arrangeMapper.processCourses(page1,course);

        return date;




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
                .eq(CourseArrange::getRoomId,roomId)
                .orderByDesc(CourseArrange::getEndTime)
                .last("limit 1");

        lambda2.in(CourseArrange::getType, "0", dayOfWeek)
                .le(CourseArrange::getStartTime, endTime)
                .eq(CourseArrange::getRoomId,roomId)
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
        } else if (startCourse == null && null!=endCourse) {
            LocalTime startTime1 = endCourse.getStartTime();
            if (endTime.isAfter(startTime1)) {
                throw new ResultException(ResultCode.PARAMER_EXCEPTION, "课程时间存在冲突，下一课程的开始时间为" + startTime1);
            }
        } else if (startCourse != null && null==endCourse) {
            LocalTime endTime1 = startCourse.getEndTime();
            if (endTime1.isAfter(startTime)) {
                throw new ResultException(ResultCode.PARAMER_EXCEPTION, "课程时间存在冲突，上一课程的结束时间为" + endTime1);
            }
        }


    }
}
