package ru.dinislam.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dinislam.server.model.Bill;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private String message;
    private Bill payload;
}
