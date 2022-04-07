package ru.arlabs.taskservice.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.arlabs.taskservice.project.request.NewProject;
import ru.arlabs.taskservice.project.request.UpdateProject;
import ru.arlabs.taskservice.project.response.FavoriteInfo;
import ru.arlabs.taskservice.project.response.ProjectSummaryInfo;
import ru.arlabs.taskservice.security.request.AuthorizedUser;

/**
 * @author Jeb
 */
@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public Page<ProjectSummaryInfo> getProjects(@AuthenticationPrincipal AuthorizedUser user,
                                                @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return projectService.getUserProjects(user.getId(), pageable);
    }

    @PostMapping
    public void createProject(@AuthenticationPrincipal AuthorizedUser user, @RequestBody NewProject newProject) {
        projectService.createProject(user.getId(), newProject);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@AuthenticationPrincipal AuthorizedUser user, @PathVariable long id) {
        projectService.deleteProject(user.getId(), id);
    }

    @PutMapping("/{id}")
    public void updateProject(@AuthenticationPrincipal AuthorizedUser user, @PathVariable long id, @RequestBody UpdateProject updateProject) {
        projectService.updateProject(user.getId(), id, updateProject);
    }

    @PostMapping("/{id}/favorite")
    public FavoriteInfo favoriteProject(@AuthenticationPrincipal AuthorizedUser user, @PathVariable long id) {
        return new FavoriteInfo(projectService.favoriteProject(user.getId(), id));
    }

    @PostMapping("/{id}/avatar")
    public void uploadAvatar(@AuthenticationPrincipal AuthorizedUser user, @PathVariable long id, @RequestParam("avatar") MultipartFile image) {
        projectService.updateAvatar(user.getId(), id, image);
    }
}
