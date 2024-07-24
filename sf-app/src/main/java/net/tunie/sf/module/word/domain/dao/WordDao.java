package net.tunie.sf.module.word.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.tunie.sf.module.word.domain.entity.WordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface WordDao extends BaseMapper<WordEntity> {
    List<WordEntity> selectRandomList(Integer selectNum, Long wordId);
}
