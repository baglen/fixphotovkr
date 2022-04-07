package ru.arlabs.taskservice.comment.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.arlabs.taskservice.comment.model.Attachment;
import ru.arlabs.taskservice.comment.model.Comment;
import ru.arlabs.taskservice.comment.response.CommentInfo;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CommentConverter {
    @Mappings({
            @Mapping(target = "author.username", source = "sender.username"),
            @Mapping(target = "author.email", source = "sender.email"),
    })
    CommentInfo convertInfo(Comment comment);

}
