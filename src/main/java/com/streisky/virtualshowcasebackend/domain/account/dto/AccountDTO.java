package com.streisky.virtualshowcasebackend.domain.account.dto;

import jakarta.validation.constraints.NotBlank;

public record AccountDTO(
        @NotBlank
        String login,
        @NotBlank
        String password
) {
}
