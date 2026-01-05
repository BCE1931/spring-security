package org.demo_st.oauth2.ENTITY;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Hero {

    private List<Daily_work> daily_work;

    private Levels levelsRepo;

    private List<ReviseTopics> revise_topics;
}
