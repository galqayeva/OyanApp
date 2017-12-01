
package com.example.android.oyan.Domain;


import java.util.ArrayList;

public class Objects {

    private AlarmDomain alarmDomain;
    private BaseDomain baseDomain;
    private UserDomain userDomain;
    private ArrayList<CountryDomain> countryDomains;
    private ArrayList<AlarmDomain> alarmDomains;
    private ArrayList<UserDomain> userDomains;

    public ArrayList<UserDomain> getUserDomains() {
        return userDomains;
    }

    public void setUserDomains(ArrayList<UserDomain> userDomains) {
        this.userDomains = userDomains;
    }

    public ArrayList<AlarmDomain> getAlarmDomains() {
        return alarmDomains;
    }

    public void setAlarmDomains(ArrayList<AlarmDomain> alarmDomains) {
        this.alarmDomains = alarmDomains;
    }

    public ArrayList<CountryDomain> getCountryDomains() {
        return countryDomains;
    }

    public void setCountryDomains(ArrayList<CountryDomain> countryDomains) {
        this.countryDomains = countryDomains;
    }

    public AlarmDomain getAlarmDomain() {
        return alarmDomain;
    }

    public void setAlarmDomain(AlarmDomain alarmDomain) {
        this.alarmDomain = alarmDomain;
    }

    public BaseDomain getBaseDomain() {
        return baseDomain;
    }

    public void setBaseDomain(BaseDomain baseDomain) {
        this.baseDomain = baseDomain;
    }

    public UserDomain getUserDomain() {
        return userDomain;
    }

    public void setUserDomain(UserDomain userDomain) {
        this.userDomain = userDomain;
    }
}
