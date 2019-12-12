package com.yaoqun.classroom.controller;


import com.yaoqun.classroom.common.Result;
import com.yaoqun.classroom.common.ResultUtil;
import com.yaoqun.classroom.entity.BuildingRoom;
import com.yaoqun.classroom.entity.BuildingType;
import com.yaoqun.classroom.service.IBuildingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;


@Controller
@RequestMapping("/buildingRoom")
public class BuildingRoomController {

    @Autowired
    private IBuildingRoomService roomService;

    @PostMapping("/save")
    public Result save(@RequestBody BuildingRoom room) {
        Object object = roomService.saveBuildingRoom(room);
        return ResultUtil.Success("保存成功", object);
    }

    @PostMapping("/update")
    public Result update(@RequestBody BuildingRoom room) {
        Object object = roomService.updateBuildingRoom(room);
        return ResultUtil.Success("修改成功", object);
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody BuildingRoom room) {
        Object object = roomService.deleteBuildingRoom(room);
        return ResultUtil.Success("删除成功", object);
    }

}
