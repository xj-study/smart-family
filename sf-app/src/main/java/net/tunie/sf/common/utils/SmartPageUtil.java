package net.tunie.sf.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.tunie.sf.common.domain.PageParam;

public class SmartPageUtil {

    public static <T> Page<T> convertPageParam(PageParam pageParam) {
        Page<T> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        return page;
    }
}
