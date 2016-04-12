package com.google.devrel.training.conference.form;

/**
 * Pojo representing a profile form on the client side.
 */
public class ProfileForm {
    /**
     * Any string user wants us to display him/her on this system.
     */
    private String displayName;

    /**
     * T shirt size.
     */
    private TeeShirtSize teeShirtSize;
    private String city;
    private int zipcode;

    private ProfileForm () {}

    /**
     * Constructor for ProfileForm, solely for unit test.
     * @param displayName A String for displaying the user on this system.
     * @param notificationEmail An e-mail address for getting notifications from this system.
     */
    public ProfileForm(String displayName, TeeShirtSize teeShirtSize, String city, int zipcode) {
        this.displayName = displayName;
        this.teeShirtSize = teeShirtSize;
        this.city=city;
        this.zipcode=zipcode;
    }

    public String getDisplayName() {
        return displayName;
    }

    public TeeShirtSize getTeeShirtSize() {
        return teeShirtSize;
    }
    
    public String getCity(){
    	return city;
    }
    
    public int getZipCode()
    {
    	return zipcode;
    }
    
    public static enum TeeShirtSize {
    	Alabama, 
    	Alaska,
    	Arizona, 
    	Arkansas, 
    	California, 
    	Colorado, 
    	Connecticut, 
    	Delaware, 
    	Florida, 
    	Georgia, 
    	Hawaii, 
    	Idaho, 
    	Illinois,
    	Indiana, 
    	Iowa, 
    	Kansas, 
    	Kentucky, 
    	Louisiana, 
    	Maine, 
    	Maryland, 
    	Massachusetts, 
    	Michigan, 
    	Minnesota, 
    	Mississippi, 
    	Missouri, 
    	Montana,
    	Nebraska, 
    	Nevada,
    	NewHampshire, 
    	NewJersey, 
    	NewMexico, 
    	NewYork, 
    	NorthCarolina, 
    	NorthDakota, 
    	Ohio, 
    	Oklahoma, 
    	Oregon, 
    	Pennsylvania,
    	RhodeIsland, 
    	SouthCarolina, 
    	SouthDakota, 
    	Tennessee, 
    	Texas, 
    	Utah, 
    	Vermont, 
    	Virginia, 
    	Washington, 
    	WestVirginia, 
    	Wisconsin, 
    	Wyoming
    }
}
