package com.fitnsport.server.utils;

import com.fitnsport.server.response.BaseResponse;
import org.springframework.stereotype.Service;

@Service
public class BaseResponseUtil {

    public static BaseResponse createSuccessBaseResponse() {
        return BaseResponse.builder()
                .es(0)
                .message("success")
                .status(200)
                .time(System.currentTimeMillis())
                .build();
    }

    public static BaseResponse createSuccessBaseResponseWithResults(Object results) {
        return BaseResponse.builder()
                .es(0)
                .message("success")
                .status(200)
                .results(results)
                .time(System.currentTimeMillis())
                .build();
    }

    public static BaseResponse createErrorBaseResponse() {
        return BaseResponse.builder()
                .es(1)
                .message("failed!")
                .status(400)
                .time(System.currentTimeMillis())
                .build();
    }

    public static BaseResponse createErrorBaseResponse(String message) {
        return BaseResponse.builder()
                .es(1)
                .message(message)
                .status(400)
                .time(System.currentTimeMillis())
                .build();
    }

    public static BaseResponse createNoDataBaseResponse() {
        return BaseResponse.builder()
                .es(2)
                .message("No data found!")
                .status(300)
                .time(System.currentTimeMillis())
                .build();
    }

}
