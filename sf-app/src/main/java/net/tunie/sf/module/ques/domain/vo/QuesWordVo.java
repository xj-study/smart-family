package net.tunie.sf.module.ques.domain.vo;

import lombok.Data;
import net.tunie.sf.module.word.domain.vo.WordVo;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class QuesWordVo {

    private Long id;
    private Long wordId;
    private String enValue;
    private String zhValue;
    private Integer type;
    private Integer fillLevel;
    private List<WordVo> optionList;

    private LocalDateTime updateTime;
}
