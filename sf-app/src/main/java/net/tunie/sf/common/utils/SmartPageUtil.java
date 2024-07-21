package net.tunie.sf.common.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.tunie.sf.common.domain.PageParam;

import java.util.List;

public class SmartPageUtil {

    public static <T> Page<T> convert2PageParam(PageParam pageParam) {
        Page<T> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        return page;
    }

    public static <T> Page<T> convert2PageResult(Page<?> page, List<T> list) {
        Page<T> tPage = new Page<>();
        tPage.setRecords(list);
        tPage.setSize(page.getSize());
        tPage.setTotal(page.getTotal());
        tPage.setPages(page.getPages());
        tPage.setCurrent(page.getCurrent());
        return tPage;
    }
}
