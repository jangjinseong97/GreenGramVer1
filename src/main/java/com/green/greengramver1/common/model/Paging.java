package com.green.greengramver1.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class Paging {
    private final static int DEFAULT_PAGE_SIZE = 20;
    @Schema(example = "1", description = "Selected Page")
    private int page;
    @Schema(example = "20", description = "item count per page")
    private int size;
    @JsonIgnore
    private int sIdx;

    public Paging(Integer page, Integer size) {
//        if(size == null || size<=0){
//            size = 20;
//        }
//        if(page == null || page < 1){
//            page = 1;
//        }
//        this.page = page;
//        this.size = size;
//        this.sIdx = (page -1)*size;

        this.page = (page == null || page < 1) ? 1 : page;
        this.size = (size == null || size <= 0) ? DEFAULT_PAGE_SIZE : size;
        this.sIdx = (this.page -1)*this.size;
    }
}
