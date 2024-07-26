package net.tunie.sf.module.ques.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.tunie.sf.module.ques.domain.entity.QuesWordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface QuesWordDao extends BaseMapper<QuesWordEntity> {
    List<QuesWordEntity> list(@Param("quesId") Long quesId);

}
