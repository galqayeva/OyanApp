
package com.example.android.oyan.Domain;

import java.io.Serializable;

public class UserDomain extends BaseDomain implements Serializable {

    private String email;
    private String password;
    private String fullName;
    private String gender;
    private String photoName;
    private String countryName;
    private long countryId;
    private double latitude;
    private double longitude;
    private String photoString;
    private long distance;
    private long sipUsername;
    private String sipPassword;
    private String sipRegistrar;
    private long isFriend;
    private String status;


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

    public long getSipUsername() {
        return sipUsername;
    }

    public void setSipUsername(long sipUsername) {
        this.sipUsername = sipUsername;
    }

    public String getSipPassword() {
        return sipPassword;
    }

    public void setSipPassword(String sipPassword) {
        this.sipPassword = sipPassword;
    }

    public String getSipRegistrar() {
        return sipRegistrar;
    }

    public void setSipRegistrar(String sipRegistrar) {
        this.sipRegistrar = sipRegistrar;
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPhotoString() {
        return photoString;
    }

    public void setPhotoString(String photoString) {
        this.photoString = photoString;
    }

}
