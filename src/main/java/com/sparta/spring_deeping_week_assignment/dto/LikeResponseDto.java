package com.sparta.spring_deeping_week_assignment.dto;

import lombok.Data;

@Data
public class LikeResponseDto {


    private int status;
    private String message;
    private Object data;


    public LikeResponseDto(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

}
