package ru.avalon.javapp.devj120.avalontelecom.models;

public class CompanyClientInfo extends ClientInfo {

    private String nameContact;
    private String nameDirector;

    public CompanyClientInfo(PhoneNumber phoneNumber, String name, String address, String nameContact, String nameDirector) {
        super(phoneNumber, name, address);
        this.nameContact = nameContact;
        this.nameDirector = nameDirector;
    }

    public void setNameContact(String nameContact) {
        this.nameContact = nameContact;
    }

    public void setNameDirector(String nameDirector) {
        this.nameDirector = nameDirector;
    }
    
    public String getNameDirector() {
        return nameDirector;
    }
        
    public String getNameContact() {
        return nameContact;
    }

    @Override
    public String getExtraInfo() {
        return nameDirector + ", " + nameContact;
    }
;

}
