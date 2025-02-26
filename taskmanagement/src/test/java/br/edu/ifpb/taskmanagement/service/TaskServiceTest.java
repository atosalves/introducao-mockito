package br.edu.ifpb.taskmanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

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

        @Test
        void testGetTaskById() {
                TaskEntity task = new TaskEntity();
                task.setId(1L);
                task.setTitle("task title");
                task.setDescription("task description");
                task.setCompleted(false);

                when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

                TaskEntity foundTask = taskService.getTaskById(1L);

                assertNotNull(foundTask);
                assertEquals("task title", foundTask.getTitle());

                verify(taskRepository).findById(1L);
        }

        @Test
        void testGetTaskByIdNotFound() {
                when(taskRepository.findById(1L)).thenReturn(Optional.empty());

                assertThrows(RuntimeException.class, () -> taskService.getTaskById(1L));

                verify(taskRepository).findById(1L);
        }

        @Test
        void testGetAllTasks() {
                TaskEntity task1 = new TaskEntity();
                task1.setId(1L);
                task1.setTitle("task title 1");
                task1.setDescription("task description 1");
                task1.setCompleted(false);

                TaskEntity task2 = new TaskEntity();
                task2.setId(2L);
                task2.setTitle("task title 2");
                task2.setDescription("task description 2");
                task2.setCompleted(true);

                when(taskRepository.findAll()).thenReturn(List.of(task1, task2));

                List<TaskEntity> tasks = taskService.getAllTasks();

                assertNotNull(tasks);
                assertEquals(2, tasks.size());

                verify(taskRepository).findAll();
        }

}
