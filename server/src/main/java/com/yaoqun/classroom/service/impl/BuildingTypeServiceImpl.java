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
import com.yaoqun.classroom.mapper.BuildingTypeMapper;
import com.yaoqun.classroom.service.IBuildingRoomService;
import com.yaoqun.classroom.service.IBuildingTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaoqun.classroom.service.ICourseArrangeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
@Transactional
public class BuildingTypeServiceImpl extends ServiceImpl<BuildingTypeMapper, BuildingType> implements IBuildingTypeService {

    @Autowired
    private IBuildingRoomService roomService;

    @Autowired
    private ICourseArrangeService courseArrangeService;


    @Override
    public Object saveBuildingType(BuildingType buildingType) {
        String buildingNo = buildingType.getBuildingNo();
        String buildingName = buildingType.getBuildingName();
        if (StringUtils.isAnyEmpty(buildingName,buildingNo)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"教学楼名称或教学楼编号为空");
        }
        QueryWrapper<BuildingType> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(BuildingType::getBuildingNo,buildingNo);

        BuildingType one = getOne(wrapper);
        if (null!=one){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"已经存在相同教学楼名称");
        }
        buildingType.setStatus("0");
        buildingType.setId(IdWorker.get32UUID());
        buildingType.setCreateTime(LocalDateTime.now());
        return save(buildingType);


    }

    @Override
    public Object updateBuildingType(BuildingType buildingType) {
        String id = buildingType.getId();
        String buildingName = buildingType.getBuildingName();
        String buildingNo = buildingType.getBuildingNo();
        if (StringUtils.isEmpty(id)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"教学楼id不能为空");
        }
        if (StringUtils.isEmpty(buildingName)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"教学楼名称不能为空");
        }
        if (StringUtils.isEmpty(buildingNo)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"教学楼编号不能为空");
        }
        QueryWrapper<BuildingType> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(BuildingType::getBuildingNo,buildingNo);
        BuildingType one = getOne(wrapper);
        if (null!=one && !one.getId().equals(id)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"已经存在相同教学楼名称");
        }
        BuildingType type = new BuildingType();
        type.setId(id);
        type.setBuildingName(buildingName);
        type.setBuildingNo(buildingNo);
        return updateById(type);

    }

    @Override
    public Object deleteBuildingType(BuildingType buildingType) {
        String id = buildingType.getId();
        if (StringUtils.isEmpty(id)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"教学楼id不能为空");
        }
        BuildingType type = getById(id);
        String buildingNo = type.getBuildingNo();
        boolean isClear = roomService.checkRoomIsClear(buildingNo);
        if (!isClear){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"教学楼下还存在教室,请先删除");
        }
        return removeById(buildingType);



    }

    @Override
    public Object listBuildingTypes(int page, int row, BuildingType buildingType) {
        Page<BuildingType> page1 = new Page<>(page, row);
        QueryWrapper<BuildingType> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<BuildingType> lambda = wrapper.lambda();
        if (StringUtils.isNotEmpty(buildingType.getBuildingNo())){
            lambda.like(BuildingType::getBuildingNo,buildingType.getBuildingNo());
        }
        if (StringUtils.isNotEmpty(buildingType.getBuildingName())){
            lambda.like(BuildingType::getBuildingName,buildingType.getBuildingName());
        }
        lambda.orderByDesc(BuildingType::getCreateTime);
        IPage<BuildingType> data = page(page1, wrapper);
        List<BuildingType> records = data.getRecords();
        for (BuildingType record : records) {
            QueryWrapper<BuildingRoom> r = new QueryWrapper<>();
            r.lambda()
                    .eq(BuildingRoom::getBuildingId,record.getId());
            int count = roomService.count(r);
            record.setNum(count);
        }
        return data;


    }

    @Override
    public Object getBuildingType(BuildingType buildingType) {
        String id = buildingType.getId();
        BuildingType one = getById(id);
        if (null==one){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"教学楼不存在");
        }
        return one;

    }
}
