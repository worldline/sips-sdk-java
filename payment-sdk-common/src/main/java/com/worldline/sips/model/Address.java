package com.worldline.sips.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(alphabetic = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address {
    private String addressAdditional1;
    private String addressAdditional2;
    private String addressAdditional3;
    private String city;
    private String company;
    private String country;
    private String postBox;
    private String state;
    private String street;
    private String streetNumber;
    private String zipcode;

    public String getAddressAdditional1() {
        return addressAdditional1;
    }

    public void setAddressAdditional1(String addressAdditional1) {
        this.addressAdditional1 = addressAdditional1;
    }

    public String getAddressAdditional2() {
        return addressAdditional2;
    }

    public void setAddressAdditional2(String addressAdditional2) {
        this.addressAdditional2 = addressAdditional2;
    }

    public String getAddressAdditional3() {
        return addressAdditional3;
    }

    public void setAddressAdditional3(String addressAdditional3) {
        this.addressAdditional3 = addressAdditional3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostBox() {
        return postBox;
    }

    public void setPostBox(String postBox) {
        this.postBox = postBox;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
