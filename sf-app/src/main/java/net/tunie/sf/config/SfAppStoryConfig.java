package net.tunie.sf.config;

import jakarta.annotation.Resource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties("sf-app.story")
@Data
public class SfAppStoryConfig {
    private long preLevel;
}

