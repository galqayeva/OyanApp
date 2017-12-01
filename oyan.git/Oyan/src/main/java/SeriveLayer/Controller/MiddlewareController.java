/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SeriveLayer.Controller;

import DataAccessLayer.Constant.Constants;
import DataAccessLayer.Repository.MiddlewareRepository;
import SeriveLayer.Domain.MiddlewareModels.*;
import SeriveLayer.Message.Messages;
import com.notnoop.apns.*;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * REST Web Service
 *
 * @author Orkhan Alikhanov
 */
@Path("middleware")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MiddlewareController {

    @Context
    private UriInfo context;

    private int currentUserID;

    private static final ConcurrentHashMap<String, myType> cachedCalls = new ConcurrentHashMap<String, myType>();
    private static final int maxWaitAmount = 10000; //milliseconds
    private static final int sleepAmount = 100; //milliseconds
    private static final int removeTime = 15000; //milliseconds
    private static final int checkOnlyFirst = 10;
    private static final int INVALID_USER_ID = -1;

    @POST
    @Path("updatedeviceinfo")
    public MiddlewareResponseModel UpdateDeviceInfo(DeviceInfoMiddlewareModel model) {
        if (NotAuthorized(model)) {
            return MiddlewareResponseModel.NotAuthorized();
        }
        return newRepo().UpdateDeviceInfo(model);
    }

    @POST
    @Path("readyforcall")
    public MiddlewareResponseModel ReadyForCall(ReadyForCallModel model) {
        if (NotAuthorized(model)) {
            return MiddlewareResponseModel.NotAuthorized();
        }

        cachedCalls.put(model.uniqueKey, new myType(model.available, System.currentTimeMillis()));
        return MiddlewareResponseModel.Success();
    }

    @POST
    @Path("makecallto")
    @SuppressWarnings("SleepWhileInLoop")
    //intented to be called only by freeswitch
    //TODO: check authentication of freeswitch.
    public boolean MakeCallTo(FreeSWITCHCallModel model) {
        removeOldCaches(); // remove old ones
        DeviceInfoMiddlewareModel deviceInfo = new MiddlewareRepository().GetDeviceInfoBySipId(model.SipId);
        String uniqueKey = "";
        switch (deviceInfo.platformId) {
            case Constants.ANDROID:
                //TODO: should be implemeted for anrdoid
                return true;
            case Constants.IOS:
                uniqueKey = wakeUpIOSDevice(deviceInfo.pushToken, deviceInfo.sandbox);
                break;
            default:
                break;
        }

        if (!uniqueKey.isEmpty()) {
            long startTime = System.currentTimeMillis();
            do {
                sleep(sleepAmount);
                myType cache = cachedCalls.get(uniqueKey);
                if (cache != null) {
                    cachedCalls.remove(uniqueKey); // remove from cache
                    return cache.available;
                }
            } while (System.currentTimeMillis() - startTime < maxWaitAmount);
        }
        return false;
    }

    //TODO: decide when to call this method
    private static void removeOldCaches() {
        Iterator<Entry<String, myType>> it = cachedCalls.entrySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            Entry<String, myType> cache = it.next();
            i++;

            if (System.currentTimeMillis() - cache.getValue().time > removeTime) {
                it.remove();
                continue;
            }

            if (i == checkOnlyFirst) {
                break;
            }
        }
    }

    private Boolean NotAuthorized(BaseMiddlewareModel model) {
        currentUserID = new MiddlewareRepository().GetUserIdFromToken(model.token);
        return currentUserID == INVALID_USER_ID;
    }

    private String wakeUpIOSDevice(String token, boolean sandbox) {
        final String uniqueKey = UUID.randomUUID().toString();
        String payload = APNS.newPayload()
                .customField("type", "VoIPWakeUp") //for fun
                .customField("uniqueKey", uniqueKey)
                .build();

        ApnsServiceBuilder builder = APNS.newService()
                .withCert(this.getClass().getClassLoader().getResourceAsStream("apple_push_notification.p12"), "orxan12");
        if (sandbox) {
            builder.withSandboxDestination();
        } else {
            builder.withProductionDestination();
        }
        ApnsService service = builder.build();

        service.push(token, payload);
        return uniqueKey;
    }

    private void sleep(long sleepAmount) {
        try {
            Thread.sleep(sleepAmount);
        } catch (InterruptedException ex) {
            //ignore
        }
    }

    private MiddlewareRepository newRepo() {
        return new MiddlewareRepository(currentUserID);
    }

    @GET
    @Path("test")
    public MiddlewareResponseModel ss() {
        return MiddlewareResponseModel.DatabaseError();
    }

}
