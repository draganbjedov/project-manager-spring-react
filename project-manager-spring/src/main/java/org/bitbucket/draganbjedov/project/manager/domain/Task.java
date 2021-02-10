package org.bitbucket.draganbjedov.project.manager.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotBlank(message = "Task summary cannot be blank")
    private String summary;

    @Column(name = "acceptance_criteria")
    private String acceptanceCriteria;

    @Column
    private String status;

    @Column
    private Integer priority;

    @Column(name = "project_sequence", updatable = false)
    private String projectSequence;

    @Column(name = "project_identifier", updatable = false)
    @NotBlank(message = "Project identifier cannot be blank")
    private String projectIdentifier;

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.REFRESH
    )
    @JoinColumn(name = "backlog_id", updatable = false, nullable = false)
    @JsonIgnore
    private Backlog backlog;

    @Column(name = "due_date")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-DD")
    private Date dueDate;

    @Column(name = "created_date", updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-DD")
    private Date createdDate;

    @Column(name = "updated_date")
    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-DD")
    private Date updatedDate;

}
