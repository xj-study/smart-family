package net.tunie.sf.module.story.domain.form;

import lombok.Data;

@Data
public class StoryUpdateForm  extends StoryAddForm {
    public StoryUpdateForm() {
    }

    public StoryUpdateForm(Long id) {
        this.id = id;
    }

    private Long id;
}
