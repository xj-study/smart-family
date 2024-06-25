package net.tunie.sf.common.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class SmartBeanUtil {

    public static <T> T copy(Object source, Class<T> target) {
        try {
            T newInstance = target.newInstance();
            BeanUtils.copyProperties(source, newInstance);
            return newInstance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T, K> List<K> copyList(List<T> source, Class<K> target) {
        return source.stream().map(e -> copy(e, target)).collect(Collectors.toList());
    }
}
