package net.tunie.sf.module.ques.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.tunie.sf.module.ques.domain.entity.QuesWordLinkEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface QuesWordLinkDao extends BaseMapper<QuesWordLinkEntity> {

}
