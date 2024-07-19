package net.tunie.sf.injector.mybatisplus;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import net.tunie.sf.injector.mybatisplus.methods.SelectIgnoreLogicDelete;
import org.apache.ibatis.session.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyLogicSqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Configuration configuration, Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(configuration, mapperClass, tableInfo);
        methodList.add(new SelectIgnoreLogicDelete());

        return methodList;
    }
}
