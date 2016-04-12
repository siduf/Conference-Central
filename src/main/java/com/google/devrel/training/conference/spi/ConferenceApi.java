package com.google.devrel.training.conference.spi;

import static com.google.devrel.training.conference.service.OfyService.ofy;
import static com.google.devrel.training.conference.service.OfyService.factory;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiConfig.Factory;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.users.User;
import com.google.devrel.training.conference.Constants;
import com.google.devrel.training.conference.domain.Profile;
import com.google.devrel.training.conference.form.ProfileForm;
import com.google.devrel.training.conference.form.ProfileForm.TeeShirtSize;
import com.google.devrel.training.conference.domain.Field;
import com.google.devrel.training.conference.form.FieldForm;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * Defines conference APIs.
 */
@Api(name = "conference", version = "v1", scopes = { Constants.EMAIL_SCOPE }, clientIds = {
        Constants.WEB_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID }, description = "API for the Conference Central Backend application.")
public class ConferenceApi {

    private static String extractDefaultDisplayNameFromEmail(String email) {
        return email == null ? null : email.substring(0, email.indexOf("@"));
    }

    /**
     * Creates or updates a Profile object associated with the given user
     * object.
     *
     * @param user
     *            A User object injected by the cloud endpoints.
     * @param profileForm
     *            A ProfileForm object sent from the client form.
     * @return Profile object just created.
     * @throws UnauthorizedException
     *             when the User object is null.
     */

    // Declare this method as a method available externally through Endpoints
    @ApiMethod(name = "saveProfile", path = "profile", httpMethod = HttpMethod.POST)
    // The request that invokes this method should provide data that
    // conforms to the fields defined in ProfileForm

    // TODO 1 Pass the ProfileForm parameter
    // TODO 2 Pass the User parameter
    public Profile saveProfile(final User user,ProfileForm profileform) throws UnauthorizedException {

        String userId = null;
        String mainEmail = null;
        
        // If the user is not logged in, throw an UnauthorizedException
        if(user==null){
        	throw new UnauthorizedException("Authentication Required");
        }
        
        // Get the userId and mainEmail
        mainEmail=user.getEmail();
        userId=user.getUserId();
        
        //Get display name and tee shirt size
        
        String displayName=profileform.getDisplayName();
        System.out.println("Value is "+displayName);
        TeeShirtSize teeShirtSize=profileform.getTeeShirtSize();
        String city=profileform.getCity();
        int zipcode=profileform.getZipCode();
        
        Profile profile = (Profile) ofy().load().key(Key.create(Profile.class,userId)).now();
        
        if (profile==null){
        	
        	// If the displayName is null, set it to default value based on the user's email
            // by calling extractDefaultDisplayNameFromEmail(...)
            if (displayName==null){
            	displayName=extractDefaultDisplayNameFromEmail(user.getEmail());
            }
            	
            if (teeShirtSize==null){
            	teeShirtSize=TeeShirtSize.Alabama;
            }
            
            if (city==null){
            	city="Not Available";
            }
            
            if (zipcode==0){
            	zipcode=32608;
            }
            
            // Create a new Profile entity from the
            // userId, displayName, mainEmail and teeShirtSize
            profile = new Profile(userId, displayName, mainEmail, teeShirtSize,city,zipcode);
        }
        else
        {
        	profile.update(displayName,teeShirtSize,city,zipcode);
        }

       

        // TODO 3 (In Lesson 3)
        // Save the Profile entity in the datastore
        ofy().save().entity(profile).now();

        // Return the profile
        return profile;
    }

    /**
     * Returns a Profile object associated with the given user object. The cloud
     * endpoints system automatically inject the User object.
     *
     * @param user
     *            A User object injected by the cloud endpoints.
     * @return Profile object.
     * @throws UnauthorizedException
     *             when the User object is null.
     */
    @ApiMethod(name = "getProfile", path = "profile", httpMethod = HttpMethod.GET)
    public Profile getProfile(final User user) throws UnauthorizedException {
        if (user == null) {
            throw new UnauthorizedException("Authorization required");
        }

        // TODO
        // load the Profile Entity
        String userId = user.getUserId(); // TODO
        Key key = Key.create(Profile.class,userId); // TODO
        Profile profile = (Profile) ofy().load().key(key).now(); // TODO load the Profile entity
        return profile;
    }
    
    /**
     * Gets the Profile entity for the current user
     * or creates it if it doesn't exist
     * @param user
     * @return user's Profile
     */
    private static Profile getProfileFromUser(User user) {
        // First fetch the user's Profile from the data-store.
        Profile profile = ofy().load().key(
                Key.create(Profile.class, user.getUserId())).now();
        if (profile == null) {
            // Create a new Profile if it doesn't exist.
            // Use default displayName and teeShirtSize
            String email = user.getEmail();
            profile = new Profile(user.getUserId(),
                    extractDefaultDisplayNameFromEmail(email), email, TeeShirtSize.Alabama,"Not Available",32608);
        }
        return profile;
    }
    
    /**
     * Creates a new Conference object and stores it to the datastore.
     *
     * @param user A user who invokes this method, null when the user is not signed in.
     * @param conferenceForm A ConferenceForm object representing user's inputs.
     * @return A newly created Conference Object.
     * @throws UnauthorizedException when the user is not signed in.
     */
    @ApiMethod(name = "createField", path = "field", httpMethod = HttpMethod.POST)
    public Field createField(final User user,final FieldForm fieldForm)
        throws UnauthorizedException {
        if (user == null) {
            throw new UnauthorizedException("Authorization required");
        }
        
        System.out.println(" Name " + fieldForm.getFieldName());
        System.out.println(" Crop Type " + fieldForm.getCropType());

        // Get the userId of the logged in User
        String userId = user.getUserId();

        // Get the key for the User's Profile
        Key<Profile> profileKey = Key.create(Profile.class,userId);

        // Allocate a key for the Field -- let App Engine allocate the ID
        // Don't forget to include the parent Profile in the allocated ID
        final Key<Field> fieldKey = factory().allocateId(profileKey, Field.class);

        // TODO (Lesson 4)
        // Get the field Id from the Key
        final long fieldId = fieldKey.getId();

        // TODO (Lesson 4)
        // Get the existing Profile entity for the current user if there is one
        // Otherwise create a new Profile entity with default values
        Profile profile = getProfileFromUser(user);

        // TODO (Lesson 4)
        // Create a new Conference Entity, specifying the user's Profile entity
        // as the parent of the field
        //(final Long id, final String ownerUserId,final FieldForm fieldForm)
        Field field =new Field(fieldId,userId,fieldForm); 

        // TODO (Lesson 4)
        // Save Conference and Profile Entities
        ofy().save().entities(field, profile).now(); 

         return field;
         }

}
