package hexlet.code.app.service;

import hexlet.code.app.DTO.UserCreateDTO;
import hexlet.code.app.DTO.UserDTO;
import hexlet.code.app.DTO.UserUpdateDTO;
import hexlet.code.app.exception.ResourceNotFoundException;
import hexlet.code.app.mapper.UserMapper;
import hexlet.code.app.model.User;
import hexlet.code.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<List<UserDTO>> getAll() {
        var users = userRepository.findAll();
        var result = users.stream()
                        .map(userMapper::map)
                        .toList();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(users.size()))
                .body(result);
    }

    public ResponseEntity<UserDTO> findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        UserDTO result = userMapper.map(user);
        return ResponseEntity.ok()
                .body(result);
    }

    public ResponseEntity<UserDTO> create(UserCreateDTO dto) {
        var user = userMapper.map(dto);
        userRepository.save(user);
        var result = userMapper.map(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(result);
    }

    public ResponseEntity<UserDTO> update(UserUpdateDTO dto, Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        userMapper.update(dto, user);
        userRepository.save(user);
        var result = userMapper.map(user);
        return ResponseEntity.ok()
                .body(result);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
