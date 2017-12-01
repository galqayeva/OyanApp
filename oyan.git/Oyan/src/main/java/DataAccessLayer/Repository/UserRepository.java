/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.Repository;

/**
 *
 * @author Eldar Hasanov
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

import BusinessLayer.Entity.*;
import BusinessLayer.Exception.*;
import DataAccessLayer.Constant.*;
import SeriveLayer.Domain.Objects;

public class UserRepository {
       public int UpdateIncomingCall(UserEntity userEntity) throws DatabaseException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        UserEntity resultEntity = new UserEntity();
        try {

            Class.forName(Constants.JDBC_DRIVER);
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
            preparedStatement = connection.prepareCall(String.format("{CALL %S(?)}", Constants.UpdateIncomingCall));
            preparedStatement.setLong(1, userEntity.getId());   
            preparedStatement.execute();
            return 1;
        } catch (Exception exception) {
            throw new DatabaseException();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception exception) {
                throw new DatabaseException();
            }
        }
         
    }
        
        public int UpdateOutgoingCall(UserEntity userEntity) throws DatabaseException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        UserEntity resultEntity = new UserEntity();
        try {

            Class.forName(Constants.JDBC_DRIVER);
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
            preparedStatement = connection.prepareCall(String.format("{CALL %S(?)}", Constants.UpdateOutgoingCall));
            preparedStatement.setLong(1, userEntity.getId()); 
            preparedStatement.execute();
            return 1;
        } catch (Exception exception) {
            throw new DatabaseException();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception exception) {
                throw new DatabaseException();
            }
        }
         
    }
                
        public int UpdateAcceptCall(UserEntity userEntity) throws DatabaseException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        UserEntity resultEntity = new UserEntity();
        try {

            Class.forName(Constants.JDBC_DRIVER);
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
            preparedStatement = connection.prepareCall(String.format("{CALL %S(?)}", Constants.UpdateAcceptCall));
            preparedStatement.setLong(1, userEntity.getId()); 
            preparedStatement.execute();
            return 1;
        } catch (Exception exception) {
            throw new DatabaseException();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception exception) {
                throw new DatabaseException();
            }
        }
         
    }
    public UserEntity Logout(UserEntity userEntity) throws DatabaseException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        UserEntity resultEntity = null;
        try {

            Class.forName(Constants.JDBC_DRIVER);
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
            preparedStatement = connection.prepareCall(String.format("{CALL %S (?)}", Constants.LOGOUT));
            preparedStatement.setString(1, userEntity.getToken());
            boolean rowExsist = preparedStatement.execute();

        } catch (Exception exception) {

            throw new DatabaseException();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception exception) {
                throw new DatabaseException();
            }
        }
        resultEntity = new UserEntity();
        return resultEntity;
    }

    public UserEntity Login(UserEntity userEntity) throws DatabaseException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        UserEntity resultEntity = null;
        try {
            String userna = Constants.DB_URL + Constants.DB_USERNAME + Constants.DB_PASSWORD + "0 0 " + String.format("{CALL %S (?,?,?)}", Constants.LOGIN);
            Class.forName(Constants.JDBC_DRIVER);
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
            preparedStatement = connection.prepareCall(String.format("{CALL %S (?,?,?)}", Constants.LOGIN));
            preparedStatement.setString(1, userEntity.getEmail());
            preparedStatement.setString(2, userEntity.getPassword());
            preparedStatement.setString(3, userEntity.getToken());
            boolean rowExsist = preparedStatement.execute();

            if (rowExsist) {
                resultEntity = new UserEntity();
                resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {

                    resultEntity.setId(resultSet.getLong(Constants.ID));
                    resultEntity.setStatus(resultSet.getString(Constants.STATUS));
                    resultEntity.setEmail(resultSet.getString(Constants.EMAIL));
                    resultEntity.setFullName(resultSet.getString(Constants.FULLNAME));
                    resultEntity.setGender(resultSet.getString(Constants.GENDER));
                    resultEntity.setPhotoName(resultSet.getString(Constants.PHOTO_NAME));
                //    resultEntity.setCountryName(resultSet.getString(Constants.COUNTRY_NAME));
                    //    resultEntity.setCountryId(resultSet.getLong(Constants.COUNTRY_ID));
                    resultEntity.setSipUsername(resultSet.getLong(Constants.SIP_USERNAME));
                    resultEntity.setSipRegistrar(resultSet.getString(Constants.SIP_REGISTRAR));
                    resultEntity.setSipPassword(resultSet.getString(Constants.SIP_PASSWORD));

                }
                resultEntity.setToken(userEntity.getToken());
            }
        } catch (Exception exception) {

            throw new DatabaseException();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception exception) {
                throw new DatabaseException();
            }
        }
        return resultEntity;
    }

    public UserEntity Registration(UserEntity userEntity) throws DatabaseException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        UserEntity resultEntity = null;

        try {

            Class.forName(Constants.JDBC_DRIVER);
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
            preparedStatement = connection.prepareCall(String.format("{CALL %S(? ,? ,? ,? ,? ,?,?)}", Constants.REGISTRATION));
            preparedStatement.setString(1, userEntity.getEmail());
            preparedStatement.setString(2, userEntity.getPassword());
            preparedStatement.setString(3, userEntity.getFullName());
            preparedStatement.setString(4, userEntity.getGender());
            preparedStatement.setString(5, userEntity.getCountryName());
            preparedStatement.setString(6, userEntity.getToken());
            preparedStatement.setString(7, userEntity.getPhotoName());
            boolean rowExsist = preparedStatement.execute();
            if (rowExsist) {
                resultEntity = new UserEntity();
                resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {
                    resultEntity.setId(resultSet.getLong(Constants.ID));
                    resultEntity.setEmail(resultSet.getString(Constants.EMAIL));
                    resultEntity.setFullName(resultSet.getString(Constants.FULLNAME));
                    resultEntity.setGender(resultSet.getString(Constants.GENDER));
                    resultEntity.setPhotoName(resultSet.getString(Constants.PHOTO_NAME));
               //     resultEntity.setCountryName(resultSet.getString(Constants.COUNTRY_NAME));
                    //     resultEntity.setCountryId(resultSet.getLong(Constants.COUNTRY_ID));
                    resultEntity.setSipUsername(resultSet.getLong(Constants.SIP_USERNAME));
                    resultEntity.setSipRegistrar(resultSet.getString(Constants.SIP_REGISTRAR));
                    resultEntity.setSipPassword(resultSet.getString(Constants.SIP_PASSWORD));

                }
                resultEntity.setToken(userEntity.getToken());
            }
        } catch (Exception exception) {

            throw new DatabaseException();
        } finally {

            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception exception) {
                throw new DatabaseException();
            }
        }
        return resultEntity;
    }

        public WakeUpEntity GetCallData(UserEntity userEntity) throws DatabaseException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        WakeUpEntity resultEntity = new WakeUpEntity();
        try {

            Class.forName(Constants.JDBC_DRIVER);
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
            preparedStatement = connection.prepareCall(String.format("{CALL %S(?)}", "GET_CALL_DATA"));
            preparedStatement.setLong(1, userEntity.getId());
            boolean rowExsist = preparedStatement.execute();
            if (rowExsist) {

                resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {
                    resultEntity.setAcceptCallAmount(resultSet.getInt("answeredcall"));
                    resultEntity.setIncomingCallAmount(resultSet.getInt("incomingcall"));
                    resultEntity.setOutgoingCallAmount(resultSet.getInt("outgoingcall")); 
                }
            }
        } catch (Exception exception) {
            throw new DatabaseException();
        } finally {
            try {

                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception exception) {
                throw new DatabaseException();
            }
        }

        return resultEntity;
    }
    
    public UserEntity GetUser(UserEntity userEntity) throws DatabaseException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        UserEntity resultEntity = new UserEntity();
        try {

            Class.forName(Constants.JDBC_DRIVER);
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
            preparedStatement = connection.prepareCall(String.format("{CALL %S(?)}", Constants.GET_USER));
            preparedStatement.setLong(1, userEntity.getId());
            boolean rowExsist = preparedStatement.execute();
            if (rowExsist) {

                resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {
                    resultEntity.setStatus(resultSet.getString(Constants.STATUS));
                    resultEntity.setId(resultSet.getLong(Constants.ID));
                    resultEntity.setEmail(resultSet.getString(Constants.EMAIL));
                    resultEntity.setFullName(resultSet.getString(Constants.FULLNAME));
                    resultEntity.setGender(resultSet.getString(Constants.GENDER));
                    resultEntity.setPhotoName(resultSet.getString(Constants.PHOTO_NAME));
                }
            }
        } catch (Exception exception) {
            throw new DatabaseException();
        } finally {
            try {

                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception exception) {
                throw new DatabaseException();
            }
        }

        return resultEntity;
    }

    public UserEntity UpdateUser(UserEntity userEntity) throws DatabaseException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        UserEntity resultEntity = new UserEntity();
        try {

            Class.forName(Constants.JDBC_DRIVER);
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
            preparedStatement = connection.prepareCall(String.format("{CALL %S(?,?,?)}", Constants.UPDATE_USER));
            preparedStatement.setLong(1, userEntity.getId());
            preparedStatement.setString(2, userEntity.getFullName());
            preparedStatement.setString(3, userEntity.getStatus());
            boolean rowExsist = preparedStatement.execute();
            if (rowExsist) {

                resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {
                    resultEntity.setStatus(resultSet.getString(Constants.STATUS));
                    resultEntity.setId(resultSet.getLong(Constants.ID));
                    resultEntity.setFullName(resultSet.getString(Constants.FULLNAME));
                }
            }

        } catch (Exception exception) {
            throw new DatabaseException();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception exception) {
                throw new DatabaseException();
            }
        }
        return resultEntity;
    }

    public long GetUserId(String token) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        long id = -1;
        try {
            Class.forName(Constants.JDBC_DRIVER);
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
            preparedStatement = connection.prepareCall(String.format("{CALL %S(?)}", Constants.GET_USER_ID));
            preparedStatement.setString(1, token);
            boolean rowExsist = preparedStatement.execute();
            if (rowExsist) {

                resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {
                    id = resultSet.getLong(Constants.ID);
                }
            }
        } catch (Exception exception) {

            id = -1;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception exception) {
                id = -1;
            }
        }
        return id;
    }

    public long CheckUserId(long userId) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        long id = -1;
        try {
            Class.forName(Constants.JDBC_DRIVER);
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
            preparedStatement = connection.prepareCall(String.format("{CALL %S(?)}", Constants.CHECK_USER_ID));
            preparedStatement.setLong(1, userId);
            boolean rowExsist = preparedStatement.execute();
            if (rowExsist) {

                resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {
                    id = resultSet.getLong(Constants.ID);
                }
            }
        } catch (Exception exception) {

            id = -1;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception exception) {
                id = -1;
            }
        }
        return id;
    }

    public BaseEntity AddAlarm(UserEntity userEntity, AlarmEntity alarmEntity) throws DatabaseException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        BaseEntity resultEntity = new BaseEntity();
        try {

            Class.forName(Constants.JDBC_DRIVER);
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
            preparedStatement = connection.prepareCall(String.format("{CALL %S(?,?,?,?,?)}", Constants.ADD_ALARM_TIME));

            preparedStatement.setLong(1, userEntity.getId());
            preparedStatement.setDouble(2, userEntity.getLatitude());
            preparedStatement.setDouble(3, userEntity.getLongitude());
            preparedStatement.setLong(4, alarmEntity.getTime());
            preparedStatement.setString(5, userEntity.getLocation());
            boolean rowExsist = preparedStatement.execute();
            if (rowExsist) {

                resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {

                    alarmEntity.setId(resultSet.getLong(Constants.ID));
                }
                preparedStatement.close();
                resultSet.close();

            }
            if (rowExsist && alarmEntity.getWeekDays() != null && alarmEntity.getWeekDays().length > 0) {
                preparedStatement = connection.prepareCall(String.format("{CALL %S(?,?)}", Constants.ADD_ALARM_WEEKDAY));
                for (long weekDay : alarmEntity.getWeekDays()) {

                    preparedStatement.setLong(1, alarmEntity.getId());
                    preparedStatement.setLong(2, weekDay);
                    preparedStatement.execute();
                }
            }

        } catch (Exception exception) {

            throw new DatabaseException();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception exception) {
                throw new DatabaseException();
            }
        }
        return resultEntity;
    }

    public AlarmEntity IsActiveAlarm(AlarmEntity alarmEntity) throws DatabaseException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        AlarmEntity resultEntity = new AlarmEntity();
        try {

            Class.forName(Constants.JDBC_DRIVER);
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
            preparedStatement = connection.prepareCall(String.format("{CALL %S(?,?)}", Constants.ISACTIVEALARM));
            preparedStatement.setLong(1, alarmEntity.getId());
            preparedStatement.setLong(2, alarmEntity.getIsActive());
            preparedStatement.execute();
            preparedStatement.close();

        } catch (Exception exception) {

            throw new DatabaseException();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (Exception exception) {
                throw new DatabaseException();
            }
        }
        return resultEntity;
    }

    public BaseEntity UpdateAlarm(UserEntity userEntity, AlarmEntity alarmEntity) throws DatabaseException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        BaseEntity resultEntity = new BaseEntity();
        try {

            Class.forName(Constants.JDBC_DRIVER);
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
            preparedStatement = connection.prepareCall(String.format("{CALL %S(?,?,?,?,?,?,?)}", Constants.UPDATE_ALARM_TIME));
            preparedStatement.setLong(1, userEntity.getId());
            preparedStatement.setDouble(2, userEntity.getLatitude());
            preparedStatement.setDouble(3, userEntity.getLongitude());
            preparedStatement.setLong(4, alarmEntity.getId());
            preparedStatement.setLong(5, alarmEntity.getIsActive());
            preparedStatement.setLong(6, alarmEntity.getTime());
            preparedStatement.setString(7, userEntity.getLocation());
            preparedStatement.execute();
            preparedStatement.close();

            if (alarmEntity.getWeekDays() != null && alarmEntity.getWeekDays().length > 0) {
                preparedStatement = connection.prepareCall(String.format("{CALL %S(?,?)}", Constants.ADD_ALARM_WEEKDAY));

                for (long weekDay : alarmEntity.getWeekDays()) {

                    preparedStatement.setLong(1, alarmEntity.getId());
                    preparedStatement.setLong(2, weekDay);
                    preparedStatement.execute();
                }
            }
        } catch (Exception exception) {

            throw new DatabaseException();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }

            } catch (Exception exception) {
                throw new DatabaseException();
            }
        }
        return resultEntity;
    }

    public BaseEntity DeleteAlarm(UserEntity userEntity, AlarmEntity alarmEntity) throws DatabaseException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        BaseEntity resultEntity = new BaseEntity();
        try {
            Class.forName(Constants.JDBC_DRIVER);
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
            preparedStatement = connection.prepareCall(String.format("{CALL %S(? ,?)}", Constants.DELETE_ALARM));
            preparedStatement.setLong(1, userEntity.getId());
            preparedStatement.setLong(2, alarmEntity.getId());
            preparedStatement.execute();
        } catch (Exception exception) {

            throw new DatabaseException();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception exception) {
                throw new DatabaseException();
            }
        }
        return resultEntity;
    }

    public ArrayList<AlarmEntity> GetAlarms(UserEntity userEntity) throws DatabaseException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet1 = null;
        ResultSet resultSet2 = null;
        ArrayList<AlarmEntity> resultList = new ArrayList<>();
        AlarmEntity resultEntity = new AlarmEntity();
        try {

            Class.forName(Constants.JDBC_DRIVER);
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
            preparedStatement = connection.prepareCall(String.format("{CALL %S(?)}", Constants.GET_ALARM_TIME));
            preparedStatement.setLong(1, userEntity.getId());
            boolean rowExsist1 = preparedStatement.execute();
            if (rowExsist1) {

                resultSet1 = preparedStatement.getResultSet();
//                preparedStatement.close();
                preparedStatement = connection.prepareCall(String.format("{CALL %S(?)}", Constants.GET_ALARM_WEEKDAYS));
                AlarmEntity helpEntity = null;
                while (resultSet1.next()) {

                    helpEntity = new AlarmEntity();
                    helpEntity.setId(resultSet1.getLong(Constants.ID));
                    helpEntity.setIsActive(resultSet1.getLong(Constants.ISACTIVE));
                    helpEntity.setTime(resultSet1.getLong(Constants.TIME));
                    helpEntity.setLatitude(resultSet1.getDouble(Constants.LATITUDE));
                    helpEntity.setLongitude(resultSet1.getDouble(Constants.LONGITUDE));
                    helpEntity.setTime(resultSet1.getLong(Constants.TIME));
                    preparedStatement.setLong(1, helpEntity.getId());
                    boolean rowExsist2 = preparedStatement.execute();
                    if (rowExsist2) {
                        long weekDays[] = new long[100];
                        int index = 0;
                        resultSet2 = preparedStatement.getResultSet();
                        while (resultSet2.next()) {

                            weekDays[index++] = resultSet2.getLong(Constants.WEEKDAY);
                        }
                        helpEntity.setWeekDays(Arrays.copyOf(weekDays, index));
                        resultList.add(helpEntity);
                    }
                }
            }
        } catch (Exception exception) {

            throw new DatabaseException();
        } finally {

            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet1 != null) {
                    resultSet1.close();
                }
            } catch (Exception exception) {
                throw new DatabaseException();
            }
        }
        return resultList;
    }

    public AlarmEntity GetNearestAlarm(UserEntity userEntity, AlarmEntity alarmEntity) throws DatabaseException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        AlarmEntity resultAlarmEntity = new AlarmEntity();
        try {

            Class.forName(Constants.JDBC_DRIVER);
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
            preparedStatement = connection.prepareCall(String.format("{CALL %S(?,?,?)}", Constants.GET_NEAREST_ALARM));

            preparedStatement.setLong(1, alarmEntity.getCurrentTime());
            preparedStatement.setLong(2, alarmEntity.getWeekDay());
            preparedStatement.setLong(3, userEntity.getId());
            boolean rowExsist = preparedStatement.execute();
            if (rowExsist) {
                resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {

                    resultAlarmEntity = new AlarmEntity();
                    resultAlarmEntity.setTime(resultSet.getLong(Constants.TIME));
                }
            }
        } catch (Exception exception) {
            throw new DatabaseException();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }

            } catch (Exception exception) {
                throw new DatabaseException();
            }
        }
        return resultAlarmEntity;
    }

    public ArrayList<UserEntity> GetSleepies(UserEntity userEntity, AlarmEntity alarmEntity) throws DatabaseException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<UserEntity> resultList = new ArrayList<>();
        UserEntity resulEntity = new UserEntity();
        AlarmEntity resultAlarmEntity = new AlarmEntity();
        try {

            Class.forName(Constants.JDBC_DRIVER);
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
            preparedStatement = connection.prepareCall(String.format("{CALL %S(?,?,?,?,?,?,?,?,?,?)}", Constants.GET_SLEEPIES));
            preparedStatement.setString(1, userEntity.getGender());
            preparedStatement.setLong(2, userEntity.getPageNumber());
            preparedStatement.setLong(3, alarmEntity.getTime());
            preparedStatement.setLong(4, alarmEntity.getCurrentTime());
            preparedStatement.setLong(5, alarmEntity.getWeekDay());
            preparedStatement.setLong(6, userEntity.getDistance());
            preparedStatement.setDouble(7, userEntity.getLongitude());
            preparedStatement.setDouble(8, userEntity.getLatitude());
            preparedStatement.setString(9, userEntity.getFullName());
            preparedStatement.setLong(10, userEntity.getId());
            boolean rowExsist = preparedStatement.execute();
            if (rowExsist) {
                resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {

                    resulEntity = new UserEntity();
                    WakeUpEntity wk = new WakeUpEntity();
                    
                    resulEntity.setStatus(resultSet.getString(Constants.STATUS));
                    resulEntity.setIsFriend(resultSet.getLong(Constants.ISFRIEND));
                    resulEntity.setId(resultSet.getLong(Constants.ID));
                    resulEntity.setEmail(resultSet.getString(Constants.EMAIL));
                    resulEntity.setFullName(resultSet.getString(Constants.FULLNAME));
                    resulEntity.setGender(resultSet.getString(Constants.GENDER));
                    resulEntity.setPhotoName(resultSet.getString(Constants.PHOTO_NAME));
                    resulEntity.setLatitude(resultSet.getDouble(Constants.LATITUDE));
                    resulEntity.setLongitude(resultSet.getDouble(Constants.LONGITUDE));
                    resulEntity.setLocation(resultSet.getString(Constants.LOCATION));
                    resulEntity.setFirstTime(resultSet.getLong(Constants.TIME));
                    resulEntity.setSipUsername(resultSet.getLong(Constants.SIP_USERNAME));
                    resulEntity.setSipRegistrar(resultSet.getString(Constants.SIP_REGISTRAR));
                    wk.setAcceptCallAmount(resultSet.getInt("answeredcall"));
                    wk.setIncomingCallAmount(resultSet.getInt("incomingcall"));
                    wk.setOutgoingCallAmount(resultSet.getInt("outgoingcall"));
                    
                    resulEntity.setWakeupEntity(wk);
                    resultList.add(resulEntity);
                }
            }
        } catch (Exception exception) {
            throw new DatabaseException();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }

            } catch (Exception exception) {
                throw new DatabaseException();
            }
        }
        return resultList;
    }

    public ArrayList<UserEntity> RefreshSleepies(UserEntity userEntity, AlarmEntity alarmEntity) throws DatabaseException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<UserEntity> resultList = new ArrayList<>();
        UserEntity resulEntity = new UserEntity();
        AlarmEntity resultAlarmEntity = new AlarmEntity();
        try {

            Class.forName(Constants.JDBC_DRIVER);
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
            preparedStatement = connection.prepareCall(String.format("{CALL %S(?,?,?,?,?,?,?,?,?)}", Constants.REFRESH_SLEEPIES));
            preparedStatement.setString(1, userEntity.getFullName());
            preparedStatement.setLong(2, userEntity.getPageNumber());
            preparedStatement.setLong(3, alarmEntity.getTime());
            preparedStatement.setLong(4, alarmEntity.getCurrentTime());
            preparedStatement.setLong(5, alarmEntity.getWeekDay());
            preparedStatement.setLong(6, userEntity.getDistance());
            preparedStatement.setDouble(7, userEntity.getLongitude());
            preparedStatement.setDouble(8, userEntity.getLatitude());
            preparedStatement.setDouble(9, userEntity.getId());
            boolean rowExsist = preparedStatement.execute();
            if (rowExsist) {
                resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {

                    resulEntity = new UserEntity();
                    resulEntity.setStatus(resultSet.getString(Constants.STATUS));
                    resulEntity.setIsFriend(resultSet.getLong(Constants.ISFRIEND));
                    resulEntity.setId(resultSet.getLong(Constants.ID));
                    resulEntity.setEmail(resultSet.getString(Constants.EMAIL));
                    resulEntity.setFullName(resultSet.getString(Constants.FULLNAME));
                    resulEntity.setGender(resultSet.getString(Constants.GENDER));
                    resulEntity.setPhotoName(resultSet.getString(Constants.PHOTO_NAME));
                    resulEntity.setLatitude(resultSet.getDouble(Constants.LATITUDE));
                    resulEntity.setLongitude(resultSet.getDouble(Constants.LONGITUDE));
                    resulEntity.setLocation(resultSet.getString(Constants.LOCATION));
                    resulEntity.setFirstTime(resultSet.getLong(Constants.TIME));
                    resulEntity.setSipUsername(resultSet.getLong(Constants.SIP_USERNAME));
                    resulEntity.setSipRegistrar(resultSet.getString(Constants.SIP_REGISTRAR));
                    resultList.add(resulEntity);
                }
            }
        } catch (Exception exception) {
            throw new DatabaseException();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }

            } catch (Exception exception) {
                throw new DatabaseException();
            }
        }
        return resultList;
    }

    public UserEntity PhotoUpload(UserEntity userEntity) throws DatabaseException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        UserEntity resultEntity = null;
        try {

            Class.forName(Constants.JDBC_DRIVER);
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
            preparedStatement = connection.prepareCall(String.format("{CALL %S(? ,?)}", Constants.PHOTO_UPLOAD));
            preparedStatement.setLong(1, userEntity.getId());
            preparedStatement.setString(2, userEntity.getPhotoName());
            preparedStatement.execute();
        } catch (Exception exception) {

            throw new DatabaseException();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (Exception exception) {
                throw new DatabaseException();
            }
        }
        resultEntity = new UserEntity();
        return resultEntity;
    }

    public ArrayList<UserEntity> GetCountries() throws DatabaseException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<UserEntity> resultList = new ArrayList<>();
        UserEntity resultEntity = new UserEntity();
        try {

            Class.forName(Constants.JDBC_DRIVER);
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
            preparedStatement = connection.prepareCall(String.format("{CALL %S()}", Constants.GET_COUNTRIES));
            boolean rowExsist = preparedStatement.execute();
            if (rowExsist) {

                resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {

                    resultEntity = new UserEntity();
                    resultEntity.setCountryId(resultSet.getLong(Constants.COUNTRY_ID));
                    resultEntity.setCountryName(resultSet.getString(Constants.COUNTRY_NAME));
                    resultList.add(resultEntity);
                }
            }
        } catch (Exception exception) {
            throw new DatabaseException();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception exception) {
                throw new DatabaseException();
            }

        }
        return resultList;
    }

    public UserEntity GetUserBySipId(UserEntity userEntity) throws DatabaseException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        UserEntity resultEntity = new UserEntity();
        try {

            Class.forName(Constants.JDBC_DRIVER);
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
            preparedStatement = connection.prepareCall(String.format("{CALL %S(?)}", Constants.GET_USER_BY_SIP_ID));
            preparedStatement.setLong(1, userEntity.getSipUsername());
            boolean rowExsist = preparedStatement.execute();
            if (rowExsist) {

                resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {
                    resultEntity.setId(resultSet.getLong(Constants.ID));
                    resultEntity.setFullName(resultSet.getString(Constants.FULLNAME));
                    resultEntity.setPhotoName(resultSet.getString(Constants.PHOTO_NAME));
                }
            }
        } catch (Exception exception) {
            throw new DatabaseException();
        } finally {
            try {

                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception exception) {
                throw new DatabaseException();
            }
        }

        return resultEntity;
    }

    public UserEntity MakeFriend(UserEntity userEntity1, UserEntity userEntity2) throws DatabaseException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        UserEntity resultEntity = new UserEntity();
        try {

            Class.forName(Constants.JDBC_DRIVER);
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
            preparedStatement = connection.prepareCall(String.format("{CALL %S(?,?)}", Constants.MAKE_FRIENDS));
            preparedStatement.setLong(1, userEntity1.getId());
            preparedStatement.setLong(2, userEntity2.getId());

            boolean rowExsist = preparedStatement.execute();
            if (rowExsist) {
                resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {

                    resultEntity.setId(resultSet.getLong(Constants.ID));
                    resultEntity.setPhotoName(resultSet.getString(Constants.PHOTO_NAME));
                }
            }
        } catch (Exception exception) {
            throw new DatabaseException();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }

            } catch (Exception exception) {
                throw new DatabaseException();
            }
        }
        return resultEntity;
    }

    public UserEntity DeleteFriend(UserEntity userEntity1, UserEntity userEntity2) throws DatabaseException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        UserEntity resultEntity = new UserEntity();
        try {

            Class.forName(Constants.JDBC_DRIVER);
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
            preparedStatement = connection.prepareCall(String.format("{CALL %S(?,?)}", Constants.DELETE_FRIEND));
            preparedStatement.setLong(1, userEntity1.getId());
            preparedStatement.setLong(2, userEntity2.getId());

            boolean rowExsist = preparedStatement.execute();
            if (rowExsist) {
                resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {

                    resultEntity.setId(resultSet.getLong(Constants.ID));
                    resultEntity.setPhotoName(resultSet.getString(Constants.PHOTO_NAME));
                }
            }
        } catch (Exception exception) {
            throw new DatabaseException();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }

            } catch (Exception exception) {
                throw new DatabaseException();
            }
        }
        return resultEntity;
    }

    public ArrayList<UserEntity> GetFriends(UserEntity userEntity, AlarmEntity alarmEntity) throws DatabaseException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<UserEntity> resultList = new ArrayList<>();
        try {

            Class.forName(Constants.JDBC_DRIVER);
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
            preparedStatement = connection.prepareCall(String.format("{CALL %S(?,?,?,?)}", Constants.GET_FRIENDS));
            preparedStatement.setLong(1, alarmEntity.getTime());
            preparedStatement.setLong(2, alarmEntity.getCurrentTime());
            preparedStatement.setLong(3, alarmEntity.getWeekDay());
            preparedStatement.setLong(4, userEntity.getId());
            boolean rowExsist = preparedStatement.execute();
            if (rowExsist) {
                resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {
                    UserEntity resulEntity = new UserEntity();
                    AlarmEntity resulAlarmEntity = new AlarmEntity();
                    WakeUpEntity wk = new WakeUpEntity();
                    resulEntity.setStatus(resultSet.getString(Constants.STATUS));
                    resulEntity.setId(resultSet.getLong(Constants.ID));
                    resulEntity.setEmail(resultSet.getString(Constants.EMAIL));
                    resulEntity.setFullName(resultSet.getString(Constants.FULLNAME));
                    resulEntity.setGender(resultSet.getString(Constants.GENDER));
                    resulEntity.setPhotoName(resultSet.getString(Constants.PHOTO_NAME));
                    resulEntity.setFirstTime(resultSet.getLong(Constants.TIME));
                    resulEntity.setSipUsername(resultSet.getLong(Constants.SIP_USERNAME));
                    resulEntity.setSipRegistrar(resultSet.getString(Constants.SIP_REGISTRAR));
                    resulEntity.setLatitude(resultSet.getDouble(Constants.LATITUDE));
                    resulEntity.setLongitude(resultSet.getDouble(Constants.LONGITUDE));
                    resulEntity.setLocation(resultSet.getString(Constants.LOCATION));
                    resulEntity.setIsActive(resultSet.getLong(Constants.ISACTIVE));
                    resulEntity.setTimeDifference(resultSet.getLong(Constants.TIME_DIFFERENCE));
                    resulEntity.setWeekDay(resultSet.getLong(Constants.WEEKDAY));
                    
                       wk.setAcceptCallAmount(resultSet.getInt("answeredcall"));
                    wk.setIncomingCallAmount(resultSet.getInt("incomingcall"));
                    wk.setOutgoingCallAmount(resultSet.getInt("outgoingcall"));
                    
                    resulEntity.setWakeupEntity(wk);
                    resultList.add(resulEntity);
                }
            }
        } catch (Exception exception) {
            throw new DatabaseException();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }

            } catch (Exception exception) {
                throw new DatabaseException();
            }
        }
        return resultList;
    }
}
