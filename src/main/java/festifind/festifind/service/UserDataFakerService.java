package festifind.festifind.service;

import festifind.festifind.entity.User;
import festifind.festifind.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserDataFakerService {

    private final UserRepository userRepository;
    private final Faker faker;

    public void saveUsers(int count) {
        List<User> users = new ArrayList<>();
        Faker faker = new Faker();

        for (int i = 0; i < count; i++) {
            String name = faker.name().fullName();
            String email = faker.internet().emailAddress();
            String password = faker.internet().password();

            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);

            users.add(user);
        }

        userRepository.saveAll(users);
    }

    public User generateRandomUser() {
        List<User> allUsers = userRepository.findAll();
        if (allUsers.isEmpty()) {
            throw new RuntimeException("No users available in the database");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(allUsers.size());
        return allUsers.get(randomIndex);
    }

}
