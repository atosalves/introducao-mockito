package br.edu.ifpb.taskmanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import br.edu.ifpb.taskmanagement.model.entity.TaskEntity;
import br.edu.ifpb.taskmanagement.model.repository.TaskRepository;

@SpringBootTest
public class TaskServiceTest {

        @MockitoBean
        TaskRepository taskRepository;

        @Autowired
        TaskService taskService;

        @Test
        void testCreateTask() {
                TaskEntity task = new TaskEntity();
                task.setTitle("task title");
                task.setDescription("task description");
                task.setCompleted(false);

                when(taskRepository.save(any(TaskEntity.class))).thenAnswer(invoker -> invoker.getArgument(0));

                TaskEntity createdTask = taskService.createTask(task);

                assertNotNull(createdTask);
                assertEquals("task title", createdTask.getTitle());

                verify(taskRepository).save(task);
        }

}
