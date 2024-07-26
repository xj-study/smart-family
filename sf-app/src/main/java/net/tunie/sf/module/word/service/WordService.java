package net.tunie.sf.module.word.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
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
import net.tunie.sf.module.word.domain.vo.WordStatVo;
import net.tunie.sf.module.word.domain.vo.WordVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class WordService extends ServiceImpl<WordDao, WordEntity> {

    public ResponseDTO<PageDTO<WordVo>> queryWordPage(WordQueryForm wordQueryForm) {
        Page<WordEntity> page = SmartPageUtil.convert2PageParam(wordQueryForm);

        LambdaQueryWrapper<WordEntity> lambdaQueryWrapper = Wrappers.lambdaQuery(WordEntity.class);
        boolean hasKeyword = StringUtils.isNotBlank(wordQueryForm.getKeyword());
        lambdaQueryWrapper.like(hasKeyword, WordEntity::getEnValue, wordQueryForm.getKeyword());
        if (!hasKeyword) {
            lambdaQueryWrapper.eq(wordQueryForm.getLevel() != null, WordEntity::getLevel, wordQueryForm.getLevel());
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

    public List<WordEntity> randomWordList(Integer selectNum) {

        List<WordEntity> list = new ArrayList<>(selectNum);
        while (selectNum > 0) {
            WordEntity entity = this.randomWord();
            if (list.contains(entity)) continue;

            list.add(entity);
            selectNum--;
        }

        return list;
    }

    /**
     * 根据 字词 的难度随机获取
     *
     * @param level
     * @return
     */
    public WordEntity randomWordByLevel(Integer level) {
        return this.randomWord(Wrappers.lambdaQuery(WordEntity.class).eq(WordEntity::getLevel, level));
    }

    public WordEntity randomWord(Wrapper<WordEntity> wrapper) {
        long count = this.count(wrapper);
        Random random = new Random();
        long idx = random.nextLong(count);
        List<WordEntity> list = this.list(new Page<>(idx + 1, 1), wrapper);
        return list.get(0);
    }

    public WordEntity randomWord() {
        long count = this.count();
        Random random = new Random();
        long idx = random.nextLong(count);
        List<WordEntity> list = this.list(new Page<>(idx + 1, 1));
        return list.get(0);
    }

    /**
     * 统计单词各个难度的数量
     */
    public ResponseDTO<List<Integer>> queryStat() {
        int size = 5;
        List<WordStatVo> wordStatVos = this.baseMapper.statLevels();
        List<Integer> list = new ArrayList<>(size);

        int voIndex = 0;
        for (int i = 0; i < size; i++) {
            if (voIndex >= wordStatVos.size()) break;
            WordStatVo wordStatVo = wordStatVos.get(voIndex);
            int count = 0;
            if (wordStatVo.getLevel() == i) {
                count = wordStatVo.getCount();
                voIndex++;
            }
            list.add(count);
        }

        if (list.size() < size) {
            for (int i = list.size(); i < size; i++) {
                list.add(0);
            }
        }

        return ResponseDTO.ok(list);
    }
}
