package com.oneum20.study.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    private String password;

    private boolean emailVerified;

    private String emailCheckToken;

    private LocalDateTime joinedAt;


    /*
        profile field
    * */
    private String bio;

    private String url;

    private String occupation;

    private String liveAround;

    private String location;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String profileImage;

    private boolean studyCreatedByEmail;

    private boolean studyCreatedByWeb;

    private boolean studyEnrollmentResultByEmail;

    private boolean studyEnrollmentResultByWeb;

    private boolean studyUpdateByEmail;

    private boolean studyUpdateByWeb;
}
