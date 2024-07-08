package net.tunie.sf.module.order.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.tunie.sf.module.order.domain.entity.OrderGiftEntity;
import net.tunie.sf.module.order.domain.form.OrderGiftQueryForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface OrderGiftDao extends BaseMapper<OrderGiftEntity> {
    List<OrderGiftEntity> selectGiftList(@Param("orderGift")OrderGiftQueryForm orderGiftQueryForm);
}
