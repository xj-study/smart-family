package net.tunie.sf.module.ques.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class QuesVo {

    private Long id;

    private List<QuesWordVo> list;
}
