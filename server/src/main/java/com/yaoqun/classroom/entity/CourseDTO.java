package com.yaoqun.classroom.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * @author doger.wang
 * @date 2020/2/15 10:31
 */
@Data
public class CourseDTO implements Serializable {


    private static final long serialVersionUID = 7846033782292392711L;
    private LocalDate date;
    private List<CourseArrangeDTO> courses;


}
