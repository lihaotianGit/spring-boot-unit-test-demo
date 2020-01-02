package cn.hunter.spring.repository;

import java.math.BigDecimal;
import java.sql.JDBCType;
import java.util.Date;
import javax.annotation.Generated;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class DemoProductDynamicSqlSupport {
    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.893+08:00", comments = "Source Table: demo_product")
    public static final DemoProduct demoProduct = new DemoProduct();

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.893+08:00", comments = "Source field: demo_product.id")
    public static final SqlColumn<Long> id = demoProduct.id;

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.894+08:00", comments = "Source field: demo_product.product_line_id")
    public static final SqlColumn<Long> productLineId = demoProduct.productLineId;

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.894+08:00", comments = "Source field: demo_product.product_name")
    public static final SqlColumn<String> productName = demoProduct.productName;

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.894+08:00", comments = "Source field: demo_product.quantity_inStock")
    public static final SqlColumn<Short> quantityInstock = demoProduct.quantityInstock;

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.894+08:00", comments = "Source field: demo_product.buy_price")
    public static final SqlColumn<BigDecimal> buyPrice = demoProduct.buyPrice;

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.894+08:00", comments = "Source field: demo_product.expiration_date")
    public static final SqlColumn<Date> expirationDate = demoProduct.expirationDate;

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.893+08:00", comments = "Source Table: demo_product")
    public static final class DemoProduct extends SqlTable {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<Long> productLineId = column("product_line_id", JDBCType.BIGINT);

        public final SqlColumn<String> productName = column("product_name", JDBCType.VARCHAR);

        public final SqlColumn<Short> quantityInstock = column("quantity_inStock", JDBCType.SMALLINT);

        public final SqlColumn<BigDecimal> buyPrice = column("buy_price", JDBCType.DECIMAL);

        public final SqlColumn<Date> expirationDate = column("expiration_date", JDBCType.TIMESTAMP);

        public DemoProduct() {
            super("demo_product");
        }
    }
}