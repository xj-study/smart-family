package net.tunie.sf.module.story.service;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import net.tunie.sf.common.code.UserErrorCode;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartListUtil;
import net.tunie.sf.common.utils.SmartUserUtil;
import net.tunie.sf.config.SfAppStoryConfig;
import net.tunie.sf.constant.RecordTypeConst;
import net.tunie.sf.module.login.domain.RequestUser;
import net.tunie.sf.module.story.constant.StoryLevelStatusConst;
import net.tunie.sf.module.story.constant.StoryRecordStatusConst;
import net.tunie.sf.module.story.constant.StoryStatusConst;
import net.tunie.sf.module.story.constant.StoryTypeConst;
import net.tunie.sf.module.story.domain.dao.StoryRecordDao;
import net.tunie.sf.module.story.domain.entity.StoryEntity;
import net.tunie.sf.module.story.domain.entity.StoryLevelEntity;
import net.tunie.sf.module.story.domain.entity.StoryRecordEntity;
import net.tunie.sf.module.story.domain.form.StoryRecordQueryForm;
import net.tunie.sf.module.story.domain.form.StoryRecordUpdateLevelForm;
import net.tunie.sf.module.story.domain.form.StoryRecordUpdateStatusForm;
import net.tunie.sf.module.story.domain.vo.StoryLevelVo;
import net.tunie.sf.module.story.domain.vo.StoryRecordVo;
import net.tunie.sf.module.user.domain.form.UserIntegralUpdateForm;
import net.tunie.sf.module.user.service.UserIntegralService;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class StoryRecordService extends ServiceImpl<StoryRecordDao, StoryRecordEntity> {

    @Resource
    private SfAppStoryConfig sfAppStoryConfig;
    @Resource
    private StoryLevelService storyLevelService;
    @Resource
    private UserIntegralService userIntegralService;
    @Resource
    private StoryService storyService;

    public ResponseDTO<List<StoryRecordVo>> queryStoryRecordList(RequestUser requestUser, StoryRecordQueryForm storyRecordQueryForm) {
        boolean userChildFlag = SmartUserUtil.getUserChildFlag(requestUser.getType());
        Long parentId = userChildFlag ? requestUser.getParentId() : requestUser.getUserId();
        Long userId = requestUser.getUserId();
        storyRecordQueryForm.setStoryStatus(StoryStatusConst.PUBLISHED);
        List<StoryRecordVo> storyRecordVos = this.baseMapper.selectStoryRecordList(parentId, userId, storyRecordQueryForm);
        return ResponseDTO.ok(storyRecordVos);
    }

    public ResponseDTO<StoryRecordVo> queryStoryRecord(Long userId, Long storyId) {

        StoryRecordVo storyRecordVo = this.baseMapper.selectStoryRecord(userId, storyId);
        if (storyRecordVo == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }

        List<StoryLevelVo> levels;

        boolean simpleFlag = storyRecordVo.getType() == StoryTypeConst.SIMPLE;
        long size = simpleFlag ? -1 : sfAppStoryConfig.getPreLevel();
        int index = -1;

        if (storyRecordVo.getId() == null) {
            // 首次访问
            StoryRecordEntity storyRecordEntity = new StoryRecordEntity();
            storyRecordEntity.setStoryId(storyId);
            storyRecordEntity.setUserId(userId);

            this.save(storyRecordEntity);

            storyRecordVo.setId(storyRecordEntity.getId());
            levels = storyLevelService.queryStoryLevelList(storyId, size).getData();
        } else {
            if (storyRecordVo.getLevelId() == -1) {
                levels = storyLevelService.queryStoryLevelList(storyId, size).getData();
            } else {
                if (storyRecordVo.getLevelIndex() == null) {
                    levels = storyLevelService.queryStoryLevelList(storyId).getData();
                    index = SmartListUtil.findIndex(levels, it -> it.getId().equals(storyRecordVo.getLevelId()));
                    if (!simpleFlag) {
                        levels = levels.stream().limit(index + 1 + sfAppStoryConfig.getPreLevel()).collect(Collectors.toList());
                    }
                } else {
                    index = storyRecordVo.getLevelIndex();
                    size = simpleFlag ? -1 : index + 1 + sfAppStoryConfig.getPreLevel();
                    levels = storyLevelService.queryStoryLevelList(storyId, size).getData();
                }
            }
        }

        if (storyRecordVo.getStatus() != StoryRecordStatusConst.NOT_ACTIVE) {
            // 设置关卡的状态
            for (int i = 0; i < index + 1; i++) {
                levels.get(i).setStatus(StoryLevelStatusConst.PASS);
            }

            if (index + 1 < levels.size()) {
                levels.get(index + 1).setStatus(StoryLevelStatusConst.ACTIVE);
            }
        }

        storyRecordVo.setLevels(levels);
        return ResponseDTO.ok(storyRecordVo);
    }

    public ResponseDTO<Integer> updateStoryRecordStatus(StoryRecordUpdateStatusForm storyRecordUpdateStatusForm) {

        return this.update(
                e -> e.setStatus(storyRecordUpdateStatusForm.getStatus()),
                storyRecordUpdateStatusForm::getId,
                (storyRecordEntity) -> {
                    StoryEntity storyEntity = storyService.getById(storyRecordEntity.getStoryId());
                    UserIntegralUpdateForm userIntegralUpdateForm = this.buildForm(RecordTypeConst.STORY_ACTIVE, form -> {
                        form.setUserId(storyRecordUpdateStatusForm.getUserId());
                        form.setRefId(storyRecordUpdateStatusForm.getId());
                        form.setIntegralChange(-storyEntity.getCostAmount());
                    });
                    userIntegralUpdateForm.setContent(this.buildContent(storyEntity::getTitle, storyEntity::getId, storyRecordEntity::getUpdateTime));
                    return userIntegralUpdateForm;
                },
                StoryRecordEntity::getStatus
        );
    }

    public ResponseDTO<Long> updateStoryRecordLevel(StoryRecordUpdateLevelForm storyRecordUpdateLevelForm) {

        return this.update(
                entity -> {
                    int index = entity.getLevelIndex() + 1;
                    entity.setLevelId(storyRecordUpdateLevelForm.getLevelId());
                    entity.setLevelIndex(index);

                    long count = this.storyLevelService.count(Wrappers.lambdaQuery(StoryLevelEntity.class).eq(StoryLevelEntity::getStoryId, entity.getStoryId()));
                    if (index + 1 >= count) {
                        entity.setStatus(StoryRecordStatusConst.PASS_ALL);
                    }

                },
                storyRecordUpdateLevelForm::getId,
                (storyRecordEntity) -> {

                    StoryLevelEntity storyLevelEntity = storyLevelService.getById(storyRecordUpdateLevelForm.getLevelId());
                    UserIntegralUpdateForm userIntegralUpdateForm = this.buildForm(RecordTypeConst.STORY_LEVEL_PASS, form -> {
                        form.setUserId(storyRecordUpdateLevelForm.getUserId());
                        form.setRefId(storyRecordUpdateLevelForm.getId());
                        form.setIntegralChange(storyLevelEntity.getPrize());
                    });

                    Supplier<String> sfTitle = () -> MessageFormat.format("{0}-{1}", storyRecordUpdateLevelForm.getStoryTitle(), storyLevelEntity.getTitle());
                    userIntegralUpdateForm.setContent(this.buildContent(sfTitle, storyLevelEntity::getId, storyRecordEntity::getUpdateTime));
                    return userIntegralUpdateForm;
                },
                StoryRecordEntity::getLevelId
        );
    }

    private <U> ResponseDTO<U> update(Consumer<StoryRecordEntity> cfUpdate, LongSupplier getId, Function<StoryRecordEntity, UserIntegralUpdateForm> getForm, Function<StoryRecordEntity, U> getResult) {
        StoryRecordEntity storyRecordEntity = this.getById(getId.getAsLong());
        if (storyRecordEntity == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }
        cfUpdate.accept(storyRecordEntity);
        this.updateById(storyRecordEntity);

        UserIntegralUpdateForm userIntegralUpdateForm = getForm.apply(storyRecordEntity);
        userIntegralService.update(userIntegralUpdateForm);

        return ResponseDTO.ok(getResult.apply(storyRecordEntity));
    }

    private UserIntegralUpdateForm buildForm(int type, Consumer<UserIntegralUpdateForm> consumer) {
        UserIntegralUpdateForm userIntegralUpdateForm = new UserIntegralUpdateForm();
        userIntegralUpdateForm.setRefType(type);
        consumer.accept(userIntegralUpdateForm);
        return userIntegralUpdateForm;
    }

    private String buildContent(Supplier<String> sfTitle, Supplier<Long> sfId, Supplier<LocalDateTime> sfUpdateTime) {
        HashMap<String, Object> content = new HashMap<>();
        content.put("title", sfTitle.get());
        content.put("id", sfId.get());
        content.put("updateTime", sfUpdateTime.get());
        return JSON.toJSONString(content);
    }

}
