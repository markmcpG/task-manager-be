package com.example.taskmanager.tasks;

import com.example.taskmanager.categories.Category;
import com.example.taskmanager.status.State;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TaskRepositoryBean implements TaskRepository {

    private static final List<Task> taskList = new ArrayList<>();

//    static {
//        taskList.add(new Task("task 1", "task 1 description", "cat 1", "status 1", "task559ffa0a-5bb7-4207-a14e-93090623dec1",false));
//        taskList.add(new Task("task 2", "task 2 description", "cat 1", "status 2", "task559ffa0a-5bb7-4207-a14e-93090623dec2",false));
//        taskList.add(new Task("task 3", "task 3 description", "cat 1", "status 3", "task559ffa0a-5bb7-4207-a14e-93090623dec3",true));
//    }

    static {
        taskList.add(new Task("task 1", "task 1 description", new Category("cat 1"), new State("status 1"), "task559ffa0a-5bb7-4207-a14e-93090623dec1",false));
        taskList.add(new Task("task 2", "task 2 description", new Category("cat 2"), new State("status 2"), "task559ffa0a-5bb7-4207-a14e-93090623dec2",false));
        taskList.add(new Task("task 3", "task 3 description", new Category("cat 3"), new State("status 3"), "task559ffa0a-5bb7-4207-a14e-93090623dec3",true));
    }

    @Override
    public Collection<Task> findAll() {
        //return taskList;
        return taskList.stream().filter(task -> !task.isDeleted()).collect(Collectors.toList());
    }

    @Override
    public Task save(Task task) {
        //return task;
        taskList.add(task);
        return task;
    }

    @Override
    public Optional<Task> findOne(Example<Task> of) {
        //return Optional.ofNullable(taskList.get(0));
        return Optional.ofNullable(findOneByUuid(of.getProbe().getUuid()));
    }

    @Override
    public void delete(Task task) {
        task.setDeleted(true);
    }

    @Override
    public Task findOneByUuid(String uuid) {
        //return new Task("Task 1", "description", "category", "status", uuid,false);
        Task searched = null;
        for (Task task : taskList) {
            if (task.getUuid().equals(uuid)) {
                searched = task;
            }
        }
        return searched;
    }
}
