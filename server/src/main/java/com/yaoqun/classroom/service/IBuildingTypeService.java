package com.yaoqun.classroom.service;

import com.yaoqun.classroom.entity.BuildingType;
import com.baomidou.mybatisplus.extension.service.IService;


public interface IBuildingTypeService extends IService<BuildingType> {

    Object saveBuildingType(BuildingType buildingType);

    Object updateBuildingType(BuildingType buildingType);

    Object deleteBuildingType(BuildingType buildingType);

    Object listBuildingTypes(int page, int row, BuildingType buildingType);

    Object getBuildingType(BuildingType buildingType);
}
