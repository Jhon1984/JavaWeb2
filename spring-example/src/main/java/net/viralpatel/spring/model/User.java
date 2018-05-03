package net.viralpatel.spring.model;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.querydsl.core.annotations.QueryEntity;

import net.viralpatel.spring.config.CascadeSave;
import net.viralpatel.spring.config.EmailAddress;

@QueryEntity
@Document
@CompoundIndexes({ @CompoundIndex(name = "email_age", def = "{'email.id' : 1, 'age': 1}") })
public class User implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    private String id;
    @Indexed(direction = IndexDirection.ASCENDING)
    private String name;
    @Indexed(direction = IndexDirection.ASCENDING)
    private Integer age;
    @Indexed(direction = IndexDirection.ASCENDING)
    private String lastname;
    @Indexed(direction = IndexDirection.ASCENDING)
    private String surname;
    private String country;
    private String city;
    private String phone;
    private String url;
    private String edu;
    private String sex;
    private String married;
    private String preference;
    @DBRef
    @Field("email")
    @CascadeSave
    private EmailAddress emailAddress;    
    //@Transient
    private Integer yearOfBirth;

    public User() {   }
        
    @PersistenceConstructor
    public User(String id, String name, Integer age, String lastname, String surname, String country, String city,
			String phone, String url, String edu, String sex, String married, String preference, Integer yearOfBirth) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.lastname = lastname;
		this.surname = surname;
		this.country = country;
		this.city = city;
		this.phone = phone;
		this.url = url;
		this.edu = edu;
		this.sex = sex;
		this.married = married;
		this.preference = preference;
		this.yearOfBirth = yearOfBirth;
	}
    
    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
    
    //@PersistenceConstructor
    public User(final String name, @Value("#root.age ?: 0") final Integer age, final EmailAddress emailAddress) {
        this.name = name;
        this.age = age;
        this.emailAddress = emailAddress;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEdu() {
		return edu;
	}

	public void setEdu(String edu) {
		this.edu = edu;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMarried() {
		return married;
	}

	public void setMarried(String married) {
		this.married = married;
	}

	public String getPreference() {
		return preference;
	}

	public void setPreference(String preference) {
		this.preference = preference;
	}

	public EmailAddress getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(EmailAddress emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Integer getYearOfBirth() {
		return yearOfBirth;
	}

	public void setYearOfBirth(Integer yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}	
   
    
}
