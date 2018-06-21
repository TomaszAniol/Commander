package com.editor;


public class DataList {
    private String name;
    private String surname;
    private String city;
    private String street;
    private String zipCode;
    private String phoneNumber;

    public DataList(String name, String surname, String city, String street, String zipCode, String phoneNumber){
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
