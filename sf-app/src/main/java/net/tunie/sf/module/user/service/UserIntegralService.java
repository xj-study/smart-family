package net.tunie.sf.module.user.service;

import jakarta.annotation.Resource;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartBeanUtil;
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

    public void update(UserIntegralUpdateForm userIntegralUpdateForm) {

        UserIntegralRecordEntity userIntegralRecordEntity = SmartBeanUtil.copy(userIntegralUpdateForm, UserIntegralRecordEntity.class);
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

    public UserIntegralEntity queryUserIntegral(Long userId) {
        return userIntegralDao.selectById(userId);
    }

    public ResponseDTO<Integer> queryIntegral(Long requestUserId) {
        UserIntegralEntity userIntegralEntity = this.queryUserIntegral(requestUserId);
        int integral = 0;
        if (userIntegralEntity != null) {
            integral = userIntegralEntity.getIntegral();
        }
        return ResponseDTO.ok(integral);
    }
}
