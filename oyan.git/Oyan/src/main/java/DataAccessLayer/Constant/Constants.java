/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.Constant;

/**
 *
 * @author Eldar Hasanov
 */
public class Constants {

    // DATABASE CONNECTION TOOLS
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://10.50.3.176:3306/OYAN";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "freebsd";

    // NAME OF PROCEDURES IN MYSQL DATABASE
    public static final String LOGOUT = "LOGOUT";
    public static final String LOGIN = "LOGIN";
    public static final String REGISTRATION = "REGISTRATION";
    public static final String PHOTO_UPLOAD = "PHOTO_UPLOAD";
    public static final String GET_USER = "GET_USER";
    public static final String GET_USER_BY_TOKEN = "GET_USER_BY_TOKEN";
    public static final String UPDATE_USER = "UPDATE_USER";
    public static final String GET_USER_ID = "GET_USER_ID";
    public static final String UpdateOutgoingCall = "UpdateOutgoingCall";
    public static final String UpdateAcceptCall = "UpdateAcceptCall";
    public static final String UpdateIncomingCall = "UpdateIncomingCall";
    public static final String CHECK_USER_ID = "CHECK_USER_ID";
    public static final String ADD_ALARM_TIME = "ADD_ALARM_TIME";
    public static final String ADD_ALARM_WEEKDAY = "ADD_ALARM_WEEKDAY";
    public static final String UPDATE_ALARM_TIME = "UPDATE_ALARM_TIME";
    public static final String DELETE_ALARM = "DELETE_ALARM";
    public static final String GET_ALARM_TIME = "GET_ALARM_TIME";
    public static final String GET_ALARM_WEEKDAYS = "GET_ALARM_WEEKDAYS";
    public static final String GET_SLEEPIES = "GET_SLEEPIES";
    public static final String GET_NEAREST_ALARM = "GET_NEAREST_ALARM";

    public static final String GET_FRIENDS = "GET_FRIENDS";
    public static final String REFRESH_SLEEPIES = "REFRESH_SLEEPIES";
    public static final String MAKE_FRIENDS = "MAKE_FRIENDS";
    public static final String DELETE_FRIEND = "DELETE_FRIEND";
    public static final String GET_COUNTRIES = "GET_COUNTRIES";
    public static final String ISACTIVEALARM = "ISACTIVEALARM";

    // NAME OF TABLES' COLUMNS IN MYSQL DATABASE 
    public static final String TOKEN = "TOKEN";
    public static final String ID = "ID";
    public static final String ISFRIEND = "ISFRIEND";
    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";
    public static final String STATUS = "USER_STATUS";
    public static final String FULLNAME = "FULLNAME";
    public static final String GENDER = "GENDER";
    public static final String PHOTO_NAME = "PHOTO_NAME";
    public static final String COUNTRY_NAME = "COUNTRY_NAME";
    public static final String COUNTRY_ID = "COUNTRY_ID";
    public static final String TIME = "TIME";
    public static final String WEEKDAY = "WEEKDAY";
    public static final String ISACTIVE = "ISACTIVE";
    public static final String USER_ID = "USER_ID";
    public static final String PAGE_RESULT = "PAGE_RESULT";
    public static final String LATITUDE = "LATITUDE";
    public static final String LONGITUDE = "LONGITUDE";
    public static final String LOCATION = "LOCATION";
    public static final String SIP_USERNAME = "SIP_USERNAME";
    public static final String SIP_REGISTRAR = "SIP_REGISTRAR";
    public static final String SIP_PASSWORD = "SIP_PASSWORD";
    public static final String GET_USER_BY_SIP_ID = "GET_USER_BY_SIP_ID";
    public static final String TIME_DIFFERENCE = "TIME_DIFFERENCE";
       
    public static final String UPDATE_DEVICE_INFO = "UPDATE_DEVICE_INFO";
    public static final String GET_DEVICE_INFO = "GET_DEVICE_INFO";
    public static final String NAME = "NAME";
    public static final String PLATFORM_ID = "PLATFORM_ID";
    public static final String PUSH_TOKEN = "PUSH_TOKEN";
    public static final String OS_VERSION = "OS_VERSION";
    public static final String APP_VERSION = "APP_VERSION";
    public static final String SANDBOX = "SANDBOX";
    public static final String GET_USER_ID_BY_TOKEN = "GET_USER_ID_BY_TOKEN";
    public static final String GET_DEVICE_INFO_BY_SIP_ID = "GET_DEVICE_INFO_BY_SIP_ID";
    
    public static final int ANDROID = 1;
    public static final int IOS = 2;
}
