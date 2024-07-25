package net.tunie.sf.module.ques.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.tunie.sf.module.ques.domain.entity.QuesWordAnswerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface QuesWordAnswerDao extends BaseMapper<QuesWordAnswerEntity> {

}
