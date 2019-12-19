package com.yaoqun.classroom.controller;


import com.yaoqun.classroom.common.Result;
import com.yaoqun.classroom.common.ResultUtil;
import com.yaoqun.classroom.entity.BuildingType;
import com.yaoqun.classroom.service.IBuildingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/buildingType")
public class BuildingTypeController {

    @Autowired
    private IBuildingTypeService buildingTypeService;


    @PostMapping("/save")
    public Result save(@RequestBody BuildingType buildingType) {
        Object object = buildingTypeService.saveBuildingType(buildingType);
        return ResultUtil.Success("保存成功", object);
    }

    @PostMapping("/update")
    public Result update(@RequestBody BuildingType buildingType) {
        Object object = buildingTypeService.updateBuildingType(buildingType);
        return ResultUtil.Success("修改成功", object);
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody BuildingType buildingType) {
        Object object = buildingTypeService.deleteBuildingType(buildingType);
        return ResultUtil.Success("删除成功", object);
    }

    @PostMapping("/list/{page}/{row}")
    public Result list(@PathVariable("page")int page, @PathVariable("row")int row, @RequestBody BuildingType buildingType) {
        Object object = buildingTypeService.listBuildingTypes(page,row,buildingType);
        return ResultUtil.Success("查询成功", object);
    }
}
