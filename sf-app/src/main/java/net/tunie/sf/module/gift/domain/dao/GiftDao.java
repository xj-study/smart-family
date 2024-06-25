package net.tunie.sf.module.gift.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.tunie.sf.module.gift.domain.entity.GiftEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface GiftDao extends BaseMapper<GiftEntity> {
}
