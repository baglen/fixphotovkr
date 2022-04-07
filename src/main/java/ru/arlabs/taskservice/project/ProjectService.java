package ru.arlabs.taskservice.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.arlabs.taskservice.project.request.NewProject;
import ru.arlabs.taskservice.project.request.UpdateProject;
import ru.arlabs.taskservice.project.response.ProjectSummaryInfo;

/**
 * @author Jeb
 */
public interface ProjectService {
    Page<ProjectSummaryInfo> getUserProjects(long userId, Pageable pageable);

    @Transactional
    void createProject(long userId, NewProject newProject);

    void deleteProject(long userId, long projectId);

    void updateProject(long userId, long projectId, UpdateProject updateProject);

    @Transactional
    boolean favoriteProject(long userId, long projectId);

    @Transactional
    void updateAvatar(long userId, long projectId, MultipartFile file);
}
