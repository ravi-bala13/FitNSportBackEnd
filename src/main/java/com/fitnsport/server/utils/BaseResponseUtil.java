package com.fitnsport.server.utils;

import com.fitnsport.server.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BaseResponseUtil {

    public static ResponseEntity<BaseResponse> createBaseResponse(String message, HttpStatus httpStatus){
        BaseResponse response = BaseResponse.builder()
                .es(0)
                .message(message)
                .status(httpStatus.value())
                .time(System.currentTimeMillis())
                .build();
        return ResponseEntity.status(httpStatus).body(response);
    }
    public static ResponseEntity<BaseResponse> createSuccessBaseResponse(Object response){

        BaseResponse baseResponse = BaseResponse.builder()
                .es(0)
                .message("success")
                .status(HttpStatus.OK.value())
                .results(response)
                .time(System.currentTimeMillis())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
    }

    public static ResponseEntity<BaseResponse> createSuccessBaseResponse(){

        BaseResponse baseResponse = BaseResponse.builder()
                .es(0)
                .message("success")
                .status(HttpStatus.OK.value())
                .time(System.currentTimeMillis())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
    }

    public static ResponseEntity<BaseResponse> createNoDataBaseResponse(){

        BaseResponse baseResponse = BaseResponse.builder()
                .es(1)
                .message("No data found!")
                .status(404)
                .time(System.currentTimeMillis())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(baseResponse);
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

    public static BaseResponse createErrorBaseResponse(String message) {
        return BaseResponse.builder()
                .es(1)
                .message(message)
                .status(400)
                .time(System.currentTimeMillis())
                .build();
    }

    public static BaseResponse createNoDataBaseResponse1() {
        return BaseResponse.builder()
                .es(1)
                .message("No data found!")
                .status(300)
                .time(System.currentTimeMillis())
                .build();
    }

}
