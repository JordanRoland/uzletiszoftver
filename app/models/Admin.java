package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.GenericModel;

@Entity
@Table(name = "admin")
public class Admin extends GenericModel{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //http://www.objectdb.com/java/jpa/entity/generated#The_Identity_Strategy_
	@Column(name = "admin_id")
	public Long adminId;
	
	@Column(name = "admin_name", unique = true)
	public String adminName;
	
	@Column(name = "admin_password")
	public String adminPassword;
	
}
