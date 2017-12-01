/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SeriveLayer.Controller;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.util.ArrayList;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import org.apache.commons.net.ftp.FTPClient;

import SeriveLayer.Domain.*;
import BusinessLayer.Workflow.*;
import SeriveLayer.Message.*;
import java.nio.file.spi.FileTypeDetector;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.apache.commons.codec.binary.Base64;
import org.glassfish.jersey.internal.util.Base64;
import java.util.Random;
import javax.xml.bind.DatatypeConverter;
import javax.xml.datatype.DatatypeConstants;

/**
 * REST Web Service
 *
 * @author Eldar Hasanov
 */
@Path("user")
public class UserController {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UserController
     */
    public UserController() {
    }

    @POST
    @Path("updateincomingcall")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public BaseDomain UpdateIncomingcall(UserDomain userDomain) {

        UserWorkflow userWorkflow = new UserWorkflow();
        BaseDomain us = new BaseDomain();
        us.setId(userWorkflow.UpdateIncomingcall(userDomain));
        return us;
    }

    @POST
    @Path("updateacceptcall")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public BaseDomain UpdateAcceptcall(UserDomain userDomain) {

        UserWorkflow userWorkflow = new UserWorkflow();
        BaseDomain us = new BaseDomain();
        us.setId(userWorkflow.UpdateAcceptcall(userDomain));
        return us;
    }

    @POST
    @Path("updateoutgoingcall")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public BaseDomain UpdateOutgoingcall(UserDomain userDomain) {

        UserWorkflow userWorkflow = new UserWorkflow();
        BaseDomain us = new BaseDomain();
        us.setId(userWorkflow.UpdateOutgoingcall(userDomain));
        return us;
    }
    
    @POST
    @Path("getcalldata")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public WakeUpDomain GetCallData(UserDomain userDomain) {

        UserWorkflow userWorkflow = new UserWorkflow();
        WakeUpDomain resultDomain = userWorkflow.GetCallData(userDomain);
        return resultDomain;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserDomain testGet() {

        UserDomain u = new UserDomain();
        u.setEmail("mr.Eldarhasanov@mail.ru");
        return u;
    }

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserDomain Login(UserDomain userDomain) {

        UserWorkflow userWorkflow = new UserWorkflow();
        UserDomain resultDomain = userWorkflow.Login(userDomain);
        return resultDomain;
    }

    @POST
    @Path("logout")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserDomain Logout(UserDomain userDomain) {

        UserWorkflow userWorkflow = new UserWorkflow();
        UserDomain resultDomain = userWorkflow.Logout(userDomain);
        return resultDomain;
    }

    @POST
    @Path("registration")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserDomain Registration(UserDomain userDomain) {

        UserWorkflow userWorkflow = new UserWorkflow();
        UserDomain resultDomain = userWorkflow.Registration(userDomain);
        return resultDomain;
    }

    @POST
    @Path("getuser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserDomain GetUser(UserDomain userDomain) {

        UserWorkflow userWorkflow = new UserWorkflow();
        UserDomain resultDomain = userWorkflow.GetUser(userDomain);
        return resultDomain;
    }

    @POST
    @Path("getuserbytoken")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserDomain GetUserByToken(UserDomain userDomain) {

        UserWorkflow userWorkflow = new UserWorkflow();
        UserDomain resultDomain = userWorkflow.GetUserByToken(userDomain);
        return resultDomain;
    }

    @POST
    @Path("updateuser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserDomain UpdateUser(UserDomain userDomain) {

        UserWorkflow userWorkflow = new UserWorkflow();
        UserDomain resultDomain = userWorkflow.UpdateUser(userDomain);
        return resultDomain;
    }

    @POST
    @Path("photoupload")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserDomain PhotoUpload(UserDomain userDomain) {

        UserDomain resultDomain = new UserDomain();
        try {
            Random random = new Random();
            long nx = random.nextLong();
            String FileLocation = "/opt/glassfish4/glassfish/domains/domain1/applications/OyanFtp/"
                    + userDomain.getPhotoName() + nx + ".jpeg";
            OutputStream out = new FileOutputStream(new File(FileLocation));
            byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(userDomain.getPhotoString().getBytes());
            out.write(bytes);
            out.flush();
            out.close();
            userDomain.setPhotoName(userDomain.getPhotoName() +nx + ".jpeg");
            UserWorkflow userWorkflow = new UserWorkflow();
            resultDomain = userWorkflow.PhotoUpload(userDomain);
            if (resultDomain.getMessageId() == Messages.SUCCESFULL) {
                resultDomain.setPhotoName(userDomain.getPhotoName());
            }
        } catch (IOException iOException) {

            resultDomain.setMessageId(Messages.PHOTO_UPLOAD_ERROR);
            resultDomain.setMessage(Messages.MESSAGE_PHOTO_UPLOAD_ERROR);
        }

        return resultDomain;
    }
    /*
     public void SavePhoto(String file, String uploadedFileLocation) throws IOException {

     try {
            
     FTPClient fTPClient = new FTPClient();
     fTPClient.connect("10.50.93.117", 40);
     fTPClient.login("eldar", "more");
     fTPClient.setFileType(FTPClient.BINARY_FILE_TYPE);
     fTPClient.setFileTransferMode(FTPClient.BINARY_FILE_TYPE);
     System.out.println(fTPClient.getReplyString());
     fTPClient.storeFile(fileName + ".jpg", uploadedInputStream);
     fTPClient.logout();
     fTPClient.disconnect();
             



     } catch (IOException e) {
     throw new IOException();
     }
     }
     */

//    @POST
//    @Path("photodownload")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public UserDomain PhotoDownload(UserDomain userDomain) {
//
//        UserDomain resultDomain = new UserDomain();
//        try {
//            String FileLocation = "C:\\Users\\Eldar\\Documents\\NetBeansProjects\\OyanFtp\\src\\main\\webapp\\"
//                    + userDomain.getPhotoName() + ".jpeg";
//            byte[] bytes = new byte[100000000];
//            InputStream inputStream = new FileInputStream(new File(FileLocation));
//            inputStream.read(bytes);
//            resultDomain.setPhotoString(Base64.encodeAsString(bytes));
//        } catch (IOException iOException) {
//        }
//        return resultDomain;
//    }
    @POST
    @Path("addalarm")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public BaseDomain AddAlarm(Objects objects) {

        AlarmDomain alarmDomain = objects.getAlarmDomain();
        UserDomain userDomain = objects.getUserDomain();
        UserWorkflow userWorkflow = new UserWorkflow();
        BaseDomain resultDomain = userWorkflow.AddAlarm(userDomain, alarmDomain);
        return resultDomain;
    }

    @POST
    @Path("updatealarm")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public BaseDomain UpdateAlarm(Objects objects) {

        AlarmDomain alarmDomain = objects.getAlarmDomain();
        UserDomain userDomain = objects.getUserDomain();
        UserWorkflow userWorkflow = new UserWorkflow();
        BaseDomain resultDomain = userWorkflow.UpdateAlarm(userDomain, alarmDomain);
        return resultDomain;
    }

    @POST
    @Path("deletealarm")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public BaseDomain DeleteAlarm(Objects objects) {

        AlarmDomain alarmDomain = objects.getAlarmDomain();
        UserDomain userDomain = objects.getUserDomain();
        UserWorkflow userWorkflow = new UserWorkflow();
        BaseDomain resultDomain = userWorkflow.DeleteAlarm(userDomain, alarmDomain);
        return resultDomain;

    }

    @POST
    @Path("getalarms")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Objects GetAlarms(UserDomain userDomain) {

        UserWorkflow userWorkflow = new UserWorkflow();
        ArrayList<AlarmDomain> resultList = userWorkflow.GetAlarms(userDomain);
        Objects objects = new Objects();
        objects.setAlarmDomains(resultList);
        return objects;
    }

    @POST
    @Path("getsleepies")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Objects GetSleepies(Objects objects) {

        UserDomain userDomain = objects.getUserDomain();
        AlarmDomain alarmDomain = objects.getAlarmDomain();
        UserWorkflow userWorkflow = new UserWorkflow();
        ArrayList<UserDomain> resultList = userWorkflow.GetSleepies(userDomain, alarmDomain);
        Objects resultObjects = new Objects();
        resultObjects.setUserDomains(resultList);
        return resultObjects;
    }
    
    @POST
    @Path("getnearestalarm")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AlarmDomain GetNearestAlarm(Objects objects) {

        UserDomain userDomain = objects.getUserDomain();
        AlarmDomain alarmDomain = objects.getAlarmDomain();
        UserWorkflow userWorkflow = new UserWorkflow();
        AlarmDomain resultDomain = userWorkflow.GetNearestAlarm(userDomain, alarmDomain);
       
        return resultDomain;
    }
    
    @POST
    @Path("getuserbysipid")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserDomain GetUserBySipId(UserDomain userDomain) {

        UserWorkflow userWorkflow = new UserWorkflow();
        UserDomain resultDomain = userWorkflow.GetUserBySipId(userDomain);
        return resultDomain;
    }

    @POST
    @Path("isactivealarm")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AlarmDomain IsActiveAlarm(AlarmDomain alarmDomain) {

        UserWorkflow userWorkflow = new UserWorkflow();
        AlarmDomain resultDomain = userWorkflow.IsActiveAlarm(alarmDomain);
        return resultDomain;
    }

    @POST
    @Path("getcountries")
    public Objects GetCountries() {

        UserWorkflow userWorkflow = new UserWorkflow();
        ArrayList<CountryDomain> resultList = userWorkflow.GetCountries();
        Objects objects = new Objects();
        objects.setCountryDomains(resultList);
        return objects;
    }

    @POST
    @Path("refreshsleepies")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Objects RefreshSleepies(Objects objects) {

        UserDomain userDomain = objects.getUserDomain();
        AlarmDomain alarmDomain = objects.getAlarmDomain();
        UserWorkflow userWorkflow = new UserWorkflow();
        ArrayList<UserDomain> resultList = userWorkflow.RefreshSleepies(userDomain, alarmDomain);
        Objects resultObjects = new Objects();
        resultObjects.setUserDomains(resultList);
        return resultObjects;
    }

    @POST
    @Path("makefriend")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserDomain MakeFriend(Objects objects) {

        UserWorkflow userWorkflow = new UserWorkflow();
        UserDomain userDomain = userWorkflow.MakeFriend(objects);
        return userDomain;
    }

    @POST
    @Path("deletefriend")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserDomain DeleteFriend(Objects objects) {

        UserWorkflow userWorkflow = new UserWorkflow();
        UserDomain userDomain = userWorkflow.DeleteFriend(objects);
        return userDomain;
    }

    @POST
    @Path("getfriends")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Objects GetFriends(Objects objects) {

        UserDomain userDomain = objects.getUserDomain();
        AlarmDomain alarmDomain = objects.getAlarmDomain();
        UserWorkflow userWorkflow = new UserWorkflow();
        ArrayList<UserDomain> resultList = userWorkflow.GetFriends(userDomain, alarmDomain);
        Objects resultObjects = new Objects();
        resultObjects.setUserDomains(resultList);
        return resultObjects;
    }
}
