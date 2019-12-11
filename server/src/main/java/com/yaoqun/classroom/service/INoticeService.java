package com.yaoqun.classroom.service;

import com.yaoqun.classroom.entity.Notice;
import com.baomidou.mybatisplus.extension.service.IService;


public interface INoticeService extends IService<Notice> {

    Object saveNotice(Notice notice);

    Object updateNotice(Notice notice);

    Object deleteNotice(Notice notice);

}
