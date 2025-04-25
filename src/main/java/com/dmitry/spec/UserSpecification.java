package com.dmitry.spec;

import com.dmitry.dto.UserSearchCriteriaDto;
import com.dmitry.entity.Users;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

/**
 * Класс со спецификацией поиска пользователей по различным фильтрам.
 * <p>
 * Используется Spring Data JPA Specification API для построения динамических запросов.
 * Поддерживаются фильтры по имени (начало строки), email, телефону и дате рождения.
 * Связанные сущности (emails, phones, account) подгружаются через fetch join.
 */
public class UserSpecification {

    /**
     * Строит спецификацию поиска по фильтрам.
     *
     * @param criteria DTO с фильтрами поиска
     * @return спецификация для репозитория
     */
    public static Specification<Users> withFilters(UserSearchCriteriaDto criteria) {
        return (root, query, cb) -> {
            root.fetch("emails", JoinType.LEFT);
            root.fetch("phones", JoinType.LEFT);
            root.fetch("account", JoinType.LEFT);
            //noinspection DataFlowIssue
            query.distinct(true);

            Predicate predicate = cb.conjunction();

            if (criteria.getName() != null && !criteria.getName().isBlank()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("name")), criteria.getName().toLowerCase() + "%"));
            }

            if (criteria.getEmail() != null && !criteria.getEmail().isBlank()) {
                predicate = cb.and(predicate,
                        cb.equal(
                                root.join("emails", JoinType.INNER).get("email"),
                                criteria.getEmail()
                        )
                );
            }

            if (criteria.getPhone() != null && !criteria.getPhone().isBlank()) {
                predicate = cb.and(predicate,
                        cb.equal(
                                root.join("phones", JoinType.INNER).get("phone"),
                                criteria.getPhone()
                        )
                );
            }

            if (criteria.getDateOfBirthAfter() != null) {
                predicate = cb.and(predicate,
                        cb.greaterThan(root.get("dateOfBirth"), criteria.getDateOfBirthAfter())
                );
            }

            return predicate;
        };
    }
}
