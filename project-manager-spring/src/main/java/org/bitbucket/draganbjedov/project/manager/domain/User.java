package org.bitbucket.draganbjedov.project.manager.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "projmng_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Email(message = "Username must be valid email address")
    @NotBlank(message = "Username cannot be blank")
    @Column(unique = true, updatable = false, nullable = false)
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Column
    private String password;

    @NotBlank(message = "Confirm password cannot be blank")
    @Transient
    private String confirmPassword;

    @NotBlank(message = "Full name cannot be blank")
    @Column
    private String fullName;

    @OneToMany(
            cascade = CascadeType.REFRESH,
            fetch = FetchType.EAGER,
            mappedBy = "user",
            orphanRemoval = true
    )
    private List<Project> projects;

    @Column(name = "created_date", updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Berlin")
    private Date createdDate;

    @Column(name = "updated_date")
    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Berlin")
    private Date updatedDate;

    public void addProject(Project project) {
        if (projects == null)
            projects = new ArrayList<>();
        project.setUser(this);
        projects.add(project);
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = new Date();
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
