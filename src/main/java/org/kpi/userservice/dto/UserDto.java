package org.kpi.userservice.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserDto (UUID id, String name, String email) {}
