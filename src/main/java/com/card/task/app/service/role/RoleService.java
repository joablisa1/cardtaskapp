package com.card.task.app.service.role;



import com.card.task.app.model.Role;
import com.card.task.app.model.RoleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RoleService {

	public Page<Role> findAll(Pageable pageable);

	public Optional<Role> findById(Long id);

	public Role save(Role role);

	public Role updateRole(Role role);

	public void deleteById(Long id);

	public boolean existsByName(RoleEnum name);

	public void deleteAll();

	public Optional<Role> findByName(RoleEnum name);
	
	public List<Role> findAll();
}
