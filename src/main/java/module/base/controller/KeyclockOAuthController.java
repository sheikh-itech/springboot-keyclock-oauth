package module.base.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import module.base.beans.User;
import module.base.repository.UserRepo;

@RestController
@RequestMapping("oauth2")
public class KeyclockOAuthController {
	
	private List<User> users = new ArrayList<User>();
	
	@Autowired
	private UserRepo userRepo;
	
	//@RolesAllowed({"user", "admin"})	//Only if jsr250Enabled = true
	@RequestMapping(value="/init")
	public boolean init() {
		
		userRepo.saveAll(users);
		return true;
	}
	
	//@RolesAllowed("user")
	@RequestMapping(value="/list")
	public ResponseEntity<User> getUser(@PathParam("id") int id) {
		
		Optional<User> user = userRepo.findById(id);
		return new ResponseEntity<User>(user.get(), HttpStatus.OK);
	}
	
	//@RolesAllowed("user")
	@RequestMapping(value="/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addUser(@RequestBody User user) {
		
		userRepo.save(user);
		return new ResponseEntity<String>("saved employee detail", HttpStatus.CREATED);
	}
	
	//@RolesAllowed("user")
	@RequestMapping(value="/listAll")
	public ResponseEntity<List<User>> getAllUser() {
		
		return new ResponseEntity<List<User>>(userRepo.findAll(), HttpStatus.OK);
	}
	
	
	@PostConstruct
	public void initialize() {
		User u1 = new User();
		u1.setEmail("shapheej@gmail.com");
		u1.setId(1);
		u1.setNamename("sheikh");
		u1.setPassword("sheikh");
		
		User u2 = new User();
		u2.setEmail("shapheej@gmail.com");
		u2.setId(2);
		u2.setNamename("sheikh1");
		u2.setPassword("sheikh1");
		
		User u3 = new User();
		u3.setEmail("shapheej@gmail.com");
		u3.setId(3);
		u3.setNamename("sheikh3");
		u3.setPassword("sheikh3");
		
		User u4 = new User();
		u4.setEmail("shapheej@gmail.com");
		u4.setId(4);
		u4.setNamename("sheikh4");
		u4.setPassword("sheikh4");
		
		User u5 = new User();
		u5.setEmail("shapheej@gmail.com");
		u5.setId(5);
		u5.setNamename("sheikh5");
		u5.setPassword("sheikh5");
		
		users.add(u1);users.add(u2);users.add(u3);
		users.add(u4);users.add(u5);
	}
}
