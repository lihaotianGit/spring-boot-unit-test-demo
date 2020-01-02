package cn.hunter.spring.base;

import cn.hunter.spring.Application;
import cn.hunter.spring.util.FileHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class BaseTest {

    protected String readJsonFile(String filePath) {
        String json = FileHelper.readClassPathResourceFile(filePath);
        if (StringUtils.isEmpty(json)) {
            log.error("readJsonFile-json-is-empty-filePath={}", filePath);
            return "";
        }
        json = json.replaceAll(System.lineSeparator(), "");
        json = json.replaceAll(" ", "");
        return json;
    }

}
