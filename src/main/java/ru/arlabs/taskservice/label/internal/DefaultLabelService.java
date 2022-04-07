package ru.arlabs.taskservice.label.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.arlabs.taskservice.label.LabelService;
import ru.arlabs.taskservice.label.converter.LabelConverter;
import ru.arlabs.taskservice.label.data.LabelRepository;
import ru.arlabs.taskservice.project.data.ProjectRepository;
import ru.arlabs.taskservice.label.model.Label;
import ru.arlabs.taskservice.project.model.Project;
import ru.arlabs.taskservice.label.request.NewLabel;
import ru.arlabs.taskservice.label.request.UpdateLabel;
import ru.arlabs.taskservice.label.response.LabelInfo;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jeb
 */
@Service
public class DefaultLabelService implements LabelService {
    private final LabelRepository labelRepository;
    private final LabelConverter converter;
    private final ProjectRepository projectRepository;

    @Autowired
    public DefaultLabelService(LabelRepository labelRepository,
                               LabelConverter converter,
                               ProjectRepository projectRepository) {
        this.labelRepository = labelRepository;
        this.converter = converter;
        this.projectRepository = projectRepository;
    }

    @Override
    @Transactional
    public List<LabelInfo> getLabels(long projectId) {
        return labelRepository.findAllByProject_Id(projectId)
                .stream()
                .map(converter::convertToInfo)
                .collect(Collectors.toList());
    }

    @Override
    public void createLabel(long projectId, NewLabel newLabel) {
        final Label label = converter.convertNew(newLabel);
        final Project project = projectRepository.findById(projectId)
                .orElseThrow(EntityNotFoundException::new);
        label.setProject(project);
        labelRepository.save(label);
    }

    @Override
    public void deleteLabel(long projectId, long labelId) {
        labelRepository.deleteByIdAndProject_Id(labelId, projectId);
    }

    @Override
    public void updateLabel(long projectId, long labelId, UpdateLabel updateLabel) {
        final Label label = labelRepository.findByIdAndProject_Id(labelId, projectId)
                .orElseThrow(EntityNotFoundException::new);
        converter.update(updateLabel, label);
        labelRepository.save(label);
    }
}
