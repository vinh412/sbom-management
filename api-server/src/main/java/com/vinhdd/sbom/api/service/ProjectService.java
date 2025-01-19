package com.vinhdd.sbom.api.service;

import com.vinhdd.sbom.api.dto.ProjectDTO;
import com.vinhdd.sbom.api.dto.ProjectWithPipelinesDTO;
import com.vinhdd.sbom.api.dto.in.ProjectDtoIn;
import com.vinhdd.sbom.api.dto.out.MembershipDto;
import com.vinhdd.sbom.api.dto.queryout.UserNotInProjectQueryOut;

import java.util.List;

public interface ProjectService {
    List<ProjectDTO> getAllProjects();
    ProjectWithPipelinesDTO getProjectByName(String name);
    ProjectDTO createProject(ProjectDtoIn projectDtoIn);
    ProjectDTO updateProject(String projectId, ProjectDtoIn projectDtoIn);
    void deleteProject(String id);
    List<MembershipDto> getAllMembers(String projectName);
    void addMember(String projectName, String userId, Boolean isAdmin, List<String> pipelineIds);
    void removeMember(String projectId, String userId);
    void updateMemberPipeline(String projectId, String userId, Boolean isAdmin, List<String> pipelines);
    List<UserNotInProjectQueryOut> getAllUsersNotInProject(String projectName);
    MembershipDto getMembership(String projectName);
}
