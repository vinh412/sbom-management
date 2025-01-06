package com.vinhdd.sbom.api.dto.out;

import com.vinhdd.sbom.api.dto.PipelineDTO;
import com.vinhdd.sbom.api.model.Membership;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MembershipDto {
    private String id;
    private String username;
    private String fullname;
    private String email;
    private String phoneNumber;
    private String description;
    private Integer status;
    private Boolean isAdmin;
    private List<PipelineDTO> pipelines;

    public static MembershipDto fromMembership(Membership membership){
        return MembershipDto.builder()
                .id(membership.getUser().getId())
                .username(membership.getUser().getUsername())
                .fullname(membership.getUser().getFullname())
                .email(membership.getUser().getEmail())
                .phoneNumber(membership.getUser().getPhoneNumber())
                .description(membership.getUser().getDescription())
                .status(membership.getUser().getStatus())
                .isAdmin(membership.getIsAdmin())
                .pipelines(membership.getPipelines().stream().map(PipelineDTO::from).toList())
                .build();
    }
}
