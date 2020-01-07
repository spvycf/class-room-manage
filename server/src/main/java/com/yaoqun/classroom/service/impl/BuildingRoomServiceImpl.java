package com.yaoqun.classroom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaoqun.classroom.common.ResultCode;
import com.yaoqun.classroom.common.ResultException;
import com.yaoqun.classroom.entity.BuildingRoom;
import com.yaoqun.classroom.entity.BuildingType;
import com.yaoqun.classroom.mapper.BuildingRoomMapper;
import com.yaoqun.classroom.service.IBuildingRoomService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaoqun.classroom.service.IBuildingTypeService;
import com.yaoqun.classroom.service.ICourseArrangeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
@Transactional
public class BuildingRoomServiceImpl extends ServiceImpl<BuildingRoomMapper, BuildingRoom> implements IBuildingRoomService {

    @Autowired
    private ICourseArrangeService arrangeService;

    @Autowired
    private IBuildingTypeService buildingTypeService;

    @Override
    public boolean checkRoomIsClear(String buildingNo) {
        QueryWrapper<BuildingRoom> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BuildingRoom::getBuildingId,buildingNo);
        int count = count(wrapper);
        return count==0;


    }

    @Override
    public Object saveBuildingRoom(BuildingRoom room) {
        String buildingId = room.getBuildingId();
        String roomNO = room.getRoomNO();
        Integer roomSpace = room.getRoomSpace();
        String hasMedia = room.getHasMedia();
        if (StringUtils.isEmpty(buildingId)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"教学楼未选择");
        }
        if (StringUtils.isEmpty(roomNO)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"教学编号为空");
        }
        if (null==roomSpace){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"教室容量未输入");
        }
        if (StringUtils.isEmpty(hasMedia)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"未选择是否有多媒体");
        }
        String id = IdWorker.get32UUID();
        checkRoomIsExist(buildingId,roomNO, id);
        room.setId(id);
        room.setStatus("0");
        return save(room);


    }

    @Override
    public Object updateBuildingRoom(BuildingRoom room) {
        String id = room.getId();
        if (StringUtils.isEmpty(id)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"教室id为空");
        }
        String roomNO = room.getRoomNO();
        Integer roomSpace = room.getRoomSpace();
        String hasMedia = room.getHasMedia();

        if (StringUtils.isEmpty(roomNO)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"教学编号为空");
        }
        if (null==roomSpace){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"教室容量未输入");
        }
        if (StringUtils.isEmpty(hasMedia)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"未选择是否有多媒体");
        }
        BuildingRoom one = getById(id);

        checkRoomIsExist(one.getBuildingNo(),roomNO,id);
        return updateById(room);


    }

    @Override
    public Object deleteBuildingRoom(BuildingRoom room) {
        String id = room.getId();
        boolean exist = arrangeService.checkExistCource(id);
        if (exist){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"请先删除此教室下所有安排的课程");
        }
        return removeById(room);

    }

    @Override
    public Object listRooms(int page, int row, BuildingRoom room) {
        Page<BuildingRoom> page1 = new Page<>(page, row);
        QueryWrapper<BuildingRoom> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<BuildingRoom> lambda = wrapper.lambda();
        if (StringUtils.isNotEmpty(room.getBuildingId())){
            lambda.eq(BuildingRoom::getBuildingId,room.getBuildingId());
        }
        if (StringUtils.isNotEmpty(room.getRoomNO())){
            lambda.like(BuildingRoom::getRoomNO,room.getRoomNO());
        }
        if (StringUtils.isNotEmpty(room.getHasMedia())){
            lambda.eq(BuildingRoom::getHasMedia,room.getHasMedia());
        }
        Map<String, String> map = new HashMap<>();
        IPage<BuildingRoom> data = page(page1, wrapper);
        List<BuildingRoom> records = data.getRecords();
        for (BuildingRoom record : records) {
            String buildingId = record.getBuildingId();
            String bNo = map.get(buildingId);
            if (StringUtils.isNotEmpty(bNo)){
                record.setBuildingNo(bNo);
                continue;
            }else {
                BuildingType type = buildingTypeService.getById(buildingId);
                map.put(buildingId,type.getBuildingNo());
                record.setBuildingNo(type.getBuildingNo());
            }
        }

        return data;

    }

    @Override
    public Object getBuildingRoom(BuildingRoom room) {
        String id = room.getId();
        if (StringUtils.isEmpty(id)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"教室id为空");
        }
        return getById(id);

    }

    private void checkRoomIsExist(String buildingNo, String roomNO, String id) {
        QueryWrapper<BuildingRoom> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BuildingRoom::getBuildingId, buildingNo)
                .eq(BuildingRoom::getRoomNO, roomNO);
        BuildingRoom one = getOne(wrapper);
        if (null != one && !id.equals(one.getId())) {
            throw new ResultException(ResultCode.PARAMER_EXCEPTION, "此教学楼教室编号已经存在");
        }
    }
}
