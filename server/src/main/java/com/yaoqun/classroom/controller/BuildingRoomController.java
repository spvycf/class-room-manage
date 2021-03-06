package com.yaoqun.classroom.controller;


import com.yaoqun.classroom.common.Result;
import com.yaoqun.classroom.common.ResultUtil;
import com.yaoqun.classroom.entity.BuildingRoom;
import com.yaoqun.classroom.service.IBuildingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
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

    @PostMapping("/get")
    public Result get(@RequestBody BuildingRoom room) {
        Object object = roomService.getBuildingRoom(room);
        return ResultUtil.Success("获取成功", object);
    }

    @PostMapping("/list/{page}/{row}")
    public Result list(@PathVariable("page")int page, @PathVariable("row")int row, @RequestBody BuildingRoom room) {
        Object object = roomService.listRooms(page,row,room);
        return ResultUtil.Success("查询成功", object);
    }
}
