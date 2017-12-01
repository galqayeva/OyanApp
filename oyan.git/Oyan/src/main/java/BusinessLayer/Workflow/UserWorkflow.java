/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLayer.Workflow;

/**
 *
 * @author Eldar Hasanov
 */
import java.util.UUID;
import java.util.ArrayList;
import java.util.Iterator;

import SeriveLayer.Domain.*;
import SeriveLayer.Message.*;
import BusinessLayer.Entity.*;
import BusinessLayer.Exception.*;
import DataAccessLayer.Repository.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.faces.view.Location;

public class UserWorkflow {

    public UserDomain Logout(UserDomain userDomain) {

        UserDomain resultDomain = new UserDomain();
        try {

            if (userDomain.getToken() == null
                    || userDomain.getToken().isEmpty()) {
                return resultDomain;
            }
            UserEntity userEntity = new UserEntity();
            userEntity.setToken(userDomain.getToken());
            UserRepository userRepository = new UserRepository();
            userRepository.Logout(userEntity);
        } catch (DatabaseException databaseException) {

            resultDomain = new UserDomain();
        }
        return resultDomain;
    }

    public UserDomain Login(UserDomain userDomain) {

        UserDomain resultDomain = new UserDomain();
        try {

            if (userDomain.getEmail() == null
                    || userDomain.getPassword() == null
                    || userDomain.getEmail().isEmpty()
                    || userDomain.getPassword().isEmpty()) {

                resultDomain.setMessageId(Messages.DATA_VALIDATION_ERROR);
                resultDomain.setMessage(Messages.MESSAGE_DATA_VALIDATION_ERROR);
                return resultDomain;
            }

            UUID uuid = UUID.randomUUID();
            UserEntity userEntity = new UserEntity();
            userEntity.setEmail(userDomain.getEmail());
            userEntity.setPassword(userDomain.getPassword());
            userEntity.setToken(uuid.toString());

            UserRepository userRepository = new UserRepository();
            UserEntity resultEntity = userRepository.Login(userEntity);

            if (resultEntity == null) {

                resultDomain.setMessageId(Messages.USER_NOT_EXSIST);
                resultDomain.setMessage(Messages.MESSAGE_USER_NOT_EXSIST);
                return resultDomain;
            }
            resultDomain.setId(resultEntity.getId());
            resultDomain.setEmail(resultEntity.getEmail());
            resultDomain.setStatus(resultEntity.getStatus());
            resultDomain.setPassword(resultEntity.getPassword());
            resultDomain.setFullName(resultEntity.getFullName());
            resultDomain.setGender(resultEntity.getGender());
            resultDomain.setPhotoName(resultEntity.getPhotoName());
            //        resultDomain.setCountryName(resultEntity.getCountryName());
            //        resultDomain.setCountryId(resultEntity.getCountryId());
            resultDomain.setToken(resultEntity.getToken());
            resultDomain.setSipUsername(resultEntity.getSipUsername());
            resultDomain.setSipRegistrar(resultEntity.getSipRegistrar());
            resultDomain.setSipPassword(resultEntity.getSipPassword());
            resultDomain.setMessageId(Messages.SUCCESFULL);
            resultDomain.setMessage(Messages.MESSAGE_SUCCESFULL);
        } catch (DatabaseException databaseException) {

            resultDomain = new UserDomain();
            resultDomain.setMessageId(Messages.DATA_ACCESS_LAYER_ISSUE);
            resultDomain.setMessage(Messages.MESSAGE_DATA_ACCESS_LAYER_ISSUE);
        }

        return resultDomain;
    }

        public WakeUpDomain GetCallData(UserDomain userDomain) {

        WakeUpDomain resultDomain = new WakeUpDomain();
        try {
             
            UserRepository userRepository = new UserRepository();
            UserEntity userEntity = new UserEntity();
            userEntity.setId(userDomain.getId());
            WakeUpEntity resultEntity = userRepository.GetCallData(userEntity);
            resultDomain.setAcceptCallAmount(resultEntity.getAcceptCallAmount());
            resultDomain.setIncomingCallAmount(resultEntity.getIncomingCallAmount()); 
            resultDomain.setOutgoingCallAmount(resultEntity.getOutgoingCallAmount()); 

        } catch (DatabaseException databaseException) {
            resultDomain = new WakeUpDomain(); 
            return resultDomain;
        }

        return resultDomain;
    }
    
    public UserDomain GetUser(UserDomain userDomain) {

        UserDomain resultDomain = new UserDomain();
        try {
            if (userDomain.getToken() == null
                    || userDomain.getToken().isEmpty()) {
                resultDomain.setMessage(Messages.MESSAGE_DATA_VALIDATION_ERROR);
                resultDomain.setMessageId(Messages.DATA_VALIDATION_ERROR);
                return resultDomain;
            }
            UserRepository userRepository = new UserRepository();
            UserEntity userEntity = new UserEntity();
            userEntity.setId(userDomain.getId());
            UserEntity resultEntity = userRepository.GetUser(userEntity);
            resultDomain.setId(resultEntity.getId());
            resultDomain.setStatus(resultEntity.getStatus());

            resultDomain.setEmail(resultEntity.getEmail());
            resultDomain.setFullName(resultEntity.getFullName());
            resultDomain.setGender(resultEntity.getGender());
            resultDomain.setPhotoName(resultEntity.getPhotoName());
            //          resultDomain.setCountryId(resultEntity.getCountryId());
            //           resultDomain.setCountryName(resultEntity.getCountryName());
            resultDomain.setMessage(Messages.MESSAGE_SUCCESFULL);
            resultDomain.setMessageId(Messages.SUCCESFULL);

        } catch (DatabaseException databaseException) {
            resultDomain = new UserDomain();
            resultDomain.setMessage(Messages.MESSAGE_DATA_ACCESS_LAYER_ISSUE);
            resultDomain.setMessageId(Messages.DATA_ACCESS_LAYER_ISSUE);
            return resultDomain;
        }

        return resultDomain;
    }

    public UserDomain GetUserByToken(UserDomain userDomain) {

        UserDomain resultDomain = new UserDomain();
        try {
            if (userDomain.getToken() == null
                    || userDomain.getToken().isEmpty()) {

                resultDomain.setMessage(Messages.MESSAGE_DATA_VALIDATION_ERROR);
                resultDomain.setMessageId(Messages.DATA_VALIDATION_ERROR);
                return resultDomain;
            }
            UserRepository userRepository = new UserRepository();
            UserEntity userEntity = new UserEntity();
            userEntity.setId(userRepository.GetUserId(userDomain.getToken()));
            UserEntity resultEntity = userRepository.GetUser(userEntity);
            resultDomain.setId(resultEntity.getId());
            resultDomain.setStatus(resultEntity.getStatus());
            resultDomain.setEmail(resultEntity.getEmail());
            resultDomain.setFullName(resultEntity.getFullName());
            resultDomain.setToken(userDomain.getToken());
            resultDomain.setGender(resultEntity.getGender());
            resultDomain.setPhotoName(resultEntity.getPhotoName());
            //   resultDomain.setCountryId(resultEntity.getCountryId());
            //    resultDomain.setCountryName(resultEntity.getCountryName());
            resultDomain.setMessage(Messages.MESSAGE_SUCCESFULL);
            resultDomain.setMessageId(Messages.SUCCESFULL);

        } catch (DatabaseException databaseException) {

            resultDomain = new UserDomain();
            resultDomain.setMessage(Messages.MESSAGE_DATA_ACCESS_LAYER_ISSUE);
            resultDomain.setMessageId(Messages.DATA_ACCESS_LAYER_ISSUE);
        }
        return resultDomain;
    }

    public UserDomain UpdateUser(UserDomain userDomain) {

        UserDomain resultDomain = new UserDomain();

        if (userDomain.getToken() == null
                || userDomain.getToken().isEmpty()
                || userDomain.getFullName() == null
                || userDomain.getFullName().isEmpty()) {

            resultDomain.setMessage(Messages.MESSAGE_DATA_VALIDATION_ERROR);
            resultDomain.setMessageId(Messages.DATA_VALIDATION_ERROR);
            return resultDomain;
        }
        UserRepository userRepository = new UserRepository();
        userDomain.setId(userRepository.GetUserId(userDomain.getToken()));
        if (userDomain.getId() == -1) {

            resultDomain.setMessage(Messages.MESSAGE_AUTHORIZATION_ERROR);
            resultDomain.setMessageId(Messages.AUTHORIZATION_ERROR);
            return resultDomain;
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDomain.getId());
        userEntity.setFullName(userDomain.getFullName());
        userEntity.setStatus(userDomain.getStatus());
        try {
            UserEntity resultEntity = userRepository.UpdateUser(userEntity);
            resultDomain.setId(resultEntity.getId());
            resultDomain.setFullName(resultEntity.getFullName().trim());
            resultDomain.setStatus(resultEntity.getStatus().trim());
            resultDomain.setMessage(Messages.MESSAGE_SUCCESFULL);
            resultDomain.setMessageId(Messages.SUCCESFULL);

        } catch (DatabaseException databaseException) {
            resultDomain = new UserDomain();
            resultDomain.setMessage(Messages.MESSAGE_DATA_ACCESS_LAYER_ISSUE);
            resultDomain.setMessageId(Messages.DATA_ACCESS_LAYER_ISSUE);
            return resultDomain;
        }
        return resultDomain;
    }

    public UserDomain Registration(UserDomain userDomain) {

        UserDomain resultDomain = new UserDomain();

        try {

            if (userDomain.getEmail() == null
                    || userDomain.getPassword() == null
                    || userDomain.getFullName() == null
                    || userDomain.getEmail().isEmpty()
                    || userDomain.getPassword().isEmpty()
                    || userDomain.getFullName().isEmpty()) {
                resultDomain.setMessageId(Messages.DATA_VALIDATION_ERROR);
                resultDomain.setMessage(Messages.MESSAGE_DATA_VALIDATION_ERROR);
                return resultDomain;
            }
            UUID uuid = UUID.randomUUID();

            UserEntity userEntity = new UserEntity();
            userEntity.setEmail(userDomain.getEmail());
            userEntity.setPassword(userDomain.getPassword());
            userEntity.setFullName(userDomain.getFullName());
            userEntity.setGender(userDomain.getGender());
            userEntity.setPhotoName(userDomain.getPhotoName());
            userEntity.setCountryName(userDomain.getCountryName());
            userEntity.setToken(uuid.toString());

            UserRepository userRepository = new UserRepository();
            UserEntity resultEntity = userRepository.Registration(userEntity);

            if (resultEntity == null) {
                resultDomain.setMessageId(Messages.REGISTRATION_ERROR);
                resultDomain.setMessage(Messages.MESSAGE_REGISTRATION_ERROR);
                return resultDomain;
            }
            resultDomain.setId(resultEntity.getId());
            resultDomain.setEmail(resultEntity.getEmail());
            resultDomain.setFullName(resultEntity.getFullName());
            resultDomain.setGender(resultEntity.getGender());
            resultDomain.setPhotoName(resultEntity.getPhotoName());
            //  resultDomain.setCountryName(resultEntity.getCountryName());
            // resultDomain.setCountryId(resultEntity.getCountryId());
            resultDomain.setToken(resultEntity.getToken());
            resultDomain.setSipUsername(resultEntity.getSipUsername());
            resultDomain.setSipRegistrar(resultEntity.getSipRegistrar());
            resultDomain.setSipPassword(resultEntity.getSipPassword());
            resultDomain.setMessageId(Messages.SUCCESFULL);
            resultDomain.setMessage(Messages.MESSAGE_SUCCESFULL);
        } catch (DatabaseException databaseException) {

            resultDomain = new UserDomain();
            resultDomain.setMessageId(Messages.DATA_ACCESS_LAYER_ISSUE);
            resultDomain.setMessage(Messages.MESSAGE_DATA_ACCESS_LAYER_ISSUE);
        }
        return resultDomain;
    }

    public BaseDomain AddAlarm(UserDomain userDomain, AlarmDomain alarmDomain) {

        BaseDomain resultDomain = new BaseDomain();
        try {
            if (userDomain.getToken() == null
                    || userDomain.getToken().isEmpty()) {

                resultDomain.setMessage(Messages.MESSAGE_DATA_VALIDATION_ERROR);
                resultDomain.setMessageId(Messages.DATA_VALIDATION_ERROR);
                return resultDomain;
            }
            UserRepository userRepository = new UserRepository();

            userDomain.setId(userRepository.GetUserId(userDomain.getToken()));
            if (userDomain.getId() == -1) {
                resultDomain.setMessage(Messages.MESSAGE_AUTHORIZATION_ERROR);
                resultDomain.setMessageId(Messages.AUTHORIZATION_ERROR);
                return resultDomain;
            }
            UserEntity userEntity = new UserEntity();
            userEntity.setId(userDomain.getId());
            userEntity.setLongitude(userDomain.getLongitude());
            userEntity.setLatitude(userDomain.getLatitude());
            userEntity.setLocation(userDomain.getLocation());
            AlarmEntity alarmEntity = new AlarmEntity();
            alarmEntity.setTime(alarmDomain.getTime());
            alarmEntity.setWeekDays(alarmDomain.getWeekDays());
            userRepository.AddAlarm(userEntity, alarmEntity);
            resultDomain.setMessage(Messages.MESSAGE_SUCCESFULL);
            resultDomain.setMessageId(Messages.SUCCESFULL);
        } catch (DatabaseException databaseException) {

            resultDomain = new BaseDomain();
            resultDomain.setMessage(Messages.MESSAGE_DATA_ACCESS_LAYER_ISSUE);
            resultDomain.setMessageId(Messages.DATA_ACCESS_LAYER_ISSUE);
        }
        return resultDomain;
    }

    public BaseDomain UpdateAlarm(UserDomain userDomain, AlarmDomain alarmDomain) {

        BaseDomain resultDomain = new BaseDomain();
        try {
            if (userDomain.getToken() == null
                    || userDomain.getToken().isEmpty()) {

                resultDomain.setMessage(Messages.MESSAGE_DATA_VALIDATION_ERROR);
                resultDomain.setMessageId(Messages.DATA_VALIDATION_ERROR);
                return resultDomain;
            }
            UserRepository userRepository = new UserRepository();
            userDomain.setId(userRepository.GetUserId(userDomain.getToken()));
            if (userDomain.getId() == -1) {

                resultDomain.setMessage(Messages.MESSAGE_AUTHORIZATION_ERROR);
                resultDomain.setMessageId(Messages.AUTHORIZATION_ERROR);
                return resultDomain;
            }
            UserEntity userEntity = new UserEntity();
            AlarmEntity alarmEntity = new AlarmEntity();
            userEntity.setId(userDomain.getId());
            userEntity.setLatitude(userDomain.getLatitude());
            userEntity.setLongitude(userDomain.getLongitude());
            userEntity.setLocation(userDomain.getLocation());
            alarmEntity.setId(alarmDomain.getId());
            alarmEntity.setIsActive(alarmDomain.getIsActive());
            alarmEntity.setTime(alarmDomain.getTime());
            alarmEntity.setWeekDays(alarmDomain.getWeekDays());
            alarmEntity.setLocation(alarmDomain.getLocation());

            userRepository.UpdateAlarm(userEntity, alarmEntity);
            resultDomain.setMessage(Messages.MESSAGE_SUCCESFULL);
            resultDomain.setMessageId(Messages.SUCCESFULL);
        } catch (DatabaseException databaseException) {

            resultDomain = new BaseDomain();
            resultDomain.setMessage(Messages.MESSAGE_DATA_ACCESS_LAYER_ISSUE);
            resultDomain.setMessageId(Messages.DATA_ACCESS_LAYER_ISSUE);
        }
        return resultDomain;
    }

    public AlarmDomain IsActiveAlarm(AlarmDomain alarmDomain) {

        AlarmDomain resultDomain = new AlarmDomain();
        try {

            UserRepository userRepository = new UserRepository();
            AlarmEntity alarmEntity = new AlarmEntity();
            alarmEntity.setId(alarmDomain.getId());
            alarmEntity.setIsActive(alarmDomain.getIsActive());
            userRepository.IsActiveAlarm(alarmEntity);
            resultDomain.setMessage(Messages.MESSAGE_SUCCESFULL);
            resultDomain.setMessageId(Messages.SUCCESFULL);
        } catch (DatabaseException databaseException) {

            resultDomain = new AlarmDomain();
            resultDomain.setMessage(Messages.MESSAGE_DATA_ACCESS_LAYER_ISSUE);
            resultDomain.setMessageId(Messages.DATA_ACCESS_LAYER_ISSUE);
        }
        return resultDomain;
    }

    public BaseDomain DeleteAlarm(UserDomain userDomain, AlarmDomain alarmDomain) {

        BaseDomain resultDomain = new BaseDomain();
        try {
            if (userDomain.getToken() == null
                    || userDomain.getToken().isEmpty()) {

                resultDomain.setMessage(Messages.MESSAGE_DATA_VALIDATION_ERROR);
                resultDomain.setMessageId(Messages.DATA_VALIDATION_ERROR);
                return resultDomain;
            }
            UserRepository userRepository = new UserRepository();
            userDomain.setId(userRepository.GetUserId(userDomain.getToken()));
            if (userDomain.getId() == -1) {

                resultDomain.setMessage(Messages.MESSAGE_AUTHORIZATION_ERROR);
                resultDomain.setMessageId(Messages.AUTHORIZATION_ERROR);
                return resultDomain;
            }
            AlarmEntity alarmEntity = new AlarmEntity();
            UserEntity userEntity = new UserEntity();
            alarmEntity.setId(alarmDomain.getId());
            userEntity.setId(userDomain.getId());
            userRepository.DeleteAlarm(userEntity, alarmEntity);
            resultDomain.setMessage(Messages.MESSAGE_SUCCESFULL);
            resultDomain.setMessageId(Messages.SUCCESFULL);
        } catch (DatabaseException databaseException) {

            resultDomain = new BaseDomain();
            resultDomain.setMessage(Messages.MESSAGE_DATA_ACCESS_LAYER_ISSUE);
            resultDomain.setMessageId(Messages.DATA_ACCESS_LAYER_ISSUE);
        }
        return resultDomain;
    }

    public ArrayList<AlarmDomain> GetAlarms(UserDomain userDomain) {

        ArrayList<AlarmDomain> resultList = new ArrayList<>();
        AlarmDomain resultDomain = new AlarmDomain();
        try {
            UserRepository userRepository = new UserRepository();
            userDomain.setId(userRepository.CheckUserId(userDomain.getId()));
            if (userDomain.getId() == -1) {

                resultDomain.setMessage(Messages.MESSAGE_AUTHORIZATION_ERROR);
                resultDomain.setMessageId(Messages.AUTHORIZATION_ERROR);
                resultList.add(resultDomain);
                return resultList;
            }
            UserEntity userEntity = new UserEntity();
            userEntity.setId(userDomain.getId());
            ArrayList<AlarmEntity> alarmEntityList = userRepository.GetAlarms(userEntity);
            AlarmEntity helpEntity = null;
            Iterator<AlarmEntity> iterator = alarmEntityList.iterator();
            while (iterator.hasNext()) {

                helpEntity = iterator.next();
                resultDomain = new AlarmDomain();
                resultDomain.setId(helpEntity.getId());
                resultDomain.setIsActive(helpEntity.getIsActive());
                resultDomain.setTime(helpEntity.getTime());
                resultDomain.setLatitude(helpEntity.getLatitude());
                resultDomain.setLongitude(helpEntity.getLongitude());
                resultDomain.setWeekDays(helpEntity.getWeekDays());
                resultDomain.setMessage(Messages.MESSAGE_SUCCESFULL);
                resultDomain.setMessageId(Messages.SUCCESFULL);
                resultList.add(resultDomain);
            }
        } catch (DatabaseException databaseException) {

            resultList = new ArrayList<>();
            return resultList;

        }
        return resultList;
    }

    public UserDomain PhotoUpload(UserDomain userDomain) {

        UserDomain resultDomain = new UserDomain();
        try {
            if (userDomain.getPhotoName() == null
                    || userDomain.getToken() == null
                    || userDomain.getPhotoName().isEmpty()
                    || userDomain.getToken().isEmpty()) {

                resultDomain.setMessage(Messages.MESSAGE_DATA_VALIDATION_ERROR);
                resultDomain.setMessageId(Messages.DATA_VALIDATION_ERROR);
                return resultDomain;
            }
            UserRepository userRepository = new UserRepository();
            userDomain.setId(userRepository.GetUserId(userDomain.getToken()));
            if (userDomain.getId() == -1) {

                resultDomain.setMessage(Messages.MESSAGE_AUTHORIZATION_ERROR);
                resultDomain.setMessageId(Messages.AUTHORIZATION_ERROR);
                return resultDomain;
            }
            UserEntity userEntity = new UserEntity();
            userEntity.setId(userDomain.getId());
            userEntity.setPhotoName(userDomain.getPhotoName());
            UserEntity resultEntity = userRepository.PhotoUpload(userEntity);
            resultDomain.setMessage(Messages.MESSAGE_SUCCESFULL);
            resultDomain.setMessageId(Messages.SUCCESFULL);
        } catch (DatabaseException databaseException) {
            resultDomain = new UserDomain();
            resultDomain.setMessage(Messages.MESSAGE_DATA_ACCESS_LAYER_ISSUE);
            resultDomain.setMessageId(Messages.DATA_ACCESS_LAYER_ISSUE);
        }
        return resultDomain;
    }

    public ArrayList<UserDomain> GetSleepies(UserDomain userDomain, AlarmDomain alarmDomain) {

        ArrayList<UserDomain> resultList = new ArrayList<>();
        UserDomain resultDomain = new UserDomain();
        try {
            if (userDomain.getToken() == null
                    || userDomain.getFullName() == null
                    || userDomain.getToken().isEmpty()) {

                return resultList;
            }
            UserRepository userRepository = new UserRepository();
            userDomain.setId(userRepository.GetUserId(userDomain.getToken()));
            if (userDomain.getId() == -1) {

                return resultList;
            }

            UserEntity userEntity = new UserEntity();
            AlarmEntity alarmEntity = new AlarmEntity();
            userEntity.setGender(userDomain.getGender());
            userEntity.setPageNumber(userDomain.getPageNumber());
            userEntity.setDistance(userDomain.getDistance());
            userEntity.setLongitude(userDomain.getLongitude());
            userEntity.setLatitude(userDomain.getLatitude());
            userEntity.setFullName(userDomain.getFullName());
            userEntity.setId(userDomain.getId());
            alarmEntity.setTime(alarmDomain.getTime());
            Calendar calendar = GregorianCalendar.getInstance();
                alarmEntity.setCurrentTime(alarmDomain.getCurrentTime());
            alarmEntity.setWeekDay(alarmDomain.getWeekDay());
            ArrayList<UserEntity> userEntityList = userRepository.GetSleepies(userEntity, alarmEntity);
            UserEntity helpEntity = null;
            Iterator<UserEntity> iterator = userEntityList.iterator();
            while (iterator.hasNext()) {

                helpEntity = iterator.next();
                resultDomain = new UserDomain();
                resultDomain.setIsFriend(helpEntity.getIsFriend());
                resultDomain.setId(helpEntity.getId());
                resultDomain.setPageResult(helpEntity.getPageResult());
                resultDomain.setEmail(helpEntity.getEmail());
                resultDomain.setFullName(helpEntity.getFullName());
                resultDomain.setGender(helpEntity.getGender());
                resultDomain.setPhotoName(helpEntity.getPhotoName());
                resultDomain.setFirstTime(helpEntity.getFirstTime());
                resultDomain.setLongitude(helpEntity.getLongitude());
                resultDomain.setStatus(helpEntity.getStatus());

                resultDomain.setLatitude(helpEntity.getLatitude());
                resultDomain.setLocation(helpEntity.getLocation());
                resultDomain.setSipUsername(helpEntity.getSipUsername());
                resultDomain.setSipRegistrar(helpEntity.getSipRegistrar());
                resultDomain.setMessage(Messages.MESSAGE_SUCCESFULL);
                resultDomain.setMessageId(Messages.SUCCESFULL);
                
               WakeUpDomain wk = new WakeUpDomain();
               wk.setAcceptCallAmount(helpEntity.getWakeupEntity().getAcceptCallAmount());
               wk.setIncomingCallAmount(helpEntity.getWakeupEntity().getIncomingCallAmount());
               wk.setOutgoingCallAmount(helpEntity.getWakeupEntity().getOutgoingCallAmount());
               resultDomain.setWakeupDomain(wk);
                
                resultList.add(resultDomain);
            }
        } catch (DatabaseException databaseException) {

            resultList = new ArrayList<>();
            return resultList;
        }
        return resultList;
    }

    public AlarmDomain GetNearestAlarm(UserDomain userDomain, AlarmDomain alarmDomain) {

        AlarmDomain resultDomain = new AlarmDomain();
        try {
            if (userDomain.getToken() == null
                    || userDomain.getToken().isEmpty()) {
                resultDomain.setMessage(Messages.MESSAGE_AUTHORIZATION_ERROR);
                resultDomain.setMessageId(Messages.AUTHORIZATION_ERROR);
                return resultDomain;
            }

            UserRepository userRepository = new UserRepository();
            userDomain.setId(userRepository.GetUserId(userDomain.getToken()));
            if (userDomain.getId() == -1) {
                resultDomain.setMessage(Messages.MESSAGE_AUTHORIZATION_ERROR);
                resultDomain.setMessageId(Messages.AUTHORIZATION_ERROR);
                return resultDomain;
            }

            UserEntity userEntity = new UserEntity();
            AlarmEntity alarmEntity = new AlarmEntity();

            userEntity.setId(userDomain.getId());

            Calendar calendar = GregorianCalendar.getInstance();
            alarmEntity.setCurrentTime(alarmDomain.getCurrentTime());
            alarmEntity.setWeekDay(alarmDomain.getWeekDay());
            AlarmEntity resultEntity = userRepository.GetNearestAlarm(userEntity, alarmEntity);

            resultDomain = new AlarmDomain();
            resultDomain.setTime(resultEntity.getFirstTime());
            resultDomain.setMessage(Messages.MESSAGE_SUCCESFULL);
            resultDomain.setMessageId(Messages.SUCCESFULL);

        } catch (DatabaseException databaseException) {

            resultDomain = new AlarmDomain();
            resultDomain.setMessage(Messages.MESSAGE_DATA_ACCESS_LAYER_ISSUE);
            resultDomain.setMessageId(Messages.DATA_ACCESS_LAYER_ISSUE);
            return resultDomain;
        }
        return resultDomain;
    }

    public UserDomain GetUserBySipId(UserDomain userDomain) {

        UserDomain resultDomain = new UserDomain();
        try {
            UserRepository userRepository = new UserRepository();
            UserEntity userEntity = new UserEntity();
            userEntity.setSipUsername(userDomain.getSipUsername());
            UserEntity resultEntity = userRepository.GetUserBySipId(userEntity);
            resultDomain.setId(resultEntity.getId());
            resultDomain.setFullName(resultEntity.getFullName());
            resultDomain.setPhotoName(resultEntity.getPhotoName());
            resultDomain.setMessage(Messages.MESSAGE_SUCCESFULL);
            resultDomain.setMessageId(Messages.SUCCESFULL);

        } catch (DatabaseException databaseException) {
            resultDomain = new UserDomain();
            resultDomain.setMessage(Messages.MESSAGE_DATA_ACCESS_LAYER_ISSUE);
            resultDomain.setMessageId(Messages.DATA_ACCESS_LAYER_ISSUE);
            return resultDomain;
        }

        return resultDomain;
    }

    public ArrayList<UserDomain> RefreshSleepies(UserDomain userDomain, AlarmDomain alarmDomain) {

        ArrayList<UserDomain> resultList = new ArrayList<>();
        UserDomain resultDomain = new UserDomain();
        try {
            if (userDomain.getToken() == null
                    || userDomain.getToken().isEmpty()) {
                return resultList;
            }
            UserRepository userRepository = new UserRepository();
            userDomain.setId(userRepository.GetUserId(userDomain.getToken()));
            if (userDomain.getId() == -1) {
                return resultList;
            }

            UserEntity userEntity = new UserEntity();
            AlarmEntity alarmEntity = new AlarmEntity();
            userEntity.setId(userDomain.getId());
            userEntity.setFullName(userDomain.getFullName());
            userEntity.setPageNumber(userDomain.getPageNumber());
            userEntity.setDistance(userDomain.getDistance());
            userEntity.setLongitude(userDomain.getLongitude());
            userEntity.setLatitude(userDomain.getLatitude());
            alarmEntity.setTime(alarmDomain.getTime());
            Calendar calendar = GregorianCalendar.getInstance();
            alarmEntity.setCurrentTime(alarmDomain.getCurrentTime());
            alarmEntity.setWeekDay(alarmDomain.getWeekDay());
            ArrayList<UserEntity> userEntityList = userRepository.RefreshSleepies(userEntity, alarmEntity);
            UserEntity helpEntity = null;
            Iterator<UserEntity> iterator = userEntityList.iterator();
            while (iterator.hasNext()) {

                helpEntity = iterator.next();
                resultDomain = new UserDomain();
                resultDomain.setId(helpEntity.getId());
                resultDomain.setIsFriend(helpEntity.getIsFriend());
                resultDomain.setPageResult(helpEntity.getPageResult());
                resultDomain.setEmail(helpEntity.getEmail());
                resultDomain.setFullName(helpEntity.getFullName());
                resultDomain.setGender(helpEntity.getGender());
                resultDomain.setStatus(helpEntity.getStatus());

                resultDomain.setPhotoName(helpEntity.getPhotoName());
                resultDomain.setFirstTime(helpEntity.getFirstTime());
                resultDomain.setLongitude(helpEntity.getLongitude());
                resultDomain.setLocation(helpEntity.getLocation());
                resultDomain.setLatitude(helpEntity.getLatitude());
                resultDomain.setSipUsername(helpEntity.getSipUsername());
                resultDomain.setSipRegistrar(helpEntity.getSipRegistrar());
                resultDomain.setMessage(Messages.MESSAGE_SUCCESFULL);
                resultDomain.setMessageId(Messages.SUCCESFULL);
                resultList.add(resultDomain);
            }
        } catch (DatabaseException databaseException) {

            resultList = new ArrayList<>();
            return resultList;
        }
        return resultList;
    }

    public ArrayList<CountryDomain> GetCountries() {

        ArrayList<CountryDomain> resultList = new ArrayList<>();
        CountryDomain resultDomain = new CountryDomain();
        try {

            UserRepository userRepository = new UserRepository();
            ArrayList<UserEntity> userEntityList = userRepository.GetCountries();
            Iterator<UserEntity> iterator = userEntityList.iterator();
            UserEntity helpEntity = null;
            while (iterator.hasNext()) {

                helpEntity = iterator.next();
                resultDomain = new CountryDomain();
                resultDomain.setCountryId(helpEntity.getCountryId());
                resultDomain.setCountryName(helpEntity.getCountryName());
                resultDomain.setMessageId(Messages.SUCCESFULL);
                resultDomain.setMessage(Messages.MESSAGE_SUCCESFULL);
                resultList.add(resultDomain);
            }
        } catch (DatabaseException databaseException) {

            resultList = new ArrayList<>();
        }
        return resultList;

    }

    public UserDomain MakeFriend(Objects objects) {

        UserDomain userDomain1 = objects.getUserDomains().get(0);
        UserDomain userDomain2 = objects.getUserDomains().get(1);

        UserDomain resultDomain = new UserDomain();
        try {
            UserRepository userRepository = new UserRepository();
            if (userRepository.GetUserId(userDomain1.getToken()) == -1) {

                resultDomain.setMessage(Messages.MESSAGE_AUTHORIZATION_ERROR);
                resultDomain.setMessageId(Messages.AUTHORIZATION_ERROR);
                return resultDomain;
            }

            UserEntity userEntity1 = new UserEntity();
            userEntity1.setId(userDomain1.getId());
            UserEntity userEntity2 = new UserEntity();
            userEntity2.setId(userDomain2.getId());

            UserEntity resultEntity = userRepository.MakeFriend(userEntity1, userEntity2);

            resultDomain.setId(resultEntity.getId());
            resultDomain.setPhotoName(resultEntity.getPhotoName());
            resultDomain.setMessage(Messages.MESSAGE_SUCCESFULL);
            resultDomain.setMessageId(Messages.SUCCESFULL);
        } catch (DatabaseException databaseException) {

            resultDomain = new UserDomain();
            resultDomain.setMessage(Messages.MESSAGE_DATA_ACCESS_LAYER_ISSUE);
            resultDomain.setMessageId(Messages.DATA_ACCESS_LAYER_ISSUE);
            return resultDomain;
        }
        return resultDomain;
    }

    public UserDomain DeleteFriend(Objects objects) {

        UserDomain userDomain1 = objects.getUserDomains().get(0);
        UserDomain userDomain2 = objects.getUserDomains().get(1);

        UserDomain resultDomain = new UserDomain();
        try {

            UserRepository userRepository = new UserRepository();
            if (userRepository.GetUserId(userDomain1.getToken()) == -1) {

                resultDomain.setMessage(Messages.MESSAGE_AUTHORIZATION_ERROR);
                resultDomain.setMessageId(Messages.AUTHORIZATION_ERROR);
                return resultDomain;
            }
            UserEntity userEntity1 = new UserEntity();
            userEntity1.setId(userDomain1.getId());
            UserEntity userEntity2 = new UserEntity();
            userEntity2.setId(userDomain2.getId());

            UserEntity resultEntity = userRepository.DeleteFriend(userEntity1, userEntity2);
            resultDomain.setMessage(Messages.MESSAGE_SUCCESFULL);
            resultDomain.setMessageId(Messages.SUCCESFULL);
        } catch (DatabaseException databaseException) {

            resultDomain = new UserDomain();
            resultDomain.setMessage(Messages.MESSAGE_DATA_ACCESS_LAYER_ISSUE);
            resultDomain.setMessageId(Messages.DATA_ACCESS_LAYER_ISSUE);
            return resultDomain;
        }
        return resultDomain;
    }

    public ArrayList<UserDomain> GetFriends(UserDomain userDomain, AlarmDomain alarmDomain) {

        ArrayList<UserDomain> resultList = new ArrayList<>();
        UserDomain resultDomain = new UserDomain();
        try {
            if (userDomain.getToken() == null
                    || userDomain.getToken().isEmpty()) {
//                resultDomain.setMessage(Messages.MESSAGE_DATA_VALIDATION_ERROR);
//                resultDomain.setMessageId(Messages.DATA_VALIDATION_ERROR);
                return resultList;
            }
            UserRepository userRepository = new UserRepository();
            userDomain.setId(userRepository.GetUserId(userDomain.getToken()));
            if (userDomain.getId() == -1) {

//                resultDomain.setMessage(Messages.MESSAGE_AUTHORIZATION_ERROR);
//                resultDomain.setMessageId(Messages.AUTHORIZATION_ERROR);
                return resultList;
            }

            UserEntity userEntity = new UserEntity();
            AlarmEntity alarmEntity = new AlarmEntity();
            userEntity.setId(userDomain.getId());
            alarmEntity.setTime(6000);
            Calendar calendar = GregorianCalendar.getInstance();
                alarmEntity.setCurrentTime(alarmDomain.getCurrentTime());
            alarmEntity.setWeekDay(alarmDomain.getWeekDay());
            ArrayList<UserEntity> userEntityList = userRepository.GetFriends(userEntity, alarmEntity);
            UserEntity helpEntity = null;
            Iterator<UserEntity> iterator = userEntityList.iterator();

            while (iterator.hasNext()) {

                helpEntity = iterator.next();
                resultDomain = new UserDomain();
                resultDomain.setId(helpEntity.getId());
                resultDomain.setEmail(helpEntity.getEmail());
                resultDomain.setFullName(helpEntity.getFullName());
                resultDomain.setGender(helpEntity.getGender());
                resultDomain.setPhotoName(helpEntity.getPhotoName());
                resultDomain.setIsActive(helpEntity.getIsActive());
                resultDomain.setFirstTime(helpEntity.getFirstTime());
                resultDomain.setStatus(helpEntity.getStatus());
                resultDomain.setLongitude(helpEntity.getLongitude());
                resultDomain.setLocation(helpEntity.getLocation());
                resultDomain.setLatitude(helpEntity.getLatitude());
                resultDomain.setSipUsername(helpEntity.getSipUsername());
                resultDomain.setSipRegistrar(helpEntity.getSipRegistrar());
                resultDomain.setIsActive(helpEntity.getIsActive());
                resultDomain.setTimeDifference(helpEntity.getTimeDifference());
                resultDomain.setWeekDay(helpEntity.getWeekDay());
                resultDomain.setMessage(Messages.MESSAGE_SUCCESFULL);
                resultDomain.setMessageId(Messages.SUCCESFULL);
                
               WakeUpDomain wk = new WakeUpDomain();
               wk.setAcceptCallAmount(helpEntity.getWakeupEntity().getAcceptCallAmount());
               wk.setIncomingCallAmount(helpEntity.getWakeupEntity().getIncomingCallAmount());
               wk.setOutgoingCallAmount(helpEntity.getWakeupEntity().getOutgoingCallAmount());
               resultDomain.setWakeupDomain(wk);
                
                resultList.add(resultDomain);
            }
        } catch (DatabaseException databaseException) {

            resultList = new ArrayList<>();
        }
        return resultList;
    }
    
    
        public int UpdateIncomingcall(UserDomain userDomain) {

        UserDomain resultDomain = new UserDomain(); 
        UserRepository userRepository = new UserRepository();
 
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDomain.getId());
        try {
            return userRepository.UpdateIncomingCall(userEntity);
            
        } catch (DatabaseException databaseException) {
            
            return -1;
        }
        
    }
    
       public int UpdateAcceptcall(UserDomain userDomain) {

        UserDomain resultDomain = new UserDomain(); 
        UserRepository userRepository = new UserRepository();
 
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDomain.getId());
        try {
            return userRepository.UpdateAcceptCall(userEntity);
            
        } catch (DatabaseException databaseException) {
            
            return -1;
        }
        
    }
       public int UpdateOutgoingcall(UserDomain userDomain) {

        UserDomain resultDomain = new UserDomain(); 
        UserRepository userRepository = new UserRepository();
 
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDomain.getId());
        try {
            return userRepository.UpdateOutgoingCall(userEntity);
            
        } catch (DatabaseException databaseException) {
            
            return -1;
        }
       }
}

