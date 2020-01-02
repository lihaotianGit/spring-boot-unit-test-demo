package cn.hunter.spring.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;

@Slf4j
public class FileHelper {

    private FileHelper() {}

    public static String readClassPathResourceFile(String file) {
        try {
            ClassPathResource resource = new ClassPathResource(file);
            File filePath = resource.getFile();
            return FileUtils.readFileToString(filePath, "UTF-8");
        } catch (Exception e) {
            log.error("readClassPathResourceFile-error", e);
            return null;
        }
    }
}
