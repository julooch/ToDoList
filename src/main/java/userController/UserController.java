package userController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;
import userModel.UserModel;
import userRepository.UserRepository;

@RestController
@RequestMapping ("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/")
	public ResponseEntity create(@RequestBody UserModel userModel) {
		var user = this.userRepository.findByUserName(userModel.getUsername());
		
		if(user != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exist!");
		}
		
		var passwordHashred = BCrypt.withDefaults()
		.hashToString(12, userModel.getPassword().toCharArray());
		
		userModel.setPassword(passwordHashred);
		
		var userCreated = this.userRepository.save(userModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
	}
}