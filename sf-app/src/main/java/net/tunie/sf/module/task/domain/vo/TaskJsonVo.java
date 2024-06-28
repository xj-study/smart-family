package net.tunie.sf.module.task.domain.vo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskJsonVo {
    private Long id;

    private Long taskId;

    private String title;

    private String content;

    private LocalDate taskDate;
}
