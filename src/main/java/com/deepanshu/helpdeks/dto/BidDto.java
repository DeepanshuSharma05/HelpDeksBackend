package com.deepanshu.helpdeks.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class BidDto {

    @NotNull(message = "amount cannot be null")
    private Double amount;

}
