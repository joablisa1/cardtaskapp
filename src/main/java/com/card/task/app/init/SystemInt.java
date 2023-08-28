package com.card.task.app.init;

import com.card.task.app.model.*;
import com.card.task.app.repository.CardRepository;
import com.card.task.app.repository.RoleRepository;
import com.card.task.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class SystemInt implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initRoles();
        initAdmin();
        initUser();
        initCard1();
        initCard2();
    }
    private void initRoles() {

        String[] r = {"admin", "user"};
        Set<String> strRoles = new HashSet<>(Arrays.asList(r));

        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    if (roleRepository.findByName(RoleEnum.ROLE_ADMIN).isEmpty()) {
                        Role adminRole = new Role(RoleEnum.ROLE_ADMIN);
                        this.roleRepository.save(adminRole);
                    }
                    break;

                default:
                    if (roleRepository.findByName(RoleEnum.ROLE_USER).isEmpty()) {
                        Role adminRole = new Role(RoleEnum.ROLE_USER);
                        this.roleRepository.save(adminRole);
                    }
            }
        });
    }
    private void initAdmin() {
        Optional<Role> admin = roleRepository.findByName(RoleEnum.ROLE_ADMIN);

        Set<Role> roles = new HashSet<>();
        String encodedPassword = passwordEncoder.encode("1234");
        roles.add(admin.get());
        Optional<User> superUserExists = userRepository.findByEmail("admin@gmail.com");
        if (!superUserExists.isPresent()){
            User superUser = new User();
            superUser.setRoles(roles);
            superUser.setEmail("admin@gmail.com");
            superUser.setUsername("admin");
            superUser.setPassword(encodedPassword);
            userRepository.save(superUser);
        }
    }
    private void initUser() {
        Optional<Role> user = roleRepository.findByName(RoleEnum.ROLE_USER);
        Set<Role> roles = new HashSet<>();
        String encodedPassword = passwordEncoder.encode("1234");
        roles.add(user.get());
        Optional<User> superUserExists = userRepository.findByEmail("user@gmail.com");
        if (!superUserExists.isPresent()){
            User superUser = new User();
            superUser.setRoles(roles);
            superUser.setEmail("user@gmail.com");
            superUser.setUsername("user");
            superUser.setPassword(encodedPassword);
            userRepository.save(superUser);
        }
    }

    private  void initCard1() {
        Optional<Card> defaultCard = cardRepository.findCardByName("BirthDay");
        Optional<User> userOpt = userRepository.findByEmail("user@gmail.com");
        if (!defaultCard.isPresent()) {
            Card o1 = new Card();
            o1.setName("BirthDay");
            o1.setColor("#FF0FF0");
            o1.setStatus(String.valueOf(CardStatus.TODO));
            o1.setDescription("Test Card");
            o1.setCreationDate(LocalDate.now());
            o1.setUser(userOpt.get());
            cardRepository.save(o1);
        }
    }

    private  void initCard2() {
        Optional<Card> defaultCard = cardRepository.findCardByName("Revenue");
        Optional<User> userOpt = userRepository.findByEmail("user@gmail.com");
        if (!defaultCard.isPresent()) {
            Card o1 = new Card();
            o1.setName("Revenue");
            o1.setColor("#FF0FF0");
            o1.setStatus(String.valueOf(CardStatus.TODO));
            o1.setDescription("Test Card");
            o1.setCreationDate(LocalDate.now());
            o1.setUser(userOpt.get());
            cardRepository.save(o1);
        }
    }
}
