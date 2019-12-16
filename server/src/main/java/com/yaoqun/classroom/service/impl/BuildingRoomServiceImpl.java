package com.yaoqun.classroom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaoqun.classroom.common.ResultCode;
import com.yaoqun.classroom.common.ResultException;
import com.yaoqun.classroom.entity.BuildingRoom;
import com.yaoqun.classroom.mapper.BuildingRoomMapper;
import com.yaoqun.classroom.service.IBuildingRoomService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaoqun.classroom.service.ICourseArrangeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BuildingRoomServiceImpl extends ServiceImpl<BuildingRoomMapper, BuildingRoom> implements IBuildingRoomService {

    @Autowired
    private ICourseArrangeService arrangeService;

    @Override
    public boolean checkRoomIsClear(String buildingNo) {
        QueryWrapper<BuildingRoom> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BuildingRoom::getBuildingId,buildingNo);
        int count = count(wrapper);
        return count==0;


    }

    @Override
    public Object saveBuildingRoom(BuildingRoom room) {
        String buildingNo = room.getBuildingId();
        String roomNO = room.getRoomNO();
        Integer roomSpace = room.getRoomSpace();
        String hasMedia = room.getHasMedia();
        if (StringUtils.isEmpty(buildingNo)){
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
        checkRoomIsExist(buildingNo,roomNO, id);
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
        String buildingNo = room.getBuildingId();
        String roomNO = room.getRoomNO();
        Integer roomSpace = room.getRoomSpace();
        String hasMedia = room.getHasMedia();
        if (StringUtils.isEmpty(buildingNo)){
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
        checkRoomIsExist(buildingNo,roomNO,id);
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
        return page(page1,wrapper);

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
