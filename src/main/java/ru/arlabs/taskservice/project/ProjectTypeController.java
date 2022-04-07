package ru.arlabs.taskservice.project;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.arlabs.taskservice.project.request.NewProjectType;
import ru.arlabs.taskservice.project.request.UpdateProjectType;
import ru.arlabs.taskservice.project.response.ProjectTypeInfo;
import ru.arlabs.taskservice.security.request.AuthorizedUser;

import java.util.List;

/**
 * @author Jeb
 */
@RestController
@RequestMapping("/projectTypes")
public class ProjectTypeController {
    private final ProjectTypeService projectTypeService;

    public ProjectTypeController(ProjectTypeService projectTypeService) {
        this.projectTypeService = projectTypeService;
    }

    @GetMapping
    public List<ProjectTypeInfo> getProjectTypes(@AuthenticationPrincipal AuthorizedUser user) {
        return projectTypeService.findProjectTypes(user.getId());
    }

    @PostMapping
    public void createProjectType(@AuthenticationPrincipal AuthorizedUser user, @RequestBody NewProjectType newProjectType) {
        projectTypeService.createProjectType(user.getId(), newProjectType);
    }

    @PutMapping("/{id}")
    public void  updateProjectType(@AuthenticationPrincipal AuthorizedUser user,
                                  @PathVariable long id,
                                  @RequestBody UpdateProjectType updateProjectType) {
        projectTypeService.updateProjectType(user.getId(), id, updateProjectType);
    }

    @DeleteMapping("/{id}")
    public void  updateProjectType(@AuthenticationPrincipal AuthorizedUser user,
                                   @PathVariable long id) {
        projectTypeService.deleteProjectType(user.getId(), id);
    }
}
