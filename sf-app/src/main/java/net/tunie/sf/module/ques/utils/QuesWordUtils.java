package net.tunie.sf.module.ques.utils;

import net.tunie.sf.module.ques.constant.QuesWordTypeConst;
import net.tunie.sf.module.ques.domain.entity.QuesWordEntity;

import java.time.LocalDateTime;

public class QuesWordUtils {

    /**
     * 更新时长 1 天
     */
    public static final int REFRESH_DURATION = 1;

    public static boolean checkNeedOptions(QuesWordEntity quesWordEntity) {
        if (quesWordEntity == null) {
            return false;
        }

        // 不是选择题
        return quesWordEntity.getType() == QuesWordTypeConst.SELECT || quesWordEntity.getType() == QuesWordTypeConst.SELECT_VIDEO;
    }

    public static boolean checkNeedRefreshOptions(QuesWordEntity quesWordEntity) {
        LocalDateTime plusUpdateTime = quesWordEntity.getUpdateTime().plusDays(REFRESH_DURATION);
        // 选项若过期了，需要重新获取
        return LocalDateTime.now().isAfter(plusUpdateTime);
    }
}
