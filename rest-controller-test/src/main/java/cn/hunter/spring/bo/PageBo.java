package cn.hunter.spring.bo;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PageBo<T> implements Serializable {

    private int current;

    private int size;

    private long total;

    private int pages;

    private List<T> records;
}
