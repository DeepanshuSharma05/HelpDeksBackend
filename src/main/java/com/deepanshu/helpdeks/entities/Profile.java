package com.deepanshu.helpdeks.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "profile")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // key take away every table will have a unique ID
    private Long id;

    @OneToOne
    @JoinColumn(name ="user_id", referencedColumnName="id", nullable = false)
    private User user;

//    [ Profile Object ]
//            ├── id           = null (not saved yet)
//            ├── fullName     = "Deepanshu Sharma"
//            ├── bio          = "Full-stack developer..."
//            └── user         ---> [ User Object (currentUser) ]
//            ├── id       = 7  <-- (Hibernate steals this ID!)
//            ├── username = "deepanshu"
//            └── password = "$2a$10$..."

//    Because user_id in the profile table is set to 7,
//    your application can later say: "Give me the profile belonging to User ID 7,"
//    and instantly fetch everything Deepanshu wrote!

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String bio;

    @Column(nullable = false)
    private String experience;

    @Column(nullable = false)
    private String location;

    @Column(name = "linkedin_profile", nullable= false)
    private String linkedinProfile;

    @Column(nullable = true,columnDefinition = "TEXT")
    private String skills;

}
