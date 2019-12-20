package com.yaoqun.classroom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaoqun.classroom.common.ResultCode;
import com.yaoqun.classroom.common.ResultException;
import com.yaoqun.classroom.entity.Notice;
import com.yaoqun.classroom.mapper.NoticeMapper;
import com.yaoqun.classroom.service.INoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Slf4j
@Service
@Transactional
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

    @Override
    public Object saveNotice(Notice notice) {
        String title = notice.getTitle();
        String content = notice.getContent();
        if (StringUtils.isAnyEmpty(title,content)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"标题或内容为空");
        }
        notice.setId(IdWorker.get32UUID());
        notice.setStatus("0");
        notice.setCreateTime(LocalDateTime.now());
        return save(notice);


    }

    @Override
    public Object updateNotice(Notice notice) {
        String id = notice.getId();
        if (StringUtils.isEmpty(id)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"公告id为空");
        }
        String title = notice.getTitle();
        String content = notice.getContent();
        if (StringUtils.isAnyEmpty(title,content)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"标题或内容为空");
        }
        return updateById(notice);


    }

    @Override
    public Object deleteNotice(Notice notice) {
        String id = notice.getId();
        if (StringUtils.isEmpty(id)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"公告id为空");
        }
        return removeById(notice);
    }

    @Override
    public Object listNotices(int page, int row, Notice notice) {
        Page<Notice> pageInfo = new Page<>(page, row);
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper.lambda().orderByDesc(Notice::getCreateTime);
        return page(pageInfo,wrapper);



    }

    @Override
    public Object get(Notice notice) {
        String id = notice.getId();
        if (StringUtils.isEmpty(id)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"公告id为空");
        }
        return getById(notice);
    }
}
