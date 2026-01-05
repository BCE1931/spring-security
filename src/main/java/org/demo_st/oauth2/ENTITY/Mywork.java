package org.demo_st.oauth2.ENTITY;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mywork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String question;

    private Boolean important ;

    @Column(columnDefinition = "TEXT")
    private String logic;

    @Column(columnDefinition = "TEXT")
    private String code;

    @Column(columnDefinition = "TEXT")
    private String questioninfo;

    private Boolean attempted;

    private Boolean work;

    private String topic;

    private String subtopic;

    private String username;

    @Temporal(TemporalType.DATE)
    private Date date;

    private String link;

    private String email;

    private String level;

    public Boolean isImportant() {
        return important;
    }

    public Boolean isAttempted() {
        return attempted;
    }
}

