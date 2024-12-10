package com.vinhdd.sbom.api.dto.in;

import lombok.Data;

@Data
public class PageRequestDtoIn {
    private int page;
    private int size;
    private String search;
    private String order;
    private String sortBy;
}
