/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SeriveLayer.Domain.MiddlewareModels;

import SeriveLayer.Message.Messages;

/**
 *
 * @author Orkhan
 */
public class MiddlewareResponseModel {
    public int messageId;
    public String message;
    public Object data;

    public static MiddlewareResponseModel Success() {
        return create(Messages.SUCCESFULL, Messages.MESSAGE_SUCCESFULL);
    }

    public static MiddlewareResponseModel NotAuthorized() {
        return create(Messages.AUTHORIZATION_ERROR, Messages.MESSAGE_AUTHORIZATION_ERROR);
    }

    public static MiddlewareResponseModel DatabaseError() {
        return create(Messages.DATA_ACCESS_LAYER_ISSUE, Messages.MESSAGE_DATA_ACCESS_LAYER_ISSUE);
    }
    
    public static MiddlewareResponseModel DataAccessError() {
        return create(Messages.DATA_ACCESS_LAYER_ISSUE, Messages.MESSAGE_DATA_ACCESS_LAYER_ISSUE);
    }
    private static MiddlewareResponseModel create(int msgId, String msg) {
        MiddlewareResponseModel result = new MiddlewareResponseModel();
        result.messageId = msgId;
        result.message = msg;
        return result;
    }
}
