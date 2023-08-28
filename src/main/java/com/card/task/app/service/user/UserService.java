package com.card.task.app.service.user;



import com.card.task.app.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

	Optional<User> findById(Long id);

	User save(User user);

	User updateUser(User user);

	Optional<User> findByUsername(String username);

	void deleteUserById(Long id);

	void deleteAll();

	void deleteById(Long id);

	Page<User> findAll(Pageable paging);
	
	boolean existsByUsername(String username);

	boolean existsByEmail(String email);
}
