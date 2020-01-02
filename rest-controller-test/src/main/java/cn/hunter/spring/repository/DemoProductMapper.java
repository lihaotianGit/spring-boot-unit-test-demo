package cn.hunter.spring.repository;

import static cn.hunter.spring.repository.DemoProductDynamicSqlSupport.*;

import cn.hunter.spring.domain.DemoProduct;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

@Mapper
public interface DemoProductMapper {
    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.904+08:00", comments = "Source Table: demo_product")
    BasicColumn[] selectList = BasicColumn.columnList(id, productLineId, productName, quantityInstock, buyPrice, expirationDate);

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.895+08:00", comments = "Source Table: demo_product")
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.896+08:00", comments = "Source Table: demo_product")
    @DeleteProvider(type = SqlProviderAdapter.class, method = "delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.896+08:00", comments = "Source Table: demo_product")
    @InsertProvider(type = SqlProviderAdapter.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "record.id")
    int insert(InsertStatementProvider<DemoProduct> insertStatement);

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.897+08:00", comments = "Source Table: demo_product")
    @InsertProvider(type = SqlProviderAdapter.class, method = "insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<DemoProduct> multipleInsertStatement);

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.897+08:00", comments = "Source Table: demo_product")
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    @ResultMap("DemoProductResult")
    Optional<DemoProduct> selectOne(SelectStatementProvider selectStatement);

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.898+08:00", comments = "Source Table: demo_product")
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    @Results(id = "DemoProductResult", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "product_line_id", property = "productLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "product_name", property = "productName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "quantity_inStock", property = "quantityInstock", jdbcType = JdbcType.SMALLINT),
            @Result(column = "buy_price", property = "buyPrice", jdbcType = JdbcType.DECIMAL),
            @Result(column = "expiration_date", property = "expirationDate", jdbcType = JdbcType.TIMESTAMP)
    })
    List<DemoProduct> selectMany(SelectStatementProvider selectStatement);

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.899+08:00", comments = "Source Table: demo_product")
    @UpdateProvider(type = SqlProviderAdapter.class, method = "update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.9+08:00", comments = "Source Table: demo_product")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, demoProduct, completer);
    }

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.9+08:00", comments = "Source Table: demo_product")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, demoProduct, completer);
    }

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.901+08:00", comments = "Source Table: demo_product")
    default int insert(DemoProduct record) {
        return MyBatis3Utils.insert(this::insert, record, demoProduct, c ->
                c.map(id).toProperty("id")
                        .map(productLineId).toProperty("productLineId")
                        .map(productName).toProperty("productName")
                        .map(quantityInstock).toProperty("quantityInstock")
                        .map(buyPrice).toProperty("buyPrice")
                        .map(expirationDate).toProperty("expirationDate")
        );
    }

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.903+08:00", comments = "Source Table: demo_product")
    default int insertMultiple(Collection<DemoProduct> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, demoProduct, c ->
                c.map(id).toProperty("id")
                        .map(productLineId).toProperty("productLineId")
                        .map(productName).toProperty("productName")
                        .map(quantityInstock).toProperty("quantityInstock")
                        .map(buyPrice).toProperty("buyPrice")
                        .map(expirationDate).toProperty("expirationDate")
        );
    }

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.903+08:00", comments = "Source Table: demo_product")
    default int insertSelective(DemoProduct record) {
        return MyBatis3Utils.insert(this::insert, record, demoProduct, c ->
                c.map(id).toPropertyWhenPresent("id", record::getId)
                        .map(productLineId).toPropertyWhenPresent("productLineId", record::getProductLineId)
                        .map(productName).toPropertyWhenPresent("productName", record::getProductName)
                        .map(quantityInstock).toPropertyWhenPresent("quantityInstock", record::getQuantityInstock)
                        .map(buyPrice).toPropertyWhenPresent("buyPrice", record::getBuyPrice)
                        .map(expirationDate).toPropertyWhenPresent("expirationDate", record::getExpirationDate)
        );
    }

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.905+08:00", comments = "Source Table: demo_product")
    default Optional<DemoProduct> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, demoProduct, completer);
    }

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.906+08:00", comments = "Source Table: demo_product")
    default List<DemoProduct> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, demoProduct, completer);
    }

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.906+08:00", comments = "Source Table: demo_product")
    default List<DemoProduct> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, demoProduct, completer);
    }

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.907+08:00", comments = "Source Table: demo_product")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, demoProduct, completer);
    }

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.907+08:00", comments = "Source Table: demo_product")
    static UpdateDSL<UpdateModel> updateAllColumns(DemoProduct record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(record::getId)
                .set(productLineId).equalTo(record::getProductLineId)
                .set(productName).equalTo(record::getProductName)
                .set(quantityInstock).equalTo(record::getQuantityInstock)
                .set(buyPrice).equalTo(record::getBuyPrice)
                .set(expirationDate).equalTo(record::getExpirationDate);
    }

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.908+08:00", comments = "Source Table: demo_product")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(DemoProduct record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(record::getId)
                .set(productLineId).equalToWhenPresent(record::getProductLineId)
                .set(productName).equalToWhenPresent(record::getProductName)
                .set(quantityInstock).equalToWhenPresent(record::getQuantityInstock)
                .set(buyPrice).equalToWhenPresent(record::getBuyPrice)
                .set(expirationDate).equalToWhenPresent(record::getExpirationDate);
    }
}