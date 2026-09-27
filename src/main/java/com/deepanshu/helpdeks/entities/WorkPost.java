package com.deepanshu.helpdeks.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "work_post")
public class WorkPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name ="skills_required")
    private String skillsRequired;

    @Column(name = "maximum_pay", nullable = false)
    private String maximumPay;

    @Column(name = "minimum_pay", nullable = false)
    private String minimumPay;

    private String filepath;


    //mapping it to user , one user can have multiple work posts

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id", nullable = false)
    private User user;


}
