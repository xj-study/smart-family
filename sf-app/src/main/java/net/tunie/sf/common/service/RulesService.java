package net.tunie.sf.common.service;

import com.alibaba.fastjson2.JSONObject;

public interface RulesService<T> {
    JSONObject getRules(T t);

    JSONObject getRules(Long id);

    void addOrUpdateQuesByRule(T t);
}
