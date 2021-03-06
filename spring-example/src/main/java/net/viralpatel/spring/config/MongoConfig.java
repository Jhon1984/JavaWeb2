package net.viralpatel.spring.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import net.viralpatel.spring.model.UserCascadeSaveMongoEventListener;
import net.viralpatel.spring.model.UserWriterConverter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableMongoRepositories(basePackages = "org.baeldung.repository")
public class MongoConfig extends AbstractMongoConfiguration {

    private final List<Converter<?, ?>> converters = new ArrayList<Converter<?, ?>>();
  
    @Override
    protected String getDatabaseName() {
        return "testdb1";
    }

    /*@Override
    public Mongo mongo() throws Exception {
        return new MongoClient("127.0.0.1", 27017);
    }*/

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(Arrays.asList(
        		 new ServerAddress("localhost", 27017),
        		 new ServerAddress("localhost", 27018),
        		 new ServerAddress("localhost", 27019)));
    }

    
    @Override
    public String getMappingBasePackage() {
        return "org.baeldung";
    }

    @Bean
    public UserCascadeSaveMongoEventListener userCascadingMongoEventListener() {
        return new UserCascadeSaveMongoEventListener();
    }

    @Bean
    public CascadeSaveMongoEventListener cascadingMongoEventListener() {
        return new CascadeSaveMongoEventListener();
    }

    @Override
    public CustomConversions customConversions() {
        converters.add(new UserWriterConverter());
        return new CustomConversions(converters);
    }

    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
    }
    
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), "testdb1");
    }
}
