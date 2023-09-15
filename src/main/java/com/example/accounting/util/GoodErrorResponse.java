package com.example.accounting.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GoodErrorResponse {
    private String message;
    private long timestamp;

}
