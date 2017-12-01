/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLayer.Entity;

import SeriveLayer.Domain.WakeUpDomain;

/**
 *
 * @author Eldar Hasanov
 */
public class UserEntity extends BaseEntity {

    private String email;
    private String password;
    private String fullName;
    private String gender;
    private String photoName;
    private String countryName;
    private long countryId;
    private long sipUsername;
    private String sipRegistrar;
    private String sipPassword;
    private long distance;
    private long isFriend;
    private String status;
    private WakeUpEntity wakeupEntity;

    public WakeUpEntity getWakeupEntity() {
        return wakeupEntity;
    }

    public void setWakeupEntity(WakeUpEntity wakeupEntity) {
        this.wakeupEntity = wakeupEntity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public long getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(long isFriend) {
        this.isFriend = isFriend;
    }
    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }
    public long getSipUsername() {
        return sipUsername;
    }

    public void setSipUsername(long sipUsername) {
        this.sipUsername = sipUsername;
    }

    public String getSipRegistrar() {
        return sipRegistrar;
    }

    public void setSipRegistrar(String sipRegistrar) {
        this.sipRegistrar = sipRegistrar;
    }

    public String getSipPassword() {
        return sipPassword;
    }

    public void setSipPassword(String sipPassword) {
        this.sipPassword = sipPassword;
    }

}
