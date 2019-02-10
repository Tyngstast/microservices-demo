package com.henrj.liquorstore.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiRequestEvent {
    private String user;
    private String url;
    private String method;
    private Integer statusCode;
    private Date date;
}
