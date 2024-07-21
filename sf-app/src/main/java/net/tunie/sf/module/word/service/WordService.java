package net.tunie.sf.module.word.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.tunie.sf.common.code.UserErrorCode;
import net.tunie.sf.common.domain.PageDTO;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartBeanUtil;
import net.tunie.sf.common.utils.SmartPageUtil;
import net.tunie.sf.module.word.domain.dao.WordDao;
import net.tunie.sf.module.word.domain.entity.WordEntity;
import net.tunie.sf.module.word.domain.form.WordAddForm;
import net.tunie.sf.module.word.domain.form.WordQueryForm;
import net.tunie.sf.module.word.domain.form.WordUpdateForm;
import net.tunie.sf.module.word.domain.vo.WordVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordService extends ServiceImpl<WordDao, WordEntity> {


    public ResponseDTO<PageDTO<WordVo>> queryWordPage(WordQueryForm wordQueryForm) {
        Page<WordEntity> page = SmartPageUtil.convert2PageParam(wordQueryForm);

        LambdaQueryWrapper<WordEntity> lambdaQueryWrapper = Wrappers.lambdaQuery(WordEntity.class);
        if (StringUtils.isNotBlank(wordQueryForm.getKeyword())) {
            lambdaQueryWrapper.like(WordEntity::getEnValue, wordQueryForm.getKeyword());
        }
        List<WordEntity> list = this.list(page, lambdaQueryWrapper);

        List<WordVo> wordVos = SmartBeanUtil.copyList(list, WordVo.class);
        Page<WordVo> wordVoPage = SmartPageUtil.convert2PageResult(page, wordVos);

        return ResponseDTO.page(wordVoPage);
    }

    public ResponseDTO<Long> addWord(WordAddForm wordAddForm) {
        WordEntity one = this.getOne(Wrappers.lambdaQuery(WordEntity.class).eq(WordEntity::getEnValue, wordAddForm.getEnValue()));
        if (one != null) {
            return ResponseDTO.userErrorParams("这个单词已经收录啦！");
        }

        WordEntity entity = SmartBeanUtil.copy(wordAddForm, WordEntity.class);
        this.save(entity);
        return ResponseDTO.ok(entity.getId());
    }

    public ResponseDTO<String> updateWord(WordUpdateForm wordUpdateForm) {
        if (wordUpdateForm.getId() == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }

        WordEntity entity = SmartBeanUtil.copy(wordUpdateForm, WordEntity.class);
        this.updateById(entity);
        return ResponseDTO.ok();
    }
}
