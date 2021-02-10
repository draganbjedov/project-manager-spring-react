package org.bitbucket.draganbjedov.project.manager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Backlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "task_sequence")
    private int taskSequence = 1;

    @Column(name = "project_identifier")
    private String projectIdentifier;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private Project project;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "backlog"
    )
    private List<Task> tasks = new ArrayList<>();

    public void addTask(Task task) {
        if (tasks == null)
            tasks = new ArrayList<>();
        task.setBacklog(this);
        tasks.add(task);
    }
}
