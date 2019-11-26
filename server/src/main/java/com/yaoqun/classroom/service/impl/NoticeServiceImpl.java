package com.yaoqun.classroom.service.impl;

import com.yaoqun.classroom.entity.Notice;
import com.yaoqun.classroom.mapper.NoticeMapper;
import com.yaoqun.classroom.service.INoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

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
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

}
