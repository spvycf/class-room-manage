package com.yaoqun.classroom.controller;


import com.yaoqun.classroom.common.Result;
import com.yaoqun.classroom.common.ResultUtil;
import com.yaoqun.classroom.entity.Notice;
import com.yaoqun.classroom.entity.User;
import com.yaoqun.classroom.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;


@Controller
@RequestMapping("/classroom/notice")
public class NoticeController {

    @Autowired
    private INoticeService noticeService;

    @PostMapping("/save")
    public Result save(@RequestBody Notice notice) {
        Object object = noticeService.saveNotice(notice);
        return ResultUtil.Success("发布成功", object);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Notice notice) {
        Object object = noticeService.updateNotice(notice);
        return ResultUtil.Success("修改成功", object);
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody Notice notice) {
        Object object = noticeService.deleteNotice(notice);
        return ResultUtil.Success("删除成功", object);
    }

    @PostMapping("/list/{page}/{row}")
    public Result list(@PathVariable("page")int page, @PathVariable("row")int row, @RequestBody Notice notice) {
        Object object = noticeService.listNotices(page,row,notice);
        return ResultUtil.Success("查询成功", object);
    }

}
