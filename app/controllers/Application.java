package controllers;

import play.*;
import play.data.validation.Required;
import play.mvc.*;
import java.util.*;

import org.mindrot.jbcrypt.BCrypt;

import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }
    
    /**
     * Admin oldal renderelése
     */
    public static void getAdmin(){
    	render("@Application.adminLogin");
    }
    
    /**
     * Login admin
     */
    public static void postAdmin(
    		@Required(message="This field is required!") String username, 
    		@Required(message="This field is required!") String password){
    	checkAuthenticity();
    	
    	Admin admin = new Admin();
    	boolean correctPw = false;
    	try {
    		admin = Admin.find("byAdminName", username).first();
    		correctPw = BCrypt.checkpw(password, admin.adminPassword);
		} catch (NullPointerException npe) {
			Logger.error("No user '%s' found!", username);
			validation.addError("username", "Username or password is wrong, please try again");
			params.flash();
			render("@Application.adminLogin");
		}
    	
    	
    	
    	if(!correctPw){
    		validation.addError("username", "Username or password is wrong, please try again");
			params.flash();
			render("@Application.adminLogin");
    	}
    	
    	Logger.info("Successfully logged in as '%s'", username);
    	
    	getAdmin();
    	
    }
}