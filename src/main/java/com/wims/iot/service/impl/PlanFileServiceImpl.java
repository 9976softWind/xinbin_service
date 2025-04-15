package com.wims.iot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wims.iot.common.util.RandomStringGenerator;
import com.wims.iot.mapper.PlanFileMapper;
import com.wims.iot.model.entity.ColFile;
import com.wims.iot.model.entity.PlanFile;
import com.wims.iot.service.IColFileService;
import com.wims.iot.service.IPlanFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 预案文件表 服务实现类
 * </p>
 *
 * @author tdw
 * @since 2025-04-14
 */
@Service
public class PlanFileServiceImpl extends ServiceImpl<PlanFileMapper, PlanFile> implements IPlanFileService {

    @Autowired
    IColFileService colFileService;

    @Override
    public PlanFile addPlanFile(PlanFile planFile) {
        ColFile colFileInfo = colFileService.getColFileById(planFile.getFileId());
        planFile.setName(colFileInfo.getFilename());
        planFile.setFileId("file_"+ RandomStringGenerator.generate(6));
        planFile.setCreatedAt(new Date());
        planFile.setUpdatedAt(planFile.getCreatedAt());
//        if(this.baseMapper.insert(planDirectory) == 1){
//            return planDirectory;
//        }else{
//            throw new KGBusinessException(KgpResultCode.SYSTEM_EXECUTION_ERROR);
//        }
        return null;

    }

    @Override
    public Boolean isDirHasFiles(String directoryId) {
        return this.baseMapper.exists(new QueryWrapper<PlanFile>().eq("directory_id",directoryId));
    }
}
