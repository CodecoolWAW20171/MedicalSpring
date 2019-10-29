package com.medbis.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Map;

@Setter
@Getter
@MappedSuperclass
public abstract class User {

    @NotNull(message = "is required")
    @Pattern(regexp = "^[0-9]{11}", message = "only 11 digits")
    @Column(name = "pesel")
    private String pesel;

    @NotNull(message = "is required")
    @Size(min = 3, message = "to short")
    @Column(name = "surname")
    private String surname;

    @NotNull(message = "is required")
    @Size(min = 3, message = "to short")
    @Column(name = "name")
    private String name;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "is required")
    @Column(name = "birthday")
    private LocalDate birthday;

    @NotNull(message = "is required")
    @Size(min = 3, message = "to short")
    @Column(name = "sex")
    private String sex;

    @NotNull(message = "is required")
    @Size(min = 3, message = "to short")
    @Column(name = "community")
    private String community;

    @NotNull(message = "is required")
    @Pattern(regexp = "^[0-9]{5}", message = "only 5 digits")
    @Column(name = "zip_code")
    private String zipCode;

    @NotNull(message = "is required")
    @Size(min = 3, message = "to short")
    @Column(name = "city")
    private String city;

    @NotNull(message = "is required")
    @Size(min = 3, message = "to short")
    @Column(name = "street")
    private String street;

    @NotNull(message = "is required")
    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "apartment_number")
    private String apartmentNumber;

    @Pattern(regexp = "^[0-9]{0}|^[0-9]{9}", message = "only 5 digits")
    @Column(name = "home_phone_number")
    private String homePhoneNumber;
//"^[0-9]{10}$|^[0-9]{12}$"
   @Pattern(regexp = "^[0-9]{0}|^[0-9]{9}", message = "only 5 digits")
//    @Pattern(regexp = "^[0-9]*$", message = "only 9 digits")
    @Column(name = "work_phone_number")
    private String workPhoneNumber;

    @Column(name = "mobile_phone_number")
    private String mobilePhoneNumber;

    public abstract Integer getResultOfTheTreatment(Treatment treatment);
    public abstract Map<Treatment, Integer> getTreatmentResultMap();

    /* public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }*/

    @Override
    public String toString() {
        return "User{" +
                "pesel=" + pesel +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", sex='" + sex + '\'' +
                ", community='" + community + '\'' +
                ", zipCode=" + zipCode +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", houseNumber=" + houseNumber +
                ", apartmentNumber=" + apartmentNumber +
                ", homePhoneNumber=" + homePhoneNumber +
                ", workPhoneNumber=" + workPhoneNumber +
                ", mobilePhoneNumber=" + mobilePhoneNumber +
                '}';
    }
}