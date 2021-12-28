package org.springframework.samples.petclinic.todo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter @Getter
@ApiModel("리퀘스트DTO")
public class RequestDto {
    private long id ;
    @ApiModelProperty("사용자이름")
    private String username;

    @NotNull
    @Size(min = 10, max = 50, message = "10자 이상 입력하세요...")
    @ApiModelProperty("설명")
    private String description;

    //@NotNull
    @ApiModelProperty("완료일자")
    private Date targetDate;

    public Todo toEntity() {
        Todo todo = new Todo();
        todo.setId(this.id);
        todo.setUsername(this.username);
        todo.setDescription(this.description);
        todo.setTargetDate(this.targetDate);

        return todo;
    }

}

