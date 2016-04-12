package com.google.devrel.training.conference.form;

import java.util.Date;

/**
 * Pojo representing a profile form on the client side.
 */
public class FieldForm {
    /**
     * Any string user wants us to display him/her on this system.
     */
    private String name;
    private String city;
    private String description;	
    private String croptype;
    private Date registerDate;
    private int fieldZipCode;

    private FieldForm() {}

    /**
     * Constructor for ProfileForm, solely for unit test.
     * @param displayName A String for displaying the user on this system.
     * @param notificationEmail An e-mail address for getting notifications from this system.
     */
    public FieldForm(String name, String city, String description,String croptype,Date registerDate, int fieldZipCode) {
        this.name = name;
        this.city = city;
        this.description=description;
        this.croptype=croptype;
        this.registerDate=registerDate;
        this.fieldZipCode=fieldZipCode;
    }

    public String getFieldName() {
        return name;
    }

    public String getCity(){
    	return city;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getCropType(){
    	return croptype;
    }
    
    public Date getRegisterDate(){
    	return registerDate;
    }
    
    public int getFieldZipCode()
    {
    	return fieldZipCode;
    }
}
