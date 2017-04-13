package controllers;

import play.*;
import play.data.validation.Required;
import play.mvc.*;
import java.util.*;

import org.mindrot.jbcrypt.BCrypt;

import models.*;

public class Application extends Controller {
	
	@Before(unless={"getAdmin","postAdmin"})
	private static void checkAuth(){
		if(session.get("username")==null) getAdmin();
	}

    public static void index() {
        render();
    }
    
    /**
     * Admin oldal renderelése
     */
    public static void getAdmin(){
    	if(session.get("username") != null){
    		redirect("/admin");
    	}
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
    		// Megnézzük, hogy van-e ilyen felhasználónk
    		admin = Admin.find("byAdminName", username).first();
    		// Megnézzük, hogy a plaintext password megegyezike- a hashelt jelszóval az adatbázisunkban
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
    	
    	// Ha itt vagyunk, sikerült az autentikáció
    	Logger.info("Successfully logged in as '%s'", username);
    	session.put("username", username);
    	getAdmin();
    	
    }
    
    public static void logout(){
    	session.remove("username");
    	getAdmin();
    }
    
    public static void getAdminMain(){
    	render("@Application.adminMain");
    }
}