package ca.keyin.flight_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AirportDtos {
    public record CreateOrUpdate(
            @NotBlank @Size(max = 150) String name,
            @NotBlank @Size(max = 8) String code,
            @NotNull Long cityId
    ) {}

    public record AirportResp(
            Long id, String name, String code, Long cityId
    ) {}
}
