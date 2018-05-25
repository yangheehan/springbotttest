package myspringboot.hero;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200") 
@RestController 
@RequestMapping(value = "/heroes") 
public class HeroController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private List<Hero> users = new ArrayList<Hero>();
	
	public HeroController() {
		buildUsers();
	}
	private void buildUsers() { 
		Hero user1 = new Hero(1L, "John"); 
		Hero user2 = new Hero(2L, "Jon"); 
		Hero user3 = new Hero(3L, "Will"); 
		Hero user4 = new Hero(4L, "Sam"); 
		Hero user5 = new Hero(5L, "Ross"); 
		users.add(user1); 
		users.add(user2); 
		users.add(user3); 
		users.add(user4); 
		users.add(user5); 
	}
	
	@RequestMapping(method = RequestMethod.GET) 
	public List<Hero> getUsers() 	{
		logger.debug("목록조회");
		return this.users; 
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.GET) 
	public Hero getUser(@PathVariable("id") Long id) { 
		logger.debug("상세조회:"+id);
		return this.users.stream().filter(user -> user.getId() == id).findFirst().orElse(null); 
	}
	@RequestMapping(method = RequestMethod.POST) 
	public Hero saveUser(@RequestBody Hero user) { 
		logger.debug("등록:"+user);
		Long nextId = 0L; 
		if (this.users.size() != 0) { 
			Hero lastUser = this.users.stream() .skip(this.users.size() - 1).findFirst().orElse(null); 
			nextId = lastUser.getId() + 1; 
		} 
		user.setId(nextId); 
		this.users.add(user); 
		logger.debug("등록 후:"+user);
		return user; 
	}
	@RequestMapping(method = RequestMethod.PUT) 
	public Hero updateUser(@RequestBody Hero user) { 
		logger.debug(" update 전 :"+user);
		Hero modifiedUser = this.users.stream() .filter(u -> u.getId() == user.getId()).findFirst().orElse(null); 
		modifiedUser.setName(user.getName()); 
		modifiedUser.setId(user.getId());
		logger.debug(" update 후 :"+modifiedUser);
		return modifiedUser; 
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE) 
	public boolean deleteUser(@PathVariable Long id) { 
		logger.debug("삭제 전:"+id);
		Hero deleteUser = this.users.stream() .filter(user -> user.getId() == id).findFirst().orElse(null); 
		if (deleteUser != null) { 
			logger.debug("삭제된 hero:"+deleteUser);
			this.users.remove(deleteUser); 
			return true; 
		} else  { return false; } 
	}


	//search
	@RequestMapping(value = "/name/{name}", method = RequestMethod.GET) 
	public List<Hero> searchHeroes(@PathVariable String name){
		logger.debug("검색:"+name);
		 return this.users.stream().filter(hero -> hero.getName().contains(name)).collect(Collectors.toList());
	}
		
	
}
