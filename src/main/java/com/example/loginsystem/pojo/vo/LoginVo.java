package com.example.loginsystem.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class LoginVo implements Serializable {

    private String user_id;
    private List<String> permissions;
}
