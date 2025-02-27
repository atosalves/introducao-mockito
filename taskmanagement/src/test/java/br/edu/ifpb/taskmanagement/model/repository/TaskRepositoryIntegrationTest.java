package br.edu.ifpb.taskmanagement.model.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import br.edu.ifpb.taskmanagement.model.entity.TaskEntity;

@DataJpaTest
@ActiveProfiles("test")
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaskRepositoryIntegrationTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    TaskRepository taskRepository;

    @Test
    void testPersistInDatabase(){
        var task = new TaskEntity();
        task.setTitle("task title");
        task.setDescription("task description");
        task.setCompleted(false);

        var savedTask = taskRepository.save(task);

        assertNotNull(savedTask.getId());
        assertEquals(task.getTitle(), "task title");

        assertFalse(savedTask.isCompleted());
    }

    @Test
    void testFindTaskByIdInDatabase(){
        var task = new TaskEntity();
        task.setTitle("task title");
        task.setDescription("task description");
        task.setCompleted(false);

        var savedTask = testEntityManager.persist(task);

        var opTask = taskRepository.findById(savedTask.getId());

        assertNotNull(opTask.get());
        assertEquals(savedTask, opTask.get());
    }

}
