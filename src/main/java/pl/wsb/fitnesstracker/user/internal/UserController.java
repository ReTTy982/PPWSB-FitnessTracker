package pl.wsb.fitnesstracker.user.internal;

import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;

import java.util.List;

/**
 * UserController is responsible for handling HTTP requests related to user operations.
 * It provides endpoints for retrieving and creating users.
 */
@RestController
@RequestMapping("/v1/users")
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    public UserController(UserServiceImpl userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/getUserData")
    public List<UserDto> getAllUsersByNames() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public List<UserDto> getUserById(@PathVariable long id) {
        return userService.getUser(id)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @PostMapping("/createUser")
    public UserDto createUser(@RequestBody UserDto userDto) {
        User user = userMapper.toDomain(userDto);
        User createdUser = userService.createUser(user);
        UserDto createdUserDto = userMapper.toDto(createdUser);
        return createdUserDto;
    }





}

