package org.example.vitasoft.DTO;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class RequestDTO {
    @NotBlank(message = "Текст заявки не может быть пустым")
    private String text;
}

