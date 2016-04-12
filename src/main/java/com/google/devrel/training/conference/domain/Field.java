package com.google.devrel.training.conference.domain;

import static com.google.devrel.training.conference.service.OfyService.ofy;

import java.util.Date;

import com.googlecode.objectify.condition.IfNotDefault;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.google.common.base.Preconditions;
import com.google.devrel.training.conference.form.FieldForm;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

// indicate that this class is an Entity
@Entity
public class Field {
	
	private static final String DEFAULT_CITY = "Default City";
	private static final String DEFAULT_CROPTYPE = "Default CropType";
	
    /**
     * The id for the data-store key.
     *
     * We use automatic id assignment for entities of Field class.
     */
    @Id
    private Long id;
    
    @Index
    private String name;
    
    /**
     * Holds Profile key as the parent.
     */
    @Parent
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Key<Profile> profileKey;
    /**
     * The userId of the organizer.
     */
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private String ownerUserId;
    
    @Index(IfNotDefault.class)
    private String city;
    
    private String description;	
    
    @Index
    private String croptype;
    
    private Date registerDate;
    
    private int fieldZipCode;
    
    /**
     * Just making the default constructor private.
     */
    private Field(){};
    
    public Field(final Long id, final String ownerUserId, final FieldForm fieldForm) {
		Preconditions.checkNotNull(fieldForm.getFieldName(), "The name is required");
		this.id = id;
		this.profileKey = Key.create(Profile.class, ownerUserId);
		this.ownerUserId = ownerUserId;
		this.name=fieldForm.getFieldName();
    	this.city=fieldForm.getCity() == null ? DEFAULT_CITY : fieldForm.getCity();
    	this.description=fieldForm.getDescription();
    	this.croptype=fieldForm.getCropType()== null ? DEFAULT_CROPTYPE : fieldForm.getCropType();;
    	this.registerDate=fieldForm.getRegisterDate();
    	this.fieldZipCode=fieldForm.getFieldZipCode();
		}
    
    public String getOwnerDisplayName() {
        Profile owner = ofy().load().key(getProfileKey()).now();
        if (owner == null) {
            return ownerUserId;
        } else {
            return owner.getDisplayName();
        }
    }
    
    public Long getID(){
    	return this.id;
    }
    
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public Key<Profile> getProfileKey() {
        return profileKey;
    }
    // Get a String version of the key
    public String getWebsafeKey() {
        return Key.create(profileKey, Field.class, id).getString();
    }
    
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public String getOwnerUserId() {
        return ownerUserId;
    }
    
    public String getFieldName(){
    	return name;
    }
    
    public String getCity(){
    	return city;
    }
    
    public String getDescription(){
    	return description;
    }
    
    public String getCropType(){
    	return croptype;
    }
    
    public Date getRegisterDate(){
    	return registerDate;
    }
    
    public int returnFieldZipCode(){
    	return fieldZipCode;
    }
    
    public void updateField (String name, String city,String description, String croptype,Date regDate,int zipcode)
    {
    	if(name!=null){
			this.name=name;
		}
		
		if (city !=null){
			this.city=city;
		}
		
		if (description!=null){
			this.description=description;
		}
		
		if (croptype != null){
			this.croptype=croptype;
		}
		
		if (regDate != null){
			this.registerDate=regDate;
		}
		
		if (zipcode!=0){
			this.fieldZipCode=zipcode;
		}
		
    }
    


}