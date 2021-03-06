package ru.dinislam.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dinislam.client.dto.enums.Operation;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    private Long sourceId;
    private Long targetId;
    private Operation operation;
    private Long value;
}
