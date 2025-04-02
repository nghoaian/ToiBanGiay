package tdtu.edu.vn.shoes_store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import tdtu.edu.vn.shoes_store.model.Role;
import tdtu.edu.vn.shoes_store.model.User;
import tdtu.edu.vn.shoes_store.repository.RoleRepository;
import tdtu.edu.vn.shoes_store.repository.UserRepository;

import java.util.Collections;

@SpringBootApplication
public class ShoesStoreApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ShoesStoreApplication.class, args);
	}

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		// Create or get admin role
		Role adminRole = roleRepository.findByName("ROLE_ADMIN");

		// If not found, create it
		if (adminRole == null) {
			adminRole = roleRepository.save(new Role("ROLE_ADMIN"));
		}

		// Create or get admin user
		Role finalAdminRole = adminRole;
		userRepository.findByUsername("admin").ifPresentOrElse(
				user -> {
					// Admin user exists
				},
				() -> {
					// Admin user does not exist, create it
					User adminUser = new User();
					adminUser.setUsername("admin");
					adminUser.setPassword(passwordEncoder.encode("adminpassword"));
					adminUser.setEmail("admin@example.com");
					adminUser.setRole(finalAdminRole);
					userRepository.save(adminUser);
				}
		);
	}
}
