package com.yaoqun.classroom.service;

import com.yaoqun.classroom.entity.BuildingRoom;
import com.baomidou.mybatisplus.extension.service.IService;


public interface IBuildingRoomService extends IService<BuildingRoom> {

    boolean checkRoomIsClear(String buildingNo);

    Object saveBuildingRoom(BuildingRoom room);

    Object updateBuildingRoom(BuildingRoom room);

    Object deleteBuildingRoom(BuildingRoom room);
}
