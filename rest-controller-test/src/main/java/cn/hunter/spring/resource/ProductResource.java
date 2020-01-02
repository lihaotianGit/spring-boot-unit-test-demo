package cn.hunter.spring.resource;

import cn.hunter.spring.bo.PageBo;
import cn.hunter.spring.bo.UploadBo;
import cn.hunter.spring.domain.DemoProduct;
import cn.hunter.spring.helper.CommonResponse;
import cn.hunter.spring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;

import static cn.hunter.spring.helper.URIHelper.createUri;

@RestController
@RequestMapping("/products")
public class ProductResource {

    @Autowired
    private ProductService productService;

    @GetMapping("test")
    public ResponseEntity<CommonResponse> test() {
        CommonResponse commonResponse = CommonResponse.createCommonResponse();
        commonResponse.setData("result", "Successful!");
        return ResponseEntity.ok().body(commonResponse);
    }

    @PostMapping
    public ResponseEntity create(@ModelAttribute("product") DemoProduct product) {
        CommonResponse commonResponse = CommonResponse.createCommonResponse();
        if (StringUtils.isEmpty(product.getProductName())) {
            commonResponse.fail("Product name is null or empty!");
            return ResponseEntity.badRequest().body(commonResponse);
        }
        DemoProduct result = productService.save(product);
        return ResponseEntity.created(createUri("/products/{id}", result.getId())).build();
    }

    @GetMapping
    public ResponseEntity<CommonResponse> findPage(@RequestParam("productName") String productName,
                                                   @PathParam("currentPage") Integer currentPage,
                                                   @PathParam("size") Integer size) {
        CommonResponse commonResponse = CommonResponse.createCommonResponse();

        PageBo<DemoProduct> pageBo = productService.findPage(currentPage, size, productName);
        commonResponse.setData("data", pageBo);
        return ResponseEntity.ok().body(commonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> findOne(@PathVariable("id") Long id) {
        CommonResponse commonResponse = CommonResponse.createCommonResponse();
        commonResponse.setData("data", productService.findOne(id));
        return ResponseEntity.ok().body(commonResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody DemoProduct product) {
        product.setId(id);
        productService.update(product);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("upload")
    public ResponseEntity upload(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.created(createUri("/products/{fileName}", file.getOriginalFilename())).build();
    }

    @PostMapping("upload-bo")
    public ResponseEntity uploadBo(@ModelAttribute("uploadBo") UploadBo uploadBo) {
        return ResponseEntity.created(createUri("/products/{fileName}", uploadBo.getFile().getOriginalFilename())).build();
    }

}
