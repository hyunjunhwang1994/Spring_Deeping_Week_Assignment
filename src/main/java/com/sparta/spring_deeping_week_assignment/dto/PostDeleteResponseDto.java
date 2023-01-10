package com.sparta.spring_deeping_week_assignment.dto;

import lombok.Data;


@Data
public class PostDeleteResponseDto {


    private int status;
    private String message;
    private Object data;
    public PostDeleteResponseDto(int status, String message,Object data) {
        this.status = status;
        this.message = message;

        this.data = data;
    }

}
