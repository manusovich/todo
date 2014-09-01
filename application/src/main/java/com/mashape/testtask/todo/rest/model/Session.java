package com.mashape.testtask.todo.rest.model;

import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Bean describes session object. Our session key is phone number
 * </>
 * Created by Alex Manusovich on 8/31/14.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Session implements Serializable {
    public static final String PHONE = "phone";

    /**
     * Phone number has to be in full format with + and country code
     */
    @XmlElement(name = PHONE)
    @Pattern(regexp = "\\+[1-9]\\d+", message = "Phone number has to be with + and country code.")
    private String phone;

    public Session() {
    }

    public Session(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
