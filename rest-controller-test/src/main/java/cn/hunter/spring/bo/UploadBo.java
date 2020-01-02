package cn.hunter.spring.bo;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@EqualsAndHashCode
public class UploadBo {

    private MultipartFile file;

}
