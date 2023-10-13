package userRepository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import userModel.UserModel;

public interface UserRepository extends JpaRepository<UserModel, UUID>{

	UserModel findByUserName(String username);
}
