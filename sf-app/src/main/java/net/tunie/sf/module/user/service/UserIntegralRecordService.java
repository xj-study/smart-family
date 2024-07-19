package net.tunie.sf.module.user.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.tunie.sf.common.domain.PageDTO;
import net.tunie.sf.common.domain.PageParam;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartPageUtil;
import net.tunie.sf.constant.RecordTypeConst;
import net.tunie.sf.module.user.domain.dao.UserIntegralRecordDao;
import net.tunie.sf.module.user.domain.entity.UserIntegralRecordEntity;
import net.tunie.sf.module.user.domain.vo.UserIntegralRecordVo;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserIntegralRecordService extends ServiceImpl<UserIntegralRecordDao, UserIntegralRecordEntity> {

    public ResponseDTO<PageDTO<UserIntegralRecordVo>> queryRecord(Long requestUserId, PageParam pageParam) {
        Page<UserIntegralRecordVo> page = SmartPageUtil.convertPageParam(pageParam);
        IPage<UserIntegralRecordVo> userIntegralRecordVoIPage = this.baseMapper.selectRecords(page, requestUserId);
        userIntegralRecordVoIPage.setRecords(this.parseContent(userIntegralRecordVoIPage.getRecords()));
        return ResponseDTO.page(userIntegralRecordVoIPage);
    }

    private List<UserIntegralRecordVo> parseContent(List<UserIntegralRecordVo> records) {
        return records.stream().peek(item -> {
            JSONObject jsonObject = JSON.parseObject(item.getContent());
            String format = null;
            switch (item.getRefType()) {
                case RecordTypeConst.TASK:
                    Object taskDate = jsonObject.get("taskDate");
                    if (taskDate instanceof JSONArray) {
                        taskDate = Strings.join((JSONArray) taskDate, '-');
                    }
                    format = MessageFormat.format("完成任务【{0}({1})】", jsonObject.getString("title"), taskDate);
                    break;
                case RecordTypeConst.STORY_ACTIVE:
                    format = MessageFormat.format("激活活动【{0}】", jsonObject.getString("title"));
                    break;
                case RecordTypeConst.STORY_LEVEL_PASS:
                    format = MessageFormat.format("通关【{0}】", jsonObject.getString("title"));
                    break;
                case RecordTypeConst.ORDER_GIFT:
                    format = MessageFormat.format("兑换礼物【{0}】", jsonObject.getString("name"));
                    break;
            }
            item.setContent(format);
        }).collect(Collectors.toList());
    }
}
