package com.vinhdd.sbom.api.dto.queryout;

import lombok.Data;

@Data
public class UserNotInProjectQueryOut {
    private String id;
    private String username;
    private String email;
    private String fullname;
}
