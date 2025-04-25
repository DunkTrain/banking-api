package com.dmitry.service.impl;

import com.dmitry.entity.Email;
import com.dmitry.entity.Users;
import com.dmitry.exception.DuplicateEmailException;
import com.dmitry.exception.ForbiddenOperationException;
import com.dmitry.repository.EmailRepository;
import com.dmitry.repository.UserRepository;
import com.dmitry.service.EmailService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация {@link EmailService}.
 * Использует JPA и Hibernate для работы с email.
 */
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void addEmail(Long userId, String email) {
        if (emailRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(email);
        }

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));

        Email emailEntity = new Email();
        emailEntity.setEmail(email);
        emailEntity.setUser(user);

        emailRepository.save(emailEntity);
    }

    @Override
    @Transactional
    public void updateEmail(Long userId, Long emailId, String newEmail) {
        Email emailEntity = emailRepository.findById(emailId)
                .orElseThrow(() -> new EntityNotFoundException("Email не найден"));

        if (!emailEntity.getUser().getId().equals(userId)) {
            throw new ForbiddenOperationException("Вы не можете редактировать чужой email");
        }

        if (emailRepository.existsByEmail(newEmail)) {
            throw new DuplicateEmailException(newEmail);
        }

        emailEntity.setEmail(newEmail);
        emailRepository.save(emailEntity);
    }

    @Override
    @Transactional
    public void removeEmail(Long userId, Long emailId) {
        Email emailEntity = emailRepository.findById(emailId)
                .orElseThrow(() -> new EntityNotFoundException("Email не найден"));

        if (!emailEntity.getUser().getId().equals(userId)) {
            throw new ForbiddenOperationException("Вы не можете удалить чужой email");
        }

        long emailCount = emailRepository.countByUserId(userId);

        if (emailCount <= 1) {
            throw new ForbiddenOperationException(
                    "Нельзя удалить последний email: у пользователя должен быть хотя бы один email"
            );
        }

        emailRepository.delete(emailEntity);
    }
}
