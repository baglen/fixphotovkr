package ru.arlabs.taskservice.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.arlabs.taskservice.comment.response.AttachmentFile;
import ru.arlabs.taskservice.comment.response.AttachmentInfo;
import ru.arlabs.taskservice.task.TaskService;

import java.util.Base64;

@RestController
@RequestMapping("/attachments")
public class AttachmentController {
    private final CommentService commentService;

    @Autowired
    public AttachmentController(CommentService commentService) {
        this.commentService = commentService;
    }

   @GetMapping("/{address}")
    public ResponseEntity<Resource> getAttachment(@PathVariable String address, @RequestParam(value = "download", defaultValue = "false") boolean download) {
       final AttachmentFile info = commentService.getAttachment(address);

       HttpHeaders headers = new HttpHeaders();
       headers.add("Content-Type", info.getType());
       if (download) {
           headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", info.getFilename()));
       }

       return ResponseEntity
               .status(HttpStatus.OK)
               .headers(headers)
               .body(new FileSystemResource(info.getPath()));
   }
}
