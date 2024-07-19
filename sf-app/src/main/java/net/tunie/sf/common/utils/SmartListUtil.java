package net.tunie.sf.common.utils;

import java.util.List;
import java.util.function.Predicate;

public class SmartListUtil {

    public static <T> int findIndex(List<T> list, Predicate<T> predicate) {
        for (int i = 0; i < list.size(); i++) {
            if (predicate.test(list.get(i))) {
                return i;
            }
        }
        return -1;
    }
}
