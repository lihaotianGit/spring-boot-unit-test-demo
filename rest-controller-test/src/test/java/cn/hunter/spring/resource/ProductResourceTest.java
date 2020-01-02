package cn.hunter.spring.resource;

import cn.hunter.spring.BaseResourceTest;
import cn.hunter.spring.bo.UploadBo;
import cn.hunter.spring.domain.DemoProduct;
import cn.hunter.spring.exception.NoSuchDataException;
import cn.hunter.spring.service.ProductService;
import com.alibaba.fastjson.JSON;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
public class ProductResourceTest extends BaseResourceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void should_success_test() throws Exception {
        this.mockMvc.perform(get("/products/test")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is("Successful!")));
    }

    @Test
    public void should_success_save() throws Exception {
        DemoProduct productReq = buildSaveDemoProduct(true);

        ResultActions resultActions = this.mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .flashAttr("product", productReq)
                .characterEncoding("utf-8"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("http://localhost/products/")));

        String resourceUri = String.valueOf(resultActions.andReturn().getResponse().getHeader("Location"));
        Long productId = Long.valueOf(resourceUri.substring(resourceUri.lastIndexOf("/") + 1));

        DemoProduct product = productService.findOne(productId);
        assertEquals(productReq.getProductName(), product.getProductName());
        assertEquals(productReq.getBuyPrice(), product.getBuyPrice());
        assertEquals(productReq.getExpirationDate(), product.getExpirationDate());
        assertEquals(productReq.getProductLineId(), product.getProductLineId());
        assertEquals(productReq.getQuantityInstock(), product.getQuantityInstock());
    }

    @Test
    public void should_fail_save_with_no_name() throws Exception {
        DemoProduct productReq = buildSaveDemoProduct(false);

        this.mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .flashAttr("product", productReq)
                .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Product name is null or empty!")));
    }

    @Test
    public void should_success_find_page() throws Exception {
        insertDemoProducts(3);

        Integer currentPage = 1;
        Integer size = 2;
        String productName = "test-product";

        this.mockMvc.perform(get("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .param("currentPage", String.valueOf(currentPage))
                .param("size", String.valueOf(size))
                .param("productName", productName)
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.current", is(1)))
                .andExpect(jsonPath("$.data.size", is(2)))
                .andExpect(jsonPath("$.data.total", is(3)))
                .andExpect(jsonPath("$.data.pages", is(2)))
                .andExpect(jsonPath("$.data.records.length()", is(2)))
                .andExpect(jsonPath("$.data.records[0].productName", is("test-product-0")))
                .andExpect(jsonPath("$.data.records[0].quantityInstock", is(25)))
                .andExpect(jsonPath("$.data.records[0].buyPrice", is(23.99)))
                .andExpect(jsonPath("$.data.records[0].expirationDate", is("2020-01-01T00:00:00.000+0800")))
                .andExpect(jsonPath("$.data.records[1].productName", is("test-product-1")))
                .andExpect(jsonPath("$.data.records[1].quantityInstock", is(26)))
                .andExpect(jsonPath("$.data.records[1].buyPrice", is(24.99)))
                .andExpect(jsonPath("$.data.records[1].expirationDate", is("2020-01-02T00:00:00.000+0800")));

        currentPage = 2;
        size = 2;
        this.mockMvc.perform(get("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .param("currentPage", String.valueOf(currentPage))
                .param("size", String.valueOf(size))
                .param("productName", productName)
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.current", is(2)))
                .andExpect(jsonPath("$.data.size", is(2)))
                .andExpect(jsonPath("$.data.total", is(3)))
                .andExpect(jsonPath("$.data.pages", is(2)))
                .andExpect(jsonPath("$.data.records.length()", is(1)))
                .andExpect(jsonPath("$.data.records[0].productName", is("test-product-2")))
                .andExpect(jsonPath("$.data.records[0].quantityInstock", is(27)))
                .andExpect(jsonPath("$.data.records[0].buyPrice", is(25.99)))
                .andExpect(jsonPath("$.data.records[0].expirationDate", is("2020-01-03T00:00:00.000+0800")));
    }

    @Test
    public void should_success_find_one() throws Exception {
        List<Long> idList = insertDemoProducts(1);
        Long productId = idList.get(0);

        this.mockMvc.perform(get("/products/" + productId)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.id", is(productId), Long.class)) // Expected 33L but is 33, use Long.class to fix it.
                .andExpect(jsonPath("$.data.productLineId", is(1)))
                .andExpect(jsonPath("$.data.productName", is("test-product-0")))
                .andExpect(jsonPath("$.data.quantityInstock", is(25)))
                .andExpect(jsonPath("$.data.buyPrice", is(23.99)))
                .andExpect(jsonPath("$.data.expirationDate", is("2020-01-01T00:00:00.000+0800")));

    }

    @Test
    public void should_success_update() throws Exception {
        List<Long> idList = insertDemoProducts(1);
        Long productId = idList.get(0);

        DemoProduct productReq = buildUpdateDemoProduct();

        this.mockMvc.perform(put("/products/" + productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(productReq))
                .characterEncoding("utf-8"))
                .andExpect(status().isNoContent());

        DemoProduct product = productService.findOne(productId);
        assertEquals(productReq.getProductName(), product.getProductName());
        assertEquals(productReq.getBuyPrice(), product.getBuyPrice());
        assertEquals(productReq.getExpirationDate(), product.getExpirationDate());
        assertEquals(productReq.getProductLineId(), product.getProductLineId());
        assertEquals(productReq.getQuantityInstock(), product.getQuantityInstock());
    }

    @Test
    public void should_success_delete() throws Exception {
        List<Long> idList = insertDemoProducts(1);
        Long productId = idList.get(0);

        this.mockMvc.perform(delete("/products/" + productId)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andExpect(status().isNoContent());

        Exception exception = assertThrows(NoSuchDataException.class, () -> {
            productService.findOne(productId);
        });
        assertEquals("Cannot find product by id.", exception.getMessage());
    }

    @Test
    public void should_success_upload() throws Exception {
        // 第一个参数 file，需要与 Controller 中 @RequestParam("file") 相同
        MockMultipartFile file = new MockMultipartFile("file", "img.jpg", MediaType.IMAGE_JPEG_VALUE, new ClassPathResource("img/img.jpg").getInputStream());

        ResultActions resultActions = this.mockMvc.perform(multipart("/products/upload")
                .file(file)
                .characterEncoding("utf-8"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("http://localhost/products/")));

        String resourceUri = String.valueOf(resultActions.andReturn().getResponse().getHeader("Location"));
        String fileName = resourceUri.substring(resourceUri.lastIndexOf("/") + 1);
        assertEquals("img.jpg", fileName);
    }

    @Test
    public void should_success_upload_bo() throws Exception {
        MockMultipartFile file = new MockMultipartFile("test-file", "img.jpg", MediaType.IMAGE_JPEG_VALUE, new ClassPathResource("img/img.jpg").getInputStream());
        UploadBo bo = UploadBo.builder().file(file).build();

        ResultActions resultActions = this.mockMvc.perform(multipart("/products/upload-bo")
                .flashAttr("uploadBo", bo)
                .characterEncoding("utf-8"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("http://localhost/products/")));

        String resourceUri = String.valueOf(resultActions.andReturn().getResponse().getHeader("Location"));
        String fileName = resourceUri.substring(resourceUri.lastIndexOf("/") + 1);
        assertEquals("img.jpg", fileName);
    }

    private DemoProduct buildSaveDemoProduct(boolean hasName) {
        DemoProduct product = new DemoProduct();
        product.setProductLineId(1L);
        if (hasName) {
            product.setProductName("test-product");
        }
        product.setBuyPrice(BigDecimal.valueOf(23.99));
        product.setExpirationDate(new DateTime().withYear(2020).withMonthOfYear(1).withDayOfMonth(1).withTimeAtStartOfDay().toDate());
        product.setQuantityInstock((short) 25);
        return product;
    }

    private List<Long> insertDemoProducts(int count) {
        List<Long> idList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            DemoProduct product = new DemoProduct();
            product.setProductLineId(1L);
            product.setProductName("test-product-" + i);
            product.setBuyPrice(BigDecimal.valueOf(23.99 + i));
            product.setExpirationDate(new DateTime().withYear(2020).withMonthOfYear(1).withDayOfMonth(1 + i).withTimeAtStartOfDay().toDate());
            product.setQuantityInstock((short) (25 + i));
            productService.save(product);
            idList.add(product.getId());
        }
        return idList;
    }

    private DemoProduct buildUpdateDemoProduct() {
        DemoProduct product = new DemoProduct();
        product.setProductLineId(2L);
        product.setProductName("test-product-update");
        product.setBuyPrice(BigDecimal.valueOf(33.99));
        product.setExpirationDate(new DateTime().withYear(2020).withMonthOfYear(2).withDayOfMonth(1).withTimeAtStartOfDay().toDate());
        product.setQuantityInstock((short) 35);
        return product;
    }

}
