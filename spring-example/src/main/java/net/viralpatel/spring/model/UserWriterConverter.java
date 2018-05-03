package net.viralpatel.spring.model;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserWriterConverter implements Converter<User, DBObject> {

    @Override
    public DBObject convert(final User user) {
        final DBObject dbObject = new BasicDBObject();
        dbObject.put("name", user.getName());
        dbObject.put("age", user.getAge());
        dbObject.put("lastname", user.getLastname());
        dbObject.put("surname", user.getSurname());
        dbObject.put("country", user.getCountry());
        dbObject.put("city", user.getCity());
        dbObject.put("phone", user.getPhone());
        dbObject.put("url", user.getUrl());
        dbObject.put("edu", user.getEdu());
        dbObject.put("sex", user.getSex());
        dbObject.put("married", user.getMarried());
        dbObject.put("preference", user.getPreference());  
        dbObject.put("yearOfBirth", user.getYearOfBirth());     
        if (user.getEmailAddress() != null) {
            final DBObject emailDbObject = new BasicDBObject();
            emailDbObject.put("value", user.getEmailAddress().getValue());
            dbObject.put("email", emailDbObject);
        }
        dbObject.removeField("_class");
        return dbObject;
    }

}
