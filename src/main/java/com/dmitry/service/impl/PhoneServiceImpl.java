package com.dmitry.service.impl;

import com.dmitry.entity.Phone;
import com.dmitry.entity.Users;
import com.dmitry.exception.DuplicatePhoneException;
import com.dmitry.exception.ForbiddenOperationException;
import com.dmitry.repository.PhoneRepository;
import com.dmitry.repository.UserRepository;
import com.dmitry.service.PhoneService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация {@link PhoneService}.
 * Использует JPA и Hibernate для работы с phone.
 */
@Service
@RequiredArgsConstructor
public class PhoneServiceImpl implements PhoneService {

    private final PhoneRepository phoneRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void addPhone(Long userId, String phone) {
        if (phoneRepository.existsByPhone(phone)) {
            throw new DuplicatePhoneException(phone);
        }

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));

        Phone phoneEntity = new Phone();
        phoneEntity.setPhone(phone);
        phoneEntity.setUser(user);

        phoneRepository.save(phoneEntity);
    }

    @Override
    @Transactional
    public void updatePhone(Long userId, Long phoneId, String newPhone) {
        Phone phoneEntity = phoneRepository.findById(phoneId)
                .orElseThrow(() -> new EntityNotFoundException("Телефон не найден"));

        if(!phoneEntity.getUser().getId().equals(userId)) {
            throw new ForbiddenOperationException("Вы не можете редактировать чужой телефон");
        }

        if (phoneRepository.existsByPhone(newPhone)) {
            throw new DuplicatePhoneException(newPhone);
        }

        phoneEntity.setPhone(newPhone);
        phoneRepository.save(phoneEntity);
    }

    @Override
    @Transactional
    public void removePhone(Long userId, Long phoneId) {
        Phone phoneEntity = phoneRepository.findById(phoneId)
                .orElseThrow(() -> new EntityNotFoundException("Телефон не найден"));

        if (!phoneEntity.getUser().getId().equals(userId)) {
            throw new ForbiddenOperationException("Вы не можете удалить чужой телефон");
        }

        long phoneCount = phoneRepository.countByUserId(userId);

        if (phoneCount <= 1) {
            throw new ForbiddenOperationException(
                    "Нельзя удалить последний телефон: у пользователя должен быть хотя бы один телефон"
            );
        }

        phoneRepository.delete(phoneEntity);
    }
}
