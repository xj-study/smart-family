package net.tunie.sf.module.order.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.tunie.sf.module.order.domain.entity.OrderGiftEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface OrderGiftDao extends BaseMapper<OrderGiftEntity> {
}
