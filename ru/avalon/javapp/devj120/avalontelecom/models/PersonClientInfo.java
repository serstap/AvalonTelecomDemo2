package ru.avalon.javapp.devj120.avalontelecom.models;

public class PersonClientInfo extends ClientInfo {

    private String birthday;
    private String nameContact;
    private String nameDirector;

    public PersonClientInfo(PhoneNumber phoneNumber, String name, String address, String birthday) {
        super(phoneNumber, name, address);
        this.birthday = birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
   
    public String getBirthday() {
        return birthday;
    }
    
    @Override
     public String getExtraInfo() {
        return birthday;
    }
}
