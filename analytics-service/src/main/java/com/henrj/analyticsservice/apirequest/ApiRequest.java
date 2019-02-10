package com.henrj.analyticsservice.apirequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiRequest {

    @Id
    private String id;
    private String user;
    private String url;
    private String method;
    private Integer statusCode;
    private Date date;
}
