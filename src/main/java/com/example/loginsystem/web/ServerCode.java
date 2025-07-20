package com.example.loginsystem.web;

public class ServerCode {

    // business error codes
    public static final Integer OK = 20000;

    // error format for request
    public static final Integer ERR_BAD_REQUEST = 40000;


    // JWT data error
    public static final Integer ERR_JWT_INVALID = 40001;

    // JWT expired
    public static final Integer ERR_JWT_EXPIRED = 40300;

    // data not found
    public static final Integer ERR_NOT_FOUND = 40400;


    // data exceed
    public static final Integer ERR_EXCEED = 40900;


    // permission deny
    public static final Integer ERR_PERMISSION_DENIED = 41000;

    public static final Integer ERR_USER_HAS_BEEN_LOGG = 41001;


    // insert error
    public static final Integer ERR_INSERT = 50000;

    // delete operation error
    public static final Integer ERR_DELETE = 50001;

    // update operation error
    public static final Integer ERR_UPDATE = 50002;




    // unknown error
    public static final Integer ERR_UNKNOWN = 59999;

    public static final Integer ERR_TEACHER_CODE_HAS_BEEN_USED = 60000;


}
