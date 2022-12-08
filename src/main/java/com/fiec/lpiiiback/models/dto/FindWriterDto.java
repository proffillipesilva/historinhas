package com.fiec.lpiiiback.models.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FindWriterDto {
    @NotNull
    private String email;
}
