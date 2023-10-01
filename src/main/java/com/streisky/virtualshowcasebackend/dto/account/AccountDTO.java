package com.streisky.virtualshowcasebackend.dto.account;

import jakarta.validation.constraints.NotBlank;

public record AccountDTO(
        @NotBlank
        String login,
        @NotBlank
        String password
) {
}
