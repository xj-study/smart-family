package net.tunie.sf.module.ques.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.tunie.sf.module.ques.domain.entity.QuesEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface QuesDao extends BaseMapper<QuesEntity> {

    void updateDisableFlagByWordId(@Param("wordId") Long wordId);
}
