package ru.arlabs.taskservice;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.arlabs.taskservice.common.mapper.PasswordEncoderMapper;
import ru.arlabs.taskservice.task.data.TaskRepository;
import ru.arlabs.taskservice.task.model.Task;

class PhotoTaskServiceApplicationTests {

    private final PasswordEncoderMapper encoderMapper = new PasswordEncoderMapper(new BCryptPasswordEncoder());
    private final TaskRepository taskRepository;

    PhotoTaskServiceApplicationTests(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Test
    void testPasswordMatches() {
        String password = "P@ssw0rd";
        String encodedPassword = encoderMapper.encode(password);
        Assert.assertEquals( true, encoderMapper.matches(password, encodedPassword));
    }

    @Test
    void DeleteDataMethod(){
        boolean result = true;
        try {
            Task taskToDelete = taskRepository.getById(10L);
            taskRepository.deleteById(taskToDelete.getId());
        } catch (IllegalArgumentException exception){
            result = false;
        }
        Assert.assertTrue(result);
    }
}
