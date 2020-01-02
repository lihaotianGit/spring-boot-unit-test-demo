package cn.hunter.spring.service;

import cn.hunter.spring.exception.NoSuchDataException;
import cn.hunter.spring.bo.PageBo;
import cn.hunter.spring.domain.DemoProduct;
import cn.hunter.spring.repository.DemoProductMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cn.hunter.spring.repository.DemoProductDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isLike;

@Service
public class ProductService {

    @Autowired
    private DemoProductMapper productMapper;

    @Transactional
    public DemoProduct save(DemoProduct product) {
        productMapper.insert(product);
        return product;
    }

    @Transactional(readOnly = true)
    public PageBo<DemoProduct> findPage(Integer currentPage, Integer size, String productNameReq) {
        PageHelper.startPage(currentPage, size, true);
        List<DemoProduct> record = productMapper.select(
                p -> p.where(productName, isLike("%" + productNameReq + "%"))
        );
        PageInfo<DemoProduct> pageInfo = new PageInfo<>(record);
        return new PageBo<>(currentPage, size, pageInfo.getTotal(), pageInfo.getPages(), record);
    }

    @Transactional(readOnly = true)
    public DemoProduct findOne(Long productId) {
        return productMapper.selectOne(
                p -> p.where(id, isEqualTo(productId))
        ).orElseThrow(() -> new NoSuchDataException("Cannot find product by id."));
    }

    @Transactional
    public int update(DemoProduct product) {
        return productMapper.update(
                p -> p.set(productName).equalTo(product.getProductName())
                        .set(quantityInstock).equalTo(product.getQuantityInstock())
                        .set(buyPrice).equalTo(product.getBuyPrice())
                        .set(productLineId).equalTo(product.getProductLineId())
                        .set(expirationDate).equalTo(product.getExpirationDate())
                        .where(id, isEqualTo(product.getId()))
        );
    }

    @Transactional
    public int delete(Long productId) {
        return productMapper.delete(p -> p.where(id, isEqualTo(productId)));
    }

}
