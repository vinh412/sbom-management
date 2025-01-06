package com.vinhdd.sbom.api.service.impl;

import com.vinhdd.sbom.api.dto.ProjectDTO;
import com.vinhdd.sbom.api.dto.ProjectWithPipelinesDTO;
import com.vinhdd.sbom.api.dto.in.ProjectDtoIn;
import com.vinhdd.sbom.api.dto.out.MembershipDto;
import com.vinhdd.sbom.api.exception.NotFoundException;
import com.vinhdd.sbom.api.model.CustomUserDetails;
import com.vinhdd.sbom.api.model.Membership;
import com.vinhdd.sbom.api.model.Project;
import com.vinhdd.sbom.api.model.User;
import com.vinhdd.sbom.api.repository.*;
import com.vinhdd.sbom.api.service.AuthService;
import com.vinhdd.sbom.api.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;
    private final PipelineRepository pipelineRepository;
    private final AuthService authService;

    @Override
    @Transactional
    public List<ProjectDTO> getAllProjects() {
        if (authService.hasAnyRole("SYS_ADMIN")) {
            return projectRepository.findAll().stream().map(ProjectDTO::from).toList();
        } else {
            User user = userRepository.findByUsername(authService.getCurrentUser().getUsername());
            return user.getProjects().stream().map(ProjectDTO::from).toList();
        }
    }

    @Override
    @Transactional
    public ProjectWithPipelinesDTO getProjectByName(String name) {
        if (authService.hasAnyRole("SYS_ADMIN")) {
            Project project = projectRepository.findByName(name).orElseThrow(() -> new NotFoundException("Project not found"));
            return ProjectWithPipelinesDTO.from(project);
        } else {
            CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByUsername(customUserDetails.getUsername());
            Project project = user.getProjects().stream().filter(p -> p.getName().equals(name)).findFirst().orElseThrow(() -> new NotFoundException("Project not found"));
            return ProjectWithPipelinesDTO.from(project);
        }
    }

    @Override
    public ProjectDTO createProject(ProjectDtoIn projectDtoIn) {
        Project project = new Project();
        project.setName(projectDtoIn.getName());
        project.setDescription(projectDtoIn.getDescription());
        Membership membership = new Membership();
        membership.setProject(project);
        membership.setIsAdmin(true);
        membership.setUser(userRepository.findByUsername(authService.getCurrentUser().getUsername()));
        membershipRepository.save(membership);
        return ProjectDTO.from(projectRepository.save(project));
    }

    @Override
    public ProjectDTO updateProject(String projectId, ProjectDtoIn projectDtoIn) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException("Project not found"));
        if (authService.hasAnyRole("SYS_ADMIN") || authService.isProjectAdmin(projectId)) {
            project.setName(projectDtoIn.getName());
            project.setDescription(projectDtoIn.getDescription());
        } else {
            throw new NotFoundException("Project not found");
        }
        return ProjectDTO.from(projectRepository.save(project));
    }

    @Override
    public void deleteProject(String id) {
        if(!authService.hasAnyRole("SYS_ADMIN") && !authService.isProjectAdmin(id)) {
            throw new NotFoundException("Project not found");
        }
        projectRepository.deleteById(id);
    }

    @Override
    public List<MembershipDto> getAllMembers(String projectName) {
        Project project = projectRepository.findByName(projectName).orElseThrow(() -> new NotFoundException("Project not found"));
        if (!authService.hasAnyRole("SYS_ADMIN") && !authService.isMember(project.getId())) {
            throw new NotFoundException("Project not found");
        }
        List<Membership> memberships = membershipRepository.findAllByProjectName(projectName);
        return memberships.stream().map(MembershipDto::fromMembership).toList();
    }

    @Override
    @Transactional
    public void addMember(String projectId, String userId, Boolean isAdmin, List<String> pipelineIds) {
        if(!authService.hasAnyRole("SYS_ADMIN") && !authService.isProjectAdmin(projectId)) {
            throw new NotFoundException("Project not found");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException("Project not found"));
        Membership membership = new Membership();
        membership.setUser(user);
        membership.setProject(project);
        membership.setIsAdmin(isAdmin);
        membership.setPipelines(new HashSet<>(pipelineRepository.findAllById(pipelineIds)));
        membershipRepository.save(membership);
    }

    @Override
    public void removeMember(String projectId, String userId) {
        if(!authService.hasAnyRole("SYS_ADMIN") && !authService.isProjectAdmin(projectId)) {
            throw new NotFoundException("Project not found");
        }
        Membership membership = membershipRepository.findByProjectIdAndUserId(projectId, userId)
                .orElseThrow(() -> new NotFoundException("Membership not found"));
        membershipRepository.delete(membership);
    }

    @Override
    @Transactional
    public void updateMemberPipeline(String projectId, String userId, Boolean isAdmin, List<String> pipelineIds) {
        if(!authService.hasAnyRole("SYS_ADMIN") && !authService.isProjectAdmin(projectId)) {
            throw new NotFoundException("Project not found");
        }
        Membership membership = membershipRepository.findByProjectIdAndUserId(projectId, userId)
                .orElseThrow(() -> new NotFoundException("Membership not found"));
        if (pipelineIds != null) {
            membership.setPipelines(new HashSet<>(pipelineRepository.findAllById(pipelineIds)));
        }
        if (isAdmin != null) {
            membership.setIsAdmin(isAdmin);
        }
        membershipRepository.save(membership);
    }
}
