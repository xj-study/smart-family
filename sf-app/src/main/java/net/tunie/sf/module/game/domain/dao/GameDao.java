package net.tunie.sf.module.game.domain.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.Data;
import net.tunie.sf.module.game.domain.entity.GameEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface GameDao extends BaseMapper<GameEntity> {
}
