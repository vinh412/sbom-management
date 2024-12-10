package com.vinhdd.sbom.api.dto.queryout;

import com.vinhdd.sbom.api.util.helper.Col;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class BuildDtoQueryOut {
    @Col("id")
    private Long id;
    @Col("name")
    private String name;
    @Col("created_at")
    private Timestamp createdAt;
    @Col("sbom_id")
    private Long sbomId;
}
