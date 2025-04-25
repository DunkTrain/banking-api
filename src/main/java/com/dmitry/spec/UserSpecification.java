package com.dmitry.spec;

import com.dmitry.dto.UserSearchCriteriaDto;
import com.dmitry.entity.Users;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

/**
 * Спецификация для поиска пользователей по заданным фильтрам.
 * <p>
 * Используется для построения динамических запросов в Spring Data JPA.
 * Позволяет фильтровать пользователей по:
 * <ul>
 *     <li>имени (начинается с...)</li>
 *     <li>email (точное совпадение)</li>
 *     <li>телефону (точное совпадение)</li>
 *     <li>дате рождения (позже указанной)</li>
 * </ul>
 * Также включает загрузку связанных сущностей (emails, phones, account) через {@code fetch join},
 * чтобы избежать проблемы N+1.
 */
public class UserSpecification {

    /**
     * Возвращает спецификацию фильтрации пользователей на основе переданных критериев.
     *
     * @param criteria параметры фильтрации, полученные из запроса
     * @return {@link Specification} для применения в {@code UserRepository.findAll(...)}
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
