# 基于 Spring Boot 的单元测试 Demo

> 本 Demo 包含了常用单元测试写法及建议

-------------------------------

#### 环境配置

0.安装 Docker 及 docker-compose，本地安装参考 [Docker Desktop](https://www.docker.com/products/docker-desktop)。

1.将 docker-env/docker-compose.yaml 文件复制到任意目录。

2.在 docker-compose.yaml 文件同级创建目录 data，data 下包含 mysql 和 redis 目录，保证读写权限：

```
-- BaseEnv
    | -- docker-compose.yaml
    | -- data
        | -- mysql
        | -- redis
```

3.在 docker-compose.yaml 同级目录执行 docker-compose up。

4.当然了，也可以自行搭建 MySQl 及 Redis 环境。

-------------------------------

## 写法

### Controller Rest API 测试

#### 1.打印 Rest 请求与响应参数
> 一般在 Controller 测试类父类声明 MockMvc 并使用 ``alwaysDo(print())`` 打印 Rest 请求与相应参数。

**代码：**
```
this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
        .alwaysDo(print())
        .build();
```

**打印示例：**
```
MockHttpServletRequest:
      HTTP Method = GET
      Request URI = /products/8
       Parameters = {}
          Headers = [Content-Type:"application/json;charset=utf-8"]
             Body = null
    Session Attrs = {}

Handler:
             Type = cn.hunter.spring.resource.ProductResource
           Method = cn.hunter.spring.resource.ProductResource#findOne(Long)

Async:
    Async started = false
     Async result = null

Resolved Exception:
             Type = null

ModelAndView:
        View name = null
             View = null
            Model = null

FlashMap:
       Attributes = null

MockHttpServletResponse:
           Status = 200
    Error message = null
          Headers = [Content-Type:"application/json"]
     Content type = application/json
             Body = {"data":{"id":8,"productLineId":1,"productName":"test-product-0","quantityInstock":25,"buyPrice":23.99,"expirationDate":"2020-01-01T00:00:00.000+0800"},"success":true}
    Forwarded URL = null
   Redirected URL = null
          Cookies = []
```

#### 2.回滚数据库测试数据

> 在测试类的声明语句上增加 ``@Transactional`` 注解即可实现该类所有测试方法操作库的回滚。

```
@Transactional
public class ProductResourceTest extends BaseResourceTest
```

#### 3.``@RequestBody`` ``@ModelAttribute`` ``@RequestParam`` ``@PathParam`` ``@PathVariable`` 测试请求方式

* ``@RequestBody`` 测试类中使用 ``.content(JSON.toJSONString(xx))`` 方法来传递参数。

**代码：**
```
// Controller
@PutMapping("/{id}")
public ResponseEntity update(@PathVariable("id") Long id, @RequestBody DemoProduct product)

// Test
DemoProduct productReq = buildUpdateDemoProduct();
this.mockMvc.perform(put("/products/" + productId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(JSON.toJSONString(productReq))
        .characterEncoding("utf-8"))
        .andExpect(status().isNoContent());
```



* ``@ModelAttribute`` 测试类中使用 ``.flashAttr()`` 方法来传递参数。

**代码：**
```
// Controller
@PostMapping
public ResponseEntity create(@ModelAttribute("product") DemoProduct product)

// Test
DemoProduct productReq = buildSaveDemoProduct(true);
ResultActions resultActions = this.mockMvc.perform(post("/products")
        .contentType(MediaType.APPLICATION_JSON)
        .flashAttr("product", productReq)
        .characterEncoding("utf-8"))
        .andExpect(status().isCreated());
```

* ``@RequestParam`` 和 ``@PathParam`` 测试类中使用 ``.param()`` 方法来传递参数。

**代码：**
```
// Controller
@PostMapping
public ResponseEntity<CommonResponse> findPage(@RequestParam("productName") String productName,
                                               @PathParam("currentPage") Integer currentPage,
                                               @PathParam("size") Integer size)

// Test
this.mockMvc.perform(get("/products")
        .contentType(MediaType.APPLICATION_JSON)
        .param("currentPage", String.valueOf(currentPage))
        .param("size", String.valueOf(size))
        .param("productName", productName)
        .characterEncoding("utf-8"))
        .andExpect(status().isOk());
```

* ``@PathVariable`` 测试类中直接将参数拼入请求地址即可。

**代码：**
```
// Controller
@GetMapping("/{id}")
public ResponseEntity<CommonResponse> findOne(@PathVariable("id") Long id)

// Test
this.mockMvc.perform(get("/products/" + productId)
```

#### 4.上传文件

> 上传文件在 Controller 中接参一般有两种写法，对应测试类中也有两种写法。

* 文件字段单独接参

**代码：**
```
// Controller
@PostMapping("upload")
public ResponseEntity upload(@RequestParam("file") MultipartFile file) {
    return ResponseEntity.created(createUri("/products/{fileName}", file.getOriginalFilename())).build();
}

// Test
// 第一个参数 file，需要与 Controller 中 @RequestParam("file") 相同
MockMultipartFile file = new MockMultipartFile("file" ...);
ResultActions resultActions = this.mockMvc.perform(multipart("/products/upload")
        .file(file)
        .characterEncoding("utf-8"))
        .andExpect(status().isCreated());
```

* 文件字段在 Bo 类中的接参

**代码：**
```
// Bo
public class UploadBo {
    private MultipartFile file;
}

// Controller
@PostMapping("upload-bo")
public ResponseEntity uploadBo(@ModelAttribute("uploadBo") UploadBo uploadBo) {
    return ResponseEntity.created(createUri("/products/{fileName}", uploadBo.getFile().getOriginalFilename())).build();
}

// Test
MockMultipartFile file = new MockMultipartFile("test-file" ...);
UploadBo bo = UploadBo.builder().file(file).build();
ResultActions resultActions = this.mockMvc.perform(multipart("/products/upload-bo")
        .flashAttr("uploadBo", bo)
        .characterEncoding("utf-8"))
        .andExpect(status().isCreated());
```

#### 5.断言 HTTP StatusCode

> 断言响应中的状态码，Controller 中使用 ``ResponseEntity`` 返回值为 ``BodyBuilder`` 的方法，测试类中使用``status().isXxx``进行断言。

* Created 201 用于响应保存/新建操作 ``.andExpect(status().isCreated())``

**代码：**
```
// Controller
return ResponseEntity.created(createUri("/products/{id}", result.getId())).build();

// Test
ResultActions resultActions = this.mockMvc.perform(post("/products")
        .contentType(MediaType.APPLICATION_JSON)
        .flashAttr("product", productReq)
        .characterEncoding("utf-8"))
        .andExpect(status().isCreated());
```

* NoContent 204 用于响应编辑/删除操作

**代码：**
```
// Controller
return ResponseEntity.noContent().build();

// Test
this.mockMvc.perform(put("/products/" + productId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(JSON.toJSONString(productReq))
        .characterEncoding("utf-8"))
        .andExpect(status().isNoContent());
```

* Ok 200 用于响应查询或其他成功操作

**代码：**
```
// Controller
return ResponseEntity.ok().body(commonResponse);

// Test
this.mockMvc.perform(get("/products/" + productId)
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8"))
        .andExpect(status().isOk());
```

* BadRequest 400 用于响应客户端请求错误

**代码：**
```
// Controller
return ResponseEntity.badRequest().body(commonResponse);

// Test
this.mockMvc.perform(post("/products")
        .contentType(MediaType.APPLICATION_JSON)
        .flashAttr("product", productReq)
        .characterEncoding("utf-8"))
        .andExpect(status().isBadRequest());
```

> 其他状态请参照 org.springframework.http.HttpStatus

#### 6.断言 Header 数据

> 使用 ``header()`` 断言 Header 中的值。

**代码：**
```
ResultActions resultActions = this.mockMvc.perform(post("/products")
        .contentType(MediaType.APPLICATION_JSON)
        .flashAttr("product", productReq)
        .characterEncoding("utf-8"))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", containsString("http://localhost/products/")));
```

#### 7.断言 Json 数据（JsonPath）

> 对于 Controller 返回的 Json 数据，在测试类中可使用如下方式断言。

**Json：**

```
{
    "data":{
        "id":8,
        "productLineId":1,
        "productName":"test-product-0",
        "quantityInstock":25,
        "buyPrice":23.99,
        "expirationDate":"2020-01-01T00:00:00.000+0800"
    },
    "success":true
}
```

**代码：**
```
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
```

> 更多关于 JsonPath 的写法请参照：[Jayway JsonPath](https://github.com/json-path/JsonPath)

#### 7.断言异常

> 有时候需要对可预见的异常进行断言，以期覆盖更多的场景。

**代码：**
```
// Service
@Transactional(readOnly = true)
public DemoProduct findOne(Long productId) {
    return productMapper.selectOne(
            p -> p.where(id, isEqualTo(productId))
    ).orElseThrow(() -> new NoSuchDataException("Cannot find product by id."));
}

// Test
Exception exception = assertThrows(NoSuchDataException.class, () -> {
    productService.findOne(productId);
});
assertEquals("Cannot find product by id.", exception.getMessage());
```

### Service 测试

#### 1.使用 ``@Before`` ``@After`` 准备和销毁数据

* 使用 ``@Before`` 准备数据

> ``@Before`` 适用于在执行测试方法之前准备测试环境，本次以准备 Redis 测试数据为例。

**代码：**
```
@Before
public void setUp() {
    redisTemplate.opsForValue().set(USER_1_KEY, readJsonFile(USER_1_FILE_PATH));
    Boolean hasKey = redisTemplate.hasKey(USER_1_KEY);
    assertNotNull(hasKey);
    assertTrue(hasKey);
}
```

* 使用 ``@After`` 销毁数据

> ``@After`` 适用于在执行测试方法之后销毁测试环境，本次以销毁 Redis 测试数据为例。

**代码：**
```
@After
public void tearDown() {
    redisTemplate.delete(USER_1_KEY);
    Boolean hasKey = redisTemplate.hasKey(USER_1_KEY);
    assertNotNull(hasKey);
    assertFalse(hasKey);
}
```

#### 2.准备接口 Mock 数据

> 对于远程过程调用函数，或其他原因无法/不想执行其真实方法体时，可使用 Mock 函数代替，并自行构造返回值。

**代码：**
```
// 需要被 Mock 的接口
public interface RpcService {
    UserRpcResponse findUserInfo(UserRpcRequest request);
}

// Service 调用
@Service
public class MainService {

    @Autowired
    private RpcService rpcService;

    public UserInfo getUserInfo(Long userId) {
        ...
        UserRpcResponse response = rpcService.findUserInfo(request);
        ...
    }
}

// Test
public class MainServiceTest extends BaseTest {
    @MockBean
    private RpcService rpcService;
    
    @Test
    public void should_success_get_user_info() {
        ...
        // 自行构造返回值
        UserRpcResponse response = UserRpcResponse.builder()
                .isSuccess(true)
                .code("0000")
                .message("success")
                .userInfo(UserInfo.builder().userId(userId).username(username).age(age).city(city).build())
                .build();
        when(rpcService.findUserInfo(any())).thenReturn(response);
        ...
    }
}
```

#### 3.准备接口静态方法 Mock 数据

> 对于某些静态方法，无法/不想执行其真实的方法体时，也可以 Mock 静态接口，自行构造返回参数。本 Demo 选择使用 Jmockit 实现静态方法 Mock。

> 首先需要引入 Jmockit Jar，配置 Maven Plugin。

**引入 Jar：**
```
<dependency>
    <groupId>org.jmockit</groupId>
    <artifactId>jmockit</artifactId>
    <version>1.48</version>
</dependency>
```

**配置 Maven Plugin：**
```
<plugin>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>2.22.2</version>
    <configuration>
        <argLine>
            -javaagent:${settings.localRepository}/org/jmockit/jmockit/1.48/jmockit-1.48.jar
        </argLine>
    </configuration>
</plugin>
```

**代码：**
```
// 需要被 Mock 的静态方法
public class StaticService {
    static DeptInfo findDeptInfo(String deptCode) {
        return DeptInfo.builder().deptCode(deptCode).build();
    }
}

// Service 调用
@Service
public class MainService {
    public DeptInfo getDeptInfo(String deptCode) {
        DeptInfo deptInfo = StaticService.findDeptInfo(deptCode);
        log.info("MainService-getDeptInfo-response={}", JSON.toJSONString(deptInfo));
        return deptInfo;
    }
}

// Test
public class MainServiceTest extends BaseTest {
    @Test
    public void should_success_get_dept_info() {
        ...
        new MockUp<StaticService>() {
            @Mock
            DeptInfo findDeptInfo(String deptCode) {
                return DeptInfo.builder().deptCode(deptCode).deptName(deptName).deptStaffNumber(deptStaffNumber).build();
            }
        };
        DeptInfo deptInfo = mainService.getDeptInfo(deptCode);
        ...
    }
}
```

> 更多请参考 [JMockit官网](https://jmockit.github.io/)

#### 4.断言控制台日志

> 对于有一些无法断言返回值，及无法通过数据等其他特征测试的方法，可以使用断言控制台日志的方式来测试。（正常情况不建议使用）

**代码：**
```
// Log Print
log.info("MainService-getDeptInfo-response={}", JSON.toJSONString(deptInfo));

// Test
public class MainServiceTest extends BaseTest {
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Test
    public void should_success_get_dept_info() {
        ...
        String consoleLog = systemOutRule.getLog();
        assertTrue(consoleLog.contains("MainService-getDeptInfo-response={\"deptCode\":\"000000\",\"deptName\":\"中台研发部\",\"deptStaffNumber\":66}"));
        ...
    }
}
```

> 更多请参考 [System Rules 官网](https://stefanbirkner.github.io/system-rules/index.html)

-------------------------------

## 建议

### 测试方法命名建议

> 一般建议使用 should_success/failed_do_sth_with_situation 的格式进行命名，命名中包含应当(成功/失败)去做(某件事)在(某种情况下)。

**例如：**

```
should_success_find_page()

should_fail_save_with_no_name()
```

### 测试建议

#### 1.查询

* 测试查询方法一般在测试类中先调用 Service 保存方法插入N条数据，然后根据查询条件调用 Controller 的查询接口。

* 在调用查询接口结束后，需要逐一断言字段是否为插入的值。见：``ProductResourceTest.should_success_find_one()``。

* 对于分页查询的测试，还要调用至少两次查询接口，断言至少两页数据。见：``ProductResourceTest.should_success_find_page()``。

#### 2.保存

* 测试保存方法一般在测试类中先调用 Controller 的保存接口，然后再调用 Service 的查询接口对字段逐一进行断言。见：``ProductResourceTest.should_success_save()``。

#### 3.更新

* 测试更新方法一般在测试类中 Service 保存方法插入N条数据，再调用 Controller 的更新接口，然后再调用 Service 的查询接口对字段逐一进行断言（包括更新及未更新的字段）。见：``ProductResourceTest.should_success_update()``。

#### 4.删除

* 测试更新方法一般在测试类中 Service 保存方法插入N条数据，再调用 Controller 的删除接口，然后再调用 Service 的查询接口断言数量或异常。见：``ProductResourceTest.should_success_delete()``。

#### 5.分支覆盖

* 代码中如有 ``if`` 或捕获异常的分支代码，务必使用预见失败的测试方法覆盖完全。见：``ProductResourceTest.should_fail_save_with_no_name()``。

