package com.examples.SpringBatchSample.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagerDTO<T> {

    private Collection<T> collection;
    private Integer totalElements;
    private Integer totalPages;
    private Long totalRecords;
    private Integer currentPage;

    public PagerDTO(Page<T> t) {
        this.currentPage =  t.getNumber();
        this.collection = t.getContent();
        this.totalElements = t.getNumberOfElements();
        this.totalPages = t.getTotalPages();
        this.totalRecords = t.getTotalElements();
    }

}
