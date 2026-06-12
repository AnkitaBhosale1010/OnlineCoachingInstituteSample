package com.coaching.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "teacher")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Long teacherId;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String phone;

    private String qualification;

    private String expertise;

    @Column(name = " join_date")
    private LocalDate joinDate;
    
    @OneToMany(mappedBy = "teacher",cascade = CascadeType.ALL)
    private List<Course> courses;
    
}
