package com.vinhdd.sbom.api.dto.out;

import com.vinhdd.sbom.api.dto.queryout.ComponentDtoQueryOut;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CompareBuildDto {
    private List<ComponentDtoQueryOut> insertedComponents = new ArrayList<>();
    private List<ComponentDtoQueryOut> removedComponents = new ArrayList<>();
    private List<ComponentDtoQueryOut> upgradedComponents = new ArrayList<>();
    private List<ComponentDtoQueryOut> downgradedComponents = new ArrayList<>();
    private List<ComponentDtoQueryOut> unchangedComponents = new ArrayList<>();
}
