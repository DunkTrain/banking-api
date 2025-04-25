package com.dmitry.mapper;

import com.dmitry.dto.EmailDTO;
import com.dmitry.dto.PhoneDTO;
import com.dmitry.dto.UsersDTO;
import com.dmitry.entity.Users;
import org.springframework.stereotype.Component;

/**
 * Компонент для преобразования сущности {@link Users} в DTO {@link UsersDTO}.
 * <p>
 * Используется в сервисе для возврата профиля пользователя.
 */
@Component
public class UserMapper {

    public UsersDTO toDto(Users user) {
        if (user == null) return null;

        UsersDTO dto = new UsersDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setEmails(
                user.getEmails().stream()
                        .map(email -> new EmailDTO(email.getEmail()))
                        .toList()
        );
        dto.setPhones(
                user.getPhones().stream()
                        .map(phone -> new PhoneDTO(phone.getPhone()))
                        .toList()
        );
        dto.setBalance(user.getAccount().getBalance());

        return dto;
    }
}
