package ru.arlabs.taskservice.label;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.arlabs.taskservice.label.request.NewLabel;
import ru.arlabs.taskservice.label.request.UpdateLabel;
import ru.arlabs.taskservice.label.response.LabelInfo;

import java.util.List;

/**
 * @author Jeb
 */
@RestController
@RequestMapping("/projects/{projectId}/labels")
public class LabelController {
    private final LabelService labelService;

    @Autowired
    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping
    public List<LabelInfo> getLabels(@PathVariable long projectId) {
        return labelService.getLabels(projectId);
    }

    @PostMapping
    public void createLabel(@PathVariable long projectId, @RequestBody NewLabel newLabel) {
        labelService.createLabel(projectId, newLabel);
    }

    @DeleteMapping("/{id}")
    public void deleteLabel(@PathVariable long projectId, @PathVariable long id) {
        labelService.deleteLabel(projectId, id);
    }

    @PutMapping("/{id}")
    public void updateLabel(@PathVariable long projectId, @PathVariable long id, @RequestBody UpdateLabel updateLabel) {
        labelService.updateLabel(projectId, id, updateLabel);
    }
}
