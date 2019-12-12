package com.yaoqun.classroom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yaoqun.classroom.common.ResultCode;
import com.yaoqun.classroom.common.ResultException;
import com.yaoqun.classroom.entity.CourseArrange;
import com.yaoqun.classroom.mapper.CourseArrangeMapper;
import com.yaoqun.classroom.service.ICourseArrangeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dogerWang
 * @since 2019-11-26
 */
@Slf4j
@Service
@Transactional
public class CourseArrangeServiceImpl extends ServiceImpl<CourseArrangeMapper, CourseArrange> implements ICourseArrangeService {

    @Override
    public boolean checkExistCource(String id) {
        QueryWrapper<CourseArrange> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(CourseArrange::getRoomId,id)
                .eq(CourseArrange::getStatus,"0");
        return count(wrapper)!=0;


    }

    @Override
    public Object saveCourseArrange(CourseArrange course) {
        String uuid = IdWorker.get32UUID();
        String roomId = course.getRoomId();
        String courseName = course.getCourseName();
        String type = course.getType();
        LocalDate date = course.getDate();
        LocalTime startTime = course.getStartTime();
        LocalTime endTime = course.getEndTime();
        if (StringUtils.isEmpty(roomId)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"教室未选择");
        }
        if (StringUtils.isEmpty(courseName)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"课程或预约名称为空");
        }
        if (StringUtils.isEmpty(type)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"课程时间类型为空");
        }
        if (null==date && "8".equals(type)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"临时预约需要选择日期");
        }
        if (null==startTime){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"开始时间未选择");
        }
        if (null==endTime){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"结束时间未选择");
        }
        checkTime(roomId,type,date,startTime,endTime);
        

    }

    private void checkTime(String roomId, String type, LocalDate date, LocalTime startTime, LocalTime endTime) {
        //临时预约
        if ("8".equals(type)){
            QueryWrapper<CourseArrange> wrapper = new QueryWrapper<>();
            //获取预约时间的星期几
            String dayOfWeek = date.getDayOfWeek().getValue()+"";
            wrapper.lambda()
                    .in(CourseArrange::getType,"0",dayOfWeek)
                    .le(CourseArrange::getEndTime,startTime)
                    .or()

                    .orderByDesc(CourseArrange::getEndTime)
                    .last("limit 1");
            CourseArrange startCourse = getOne(wrapper);

            QueryWrapper<CourseArrange> wrapper2 = new QueryWrapper<>();
            //获取预约时间的星期几
            wrapper2.lambda()
                    .in(CourseArrange::getType,"0",dayOfWeek)
                    .le(CourseArrange::getStartTime,endTime)
                    .orderByDesc(CourseArrange::getStartTime)
                    .last("limit 1");
            CourseArrange endCourse = getOne(wrapper2);
            //比较空档期
            if (null==startCourse && null==endCourse){
                //没有任何课程，直接pass
            }else if (startCourse==null){
                LocalTime startTime1 = endCourse.getStartTime();
                if (endTime.isAfter(startTime1)){
                    throw new ResultException(ResultCode.PARAMER_EXCEPTION,"课程时间存在冲突，下一课程的开始时间为"+startTime1);
                }
            }else {
                LocalTime endTime1 = startCourse.getEndTime();
                if (endTime1.isAfter(startTime)){
                    throw new ResultException(ResultCode.PARAMER_EXCEPTION,"课程时间存在冲突，上一课程的结束时间为"+endTime1);
                }
            }
        }else {
            //每周每天类型的

        }



    }
}
