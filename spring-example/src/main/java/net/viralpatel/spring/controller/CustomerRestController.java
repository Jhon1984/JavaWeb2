package net.viralpatel.spring.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.DBCollection;

import net.viralpatel.spring.dao.CustomerDAO;
import net.viralpatel.spring.model.Customer;
import net.viralpatel.spring.model.User;

@RestController
public class CustomerRestController {

	@Autowired
	 private MongoTemplate mongoTemplate;
	@Autowired
	private CustomerDAO customerDAO;

	
	@RequestMapping(value = "/cache", method = RequestMethod.GET)
	public @ResponseBody String getData(@RequestParam(name = "count") String count) {
		//logger.info("Start cache");
		Integer cnt = Integer.parseInt(count);
		    MongoConverter converter = mongoTemplate.getConverter();
	        // Sample code
	       DBCollection collection = mongoTemplate.getCollection("user");
	        // Get  BulkWriteOperation by accessing the mongodb com.mongodb.DBCollection class on mycol //Collectio
	        BulkWriteOperation  bulkWriteOperation= collection.initializeUnorderedBulkOperation();
	        //perform the insert operation in the loop to add objects for bulk execution
	        for (int i=0;i<cnt;i++)
	        {        	
	            User user =  new User(new ObjectId()+"", "EricFirst"+i, 45, "EricLast"+i, "EricSurname"+i, "India", "Chennai",
	        			"88888888", "WWW.GOOGLE.COM", "BE", "Male", "married", "preference", 1984);
	            BasicDBObject  dbObject = new BasicDBObject();
	            converter.write(user, dbObject);

	        bulkWriteOperation.insert(dbObject);
	        }
	        String start = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()).toString();
	        System.out.println("Before Insert ::: "+new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()));
	        // execute bulk operation on mycol collection
	        BulkWriteResult result=bulkWriteOperation.execute();
	        String end = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()).toString();
	        System.out.println("After Insert ::: "+new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()));
	        System.out.println("INSERTED COUNT ###### "+result.getInsertedCount());
		
		
		
		return "Success - Start Time = "+start+" and End Time = "+end;
	}
	
	@GetMapping("/customers")
	public List getCustomers() {
		return customerDAO.list();
	}

	@GetMapping("/customers/{id}")
	public ResponseEntity getCustomer(@PathVariable("id") Long id) {

		Customer customer = customerDAO.get(id);
		if (customer == null) {
			return new ResponseEntity("No Customer found for ID " + id, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(customer, HttpStatus.OK);
	}

	@PostMapping(value = "/customers")
	public ResponseEntity createCustomer(@RequestBody Customer customer) {

		customerDAO.create(customer);

		return new ResponseEntity(customer, HttpStatus.OK);
	}

	@DeleteMapping("/customers/{id}")
	public ResponseEntity deleteCustomer(@PathVariable Long id) {

		if (null == customerDAO.delete(id)) {
			return new ResponseEntity("No Customer found for ID " + id, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(id, HttpStatus.OK);

	}

	@PutMapping("/customers/{id}")
	public ResponseEntity updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {

		customer = customerDAO.update(id, customer);

		if (null == customer) {
			return new ResponseEntity("No Customer found for ID " + id, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(customer, HttpStatus.OK);
	}

}