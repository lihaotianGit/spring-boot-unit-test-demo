package cn.hunter.spring.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;

public class DemoProduct implements Serializable {
    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.884+08:00", comments = "Source field: demo_product.id")
    private Long id;

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.888+08:00", comments = "Source field: demo_product.product_line_id")
    private Long productLineId;

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.889+08:00", comments = "Source field: demo_product.product_name")
    private String productName;

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.889+08:00", comments = "Source field: demo_product.quantity_inStock")
    private Short quantityInstock;

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.889+08:00", comments = "Source field: demo_product.buy_price")
    private BigDecimal buyPrice;

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.889+08:00", comments = "Source field: demo_product.expiration_date")
    private Date expirationDate;

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.889+08:00", comments = "Source Table: demo_product")
    private static final long serialVersionUID = 1L;

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.888+08:00", comments = "Source field: demo_product.id")
    public Long getId() {
        return id;
    }

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.888+08:00", comments = "Source field: demo_product.id")
    public void setId(Long id) {
        this.id = id;
    }

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.889+08:00", comments = "Source field: demo_product.product_line_id")
    public Long getProductLineId() {
        return productLineId;
    }

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.889+08:00", comments = "Source field: demo_product.product_line_id")
    public void setProductLineId(Long productLineId) {
        this.productLineId = productLineId;
    }

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.889+08:00", comments = "Source field: demo_product.product_name")
    public String getProductName() {
        return productName;
    }

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.889+08:00", comments = "Source field: demo_product.product_name")
    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.889+08:00", comments = "Source field: demo_product.quantity_inStock")
    public Short getQuantityInstock() {
        return quantityInstock;
    }

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.889+08:00", comments = "Source field: demo_product.quantity_inStock")
    public void setQuantityInstock(Short quantityInstock) {
        this.quantityInstock = quantityInstock;
    }

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.889+08:00", comments = "Source field: demo_product.buy_price")
    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.889+08:00", comments = "Source field: demo_product.buy_price")
    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.889+08:00", comments = "Source field: demo_product.expiration_date")
    public Date getExpirationDate() {
        return expirationDate;
    }

    @Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2020-01-02T11:22:03.889+08:00", comments = "Source field: demo_product.expiration_date")
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}