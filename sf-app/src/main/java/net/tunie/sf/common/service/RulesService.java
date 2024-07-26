package net.tunie.sf.common.service;

import com.alibaba.fastjson2.JSONObject;

public interface RulesService<T> {
    JSONObject getRules(T t);

    void addOrUpdateQuesByRule(T t);
}
