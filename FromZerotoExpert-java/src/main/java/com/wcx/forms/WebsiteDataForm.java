package com.wcx.forms;


import lombok.Data;

import java.util.Date;

@Data
public class WebsiteDataForm {

    private String date;
    private Integer ip;
    private Integer uv;
    private Integer pv;
}
