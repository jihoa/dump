package org.springframework.samples.petclinic.sample;

import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Todo {
    private long id;
    private String username;
    private String description;
    private Date targetDate;
    private boolean isDone;

    public Todo(long id, String username, String description, Date targetDate) {
        this.id = id;
        this.username = username;
        this.description = description;
        this.targetDate = targetDate;
        this.isDone = false;
    }

}
