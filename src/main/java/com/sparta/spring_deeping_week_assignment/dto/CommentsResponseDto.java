package com.sparta.spring_deeping_week_assignment.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
@Builder
public class CommentsResponseDto<T> {


    private int statusCode;
    private String responseMessage;
    private T data;


    public CommentsResponseDto(int statusCode, String responseMessage) {
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
        this.data =null;

    }


    public static <T> CommentsResponseDto<T> responseDto(final int statusCode, final String responseMessage) {

        return responseDto(statusCode, responseMessage, null);
    }

    public static <T> CommentsResponseDto<T> responseDto(final int statusCode, final String responseMessage, final T t) {
        return CommentsResponseDto.<T>builder()
                .data(t)
                .statusCode(statusCode)
                .responseMessage(responseMessage)
                .build();
    }

}
