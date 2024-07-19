package net.tunie.sf.injector.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyBaseMapper<T> extends BaseMapper<T> {

    List<T> selectIgnoreLogicDelete(@Param("ew") Wrapper<T> queryWrapper);
}
