package module.base.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import module.base.beans.User;
import module.base.repository.UserRepo;

//@RestController
//@RequestMapping("oauth2")
@Controller
public class KeyclockOAuthController {
	
	private List<User> users = new ArrayList<User>();
	
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private HttpServletRequest request;
	
	@RolesAllowed({"user", "admin"})	//Only if jsr250Enabled = true
	@GetMapping(value="/init")
	public boolean init() {
		
		userRepo.saveAll(users);
		return true;
	}
	
	@RolesAllowed({"user", "admin"})
	@GetMapping(value="/login")
	public String login() {
		return "login";
	}
	
	@RolesAllowed("user")
	@GetMapping(value="/greet")
	public String greet() {
		return "hello";
	}
	
	@RolesAllowed("user")
	@GetMapping(value="/error1")
	public String error() {
		return "error";
	}
	
	@RolesAllowed("admin")
	@GetMapping(value="/greet1")
	public String greet1() {
		return "hello";
	}
	
	@RolesAllowed("user")
	@GetMapping(value="/list")
	public ResponseEntity<User> getUser(@PathParam("id") int id) {
		
		Optional<User> user = userRepo.findById(id);
		return new ResponseEntity<User>(user.get(), HttpStatus.OK);
	}
	
	@RolesAllowed("user")
	@GetMapping(value="/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addUser(@RequestBody User user) {
		
		userRepo.save(user);
		return new ResponseEntity<String>("saved employee detail", HttpStatus.CREATED);
	}
	
	@RolesAllowed({"user", "admin"})
	@GetMapping(value="/listAll")
	public ResponseEntity<List<User>> getAllUser() {
		
		if(null!=request) {
			
			Cookie[] cookie = request.getCookies();
			
			if(null!=cookie)
				
				for(Cookie c : cookie)
					System.out.println(c.getName()+"--> "+c.getValue());
		}
		
		return new ResponseEntity<List<User>>(userRepo.findAll(), HttpStatus.OK);
	}
	
	
	@PostConstruct
	public void initialize() {
		User u1 = new User();
		u1.setEmail("shapheej@gmail.com");
		u1.setId(1);
		u1.setUsername("sheikh");
		u1.setPassword("sheikh");
		
		User u2 = new User();
		u2.setEmail("shapheej@gmail.com");
		u2.setId(2);
		u1.setUsername("sheikh1");
		u2.setPassword("sheikh1");
		
		User u3 = new User();
		u3.setEmail("shapheej@gmail.com");
		u3.setId(3);
		u1.setUsername("sheikh3");
		u3.setPassword("sheikh3");
		
		User u4 = new User();
		u4.setEmail("shapheej@gmail.com");
		u4.setId(4);
		u1.setUsername("sheikh4");
		u4.setPassword("sheikh4");
		
		User u5 = new User();
		u5.setEmail("shapheej@gmail.com");
		u5.setId(5);
		u1.setUsername("sheikh5");
		u5.setPassword("sheikh5");
		
		users.add(u1);users.add(u2);users.add(u3);
		users.add(u4);users.add(u5);
	}
}
