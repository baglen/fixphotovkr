package ru.arlabs.taskservice.label;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import ru.arlabs.taskservice.label.request.NewLabel;
import ru.arlabs.taskservice.label.request.UpdateLabel;
import ru.arlabs.taskservice.label.response.LabelInfo;

import java.util.List;

/**
 * @author Jeb
 */
public interface LabelService {
    @PreAuthorize("@permissionService.canRead(authentication.principal.id, #projectId)")
    List<LabelInfo> getLabels(long projectId);

    @Transactional
    @PreAuthorize("@permissionService.canWrite(authentication.principal.id, #projectId)")
    void createLabel(long projectId, NewLabel newProject);

    @PreAuthorize("@permissionService.canWrite(authentication.principal.id, #projectId)")
    void deleteLabel(long projectId, long labelId);

    @PreAuthorize("@permissionService.canWrite(authentication.principal.id, #projectId)")
    void updateLabel(long projectId, long labelId, UpdateLabel updateProject);
}
