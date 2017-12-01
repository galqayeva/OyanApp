/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SeriveLayer.Domain.MiddlewareModels;

/**
 *
 * @author Orkhan
 */
public class DeviceInfoMiddlewareModel extends BaseMiddlewareModel {
    public String name;
    public int platformId;
    public String pushToken;
    public String osVersion;
    public String appVersion;
    public boolean sandbox = false;
}
