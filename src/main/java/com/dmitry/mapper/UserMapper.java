package com.dmitry.mapper;

import com.dmitry.dto.responce.EmailResponseDto;
import com.dmitry.dto.responce.PhoneResponseDto;
import com.dmitry.dto.responce.UserResponseDto;
import com.dmitry.entity.Users;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Компонент для преобразования сущности {@link Users} в DTO {@link UserResponseDto}.
 */
@Component
public class UserMapper {

    /**
     * Преобразует пользователя в DTO для получения профиля
     *
     * @param user сущность пользователя
     * @return DTO для профиля
     */
    public UserResponseDto toDto(Users user) {
        if (user == null) {
            return null;
        }

        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setDateOfBirth(user.getDateOfBirth());

        dto.setEmails(
                user.getEmails().stream()
                        .map(email -> new EmailResponseDto(email.getEmail()))
                        .collect(Collectors.toSet())
        );

        dto.setPhones(
                user.getPhones().stream()
                        .map(phone -> new PhoneResponseDto(phone.getPhone()))
                        .collect(Collectors.toSet())
        );

        dto.setBalance(user.getAccount().getBalance());

        return dto;
    }
}
