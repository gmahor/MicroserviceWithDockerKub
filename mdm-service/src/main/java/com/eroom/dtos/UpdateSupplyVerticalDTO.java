package com.eroom.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateSupplyVerticalDTO {

    @Min(value = 1, message = "Id Is Required.")
    private Long id;

    @NotBlank(message = "Supply Vertical Is Required.")
    private String supplyVertical;

}