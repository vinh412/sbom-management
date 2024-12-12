package com.vinhdd.sbom.api.dto.out;

import com.vinhdd.sbom.api.util.helper.Col;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DependencyDtoOut {
    private String ref;
    @Col(value = "depends_on", jsonString = true)
    private List<String> dependsOn;
}
