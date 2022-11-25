package com.fiec.lpiiiback.models.dto;

import com.fiec.lpiiiback.models.entities.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    Integer id;
    String email;
    String name;
    String profileImage;
    Integer age;
    String phoneNumber;

    public static UserDto convertToUserDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .profileImage(user.getProfileImage())
                .age(user.getAge())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
