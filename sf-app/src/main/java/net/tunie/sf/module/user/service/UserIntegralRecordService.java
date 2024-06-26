package net.tunie.sf.module.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartBeanUtil;
import net.tunie.sf.module.user.domain.dao.UserIntegralRecordDao;
import net.tunie.sf.module.user.domain.entity.UserIntegralRecordEntity;
import net.tunie.sf.module.user.domain.vo.UserIntegralRecordVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserIntegralRecordService {

    @Resource
    private UserIntegralRecordDao userIntegralRecordDao;

    public void add(UserIntegralRecordEntity userIntegralRecordEntity) {
        userIntegralRecordDao.insert(userIntegralRecordEntity);
    }

    public ResponseDTO<List<UserIntegralRecordVo>> queryRecord(Long requestUserId) {
        List<UserIntegralRecordVo> userIntegralRecordVos = userIntegralRecordDao.selectRecords(requestUserId);
        return ResponseDTO.ok(userIntegralRecordVos);
    }
}
