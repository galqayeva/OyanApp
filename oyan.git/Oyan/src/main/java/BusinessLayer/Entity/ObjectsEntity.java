/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLayer.Entity;
 
import java.util.ArrayList;

/**
 *
 * @author Eldar
 */
public class ObjectsEntity {

    public AlarmEntity getAlarmEntity() {
        return alarmEntity;
    }

    public void setAlarmEntity(AlarmEntity alarmEntity) {
        this.alarmEntity = alarmEntity;
    }

    public BaseEntity getBaseEntity() {
        return baseEntity;
    }

    public void setBaseEntity(BaseEntity baseEntity) {
        this.baseEntity = baseEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public ArrayList<AlarmEntity> getAlarmEntitys() {
        return alarmEntitys;
    }

    public void setAlarmEntitys(ArrayList<AlarmEntity> alarmEntitys) {
        this.alarmEntitys = alarmEntitys;
    }

    public ArrayList<UserEntity> getUserEntitys() {
        return userEntitys;
    }

    public void setUserEntitys(ArrayList<UserEntity> userEntitys) {
        this.userEntitys = userEntitys;
    }
    
    private AlarmEntity alarmEntity;
    private BaseEntity baseEntity;
    private UserEntity userEntity; 
    private ArrayList<AlarmEntity> alarmEntitys;
    private ArrayList<UserEntity> userEntitys;
}
