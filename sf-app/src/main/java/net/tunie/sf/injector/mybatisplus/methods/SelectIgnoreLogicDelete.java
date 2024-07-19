package net.tunie.sf.injector.mybatisplus.methods;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

public class SelectIgnoreLogicDelete extends AbstractMethod {


    protected SelectIgnoreLogicDelete(String methodName) {
        super(methodName);
    }

    public SelectIgnoreLogicDelete() {
        this("selectIgnoreLogicDelete");
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.SELECT_LIST;
        String sql = String.format(sqlMethod.getSql(), this.sqlFirst(), this.sqlSelectColumns(tableInfo, true), tableInfo.getTableName(), this.sqlWhereEntityWrapper(true, tableInfo), this.sqlOrderBy(tableInfo), this.sqlComment());
        SqlSource sqlSource = super.createSqlSource(this.configuration, sql, modelClass);
        return this.addSelectMappedStatementForTable(mapperClass, this.methodName, sqlSource, tableInfo);
    }

    protected String sqlWhereEntityWrapper(boolean newLine, TableInfo table) {
        String _sgEs_ = "<bind name=\"_sgEs_\" value=\"ew.sqlSegment != null and ew.sqlSegment != ''\"/>";
        String andSqlSegment = SqlScriptUtils.convertIf(String.format(" AND ${%s}", "ew.sqlSegment"), String.format("_sgEs_ and %s", "ew.nonEmptyOfNormal"), true);
        String lastSqlSegment = SqlScriptUtils.convertIf(String.format(" ${%s}", "ew.sqlSegment"), String.format("_sgEs_ and %s", "ew.emptyOfNormal"), true);
        String sqlScript;

        sqlScript = table.getAllSqlWhere(false, false, true, "ew.entity.");
        sqlScript = SqlScriptUtils.convertIf(sqlScript, String.format("%s != null", "ew.entity"), true);
        sqlScript = SqlScriptUtils.convertWhere(sqlScript + "\n" + andSqlSegment) + "\n" + lastSqlSegment;
        sqlScript = SqlScriptUtils.convertIf(_sgEs_ + "\n" + sqlScript, String.format("%s != null", "ew"), true);
        return newLine ? "\n" + sqlScript : sqlScript;

    }

}
