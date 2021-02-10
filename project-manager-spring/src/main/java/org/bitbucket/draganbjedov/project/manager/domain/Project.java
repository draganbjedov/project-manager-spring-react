package org.bitbucket.draganbjedov.project.manager.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "Project name cannot be blank")
    private String name;

    @Column(updatable = false, unique = true)
    @NotBlank(message = "Project identifier cannot be blank")
    @Size(min = 4, max = 5, message = "Project identifier must be 4 or 5 characters long")
    private String identifier;

    @Column
    @NotBlank(message = "Project description cannot be blank")
    private String description;

    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "project"
    )
    @JsonIgnore
    private Backlog backlog;

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-DD")
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-DD")
    private Date endDate;

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

//    @PrePersist
//    protected void onCreate() {
//        createdDate = new Date();
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        updatedDate = new Date();
//    }

}
