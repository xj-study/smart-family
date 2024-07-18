package net.tunie.sf.module.story.domain.form;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StoryRecordUpdateStatusForm {
    private Long id;
    private Long userId;
    private Integer status;

    public StoryRecordUpdateStatusForm(Long id) {
        this.id = id;
    }
}
