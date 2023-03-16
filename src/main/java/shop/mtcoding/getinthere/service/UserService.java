package shop.mtcoding.getinthere.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.getinthere.dto.JoinDto;
import shop.mtcoding.getinthere.model.User;
import shop.mtcoding.getinthere.model.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User userJoin(JoinDto joinDto) {
        User user = null;
        try {
            userRepository.insert(joinDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
}
