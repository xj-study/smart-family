package net.tunie.sf.module.user.service;

import jakarta.annotation.Resource;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartBeanUtil;
import net.tunie.sf.module.system.record.domain.form.RecordAddForm;
import net.tunie.sf.module.system.record.service.RecordService;
import net.tunie.sf.module.user.domain.dao.UserIntegralDao;
import net.tunie.sf.module.user.domain.entity.UserIntegralEntity;
import net.tunie.sf.module.user.domain.entity.UserIntegralRecordEntity;
import net.tunie.sf.module.user.domain.form.UserIntegralUpdateForm;
import org.springframework.stereotype.Service;

@Service
public class UserIntegralService {

    @Resource
    private UserIntegralDao userIntegralDao;

    @Resource
    private UserIntegralRecordService userIntegralRecordService;

    @Resource
    private RecordService recordService;

    public void update(UserIntegralUpdateForm userIntegralUpdateForm) {

        RecordAddForm recordAddForm = SmartBeanUtil.copy(userIntegralUpdateForm, RecordAddForm.class);
        Long recordId = recordService.add(recordAddForm);

        UserIntegralRecordEntity userIntegralRecordEntity = new UserIntegralRecordEntity();
        userIntegralRecordEntity.setUserId(userIntegralUpdateForm.getUserId());
        userIntegralRecordEntity.setIntegralChange(userIntegralUpdateForm.getIntegralChange());
        userIntegralRecordEntity.setRefId(recordId);
        userIntegralRecordService.add(userIntegralRecordEntity);

        UserIntegralEntity userIntegralEntity = userIntegralDao.selectById(userIntegralUpdateForm.getUserId());
        if (userIntegralEntity == null) {
            userIntegralEntity = new UserIntegralEntity();
            userIntegralEntity.setUserId(userIntegralUpdateForm.getUserId());
            userIntegralEntity.setIntegral(userIntegralRecordEntity.getIntegralChange());
            userIntegralDao.insert(userIntegralEntity);
        } else {
            Integer newIntegral = userIntegralEntity.getIntegral() + userIntegralUpdateForm.getIntegralChange();
            userIntegralEntity.setIntegral(newIntegral);
            userIntegralDao.updateById(userIntegralEntity);
        }
    }

    public Integer queryUserIntegral(Long userId) {

        UserIntegralEntity userIntegralEntity = userIntegralDao.selectById(userId);
        int integral = 0;
        if (userIntegralEntity != null) {
            integral = userIntegralEntity.getIntegral();
        }
        return integral;
    }

    public ResponseDTO<Integer> queryIntegral(Long requestUserId) {
        return ResponseDTO.ok(this.queryUserIntegral(requestUserId));
    }
}
