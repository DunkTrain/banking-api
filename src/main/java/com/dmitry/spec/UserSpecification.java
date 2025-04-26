package com.dmitry.spec;

import com.dmitry.dto.responce.UserSearchCriteriaDto;
import com.dmitry.entity.Users;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

/**
 * Спецификация для поиска пользователей по заданным фильтрам.
 * <p>
 * Используется для построения динамических запросов в Spring Data JPA.
 * Отвечает только за условия фильтрации, не загружает связанные сущности.
 */
public class UserSpecification {

    /**
     * Строит спецификацию поиска пользователей на основе переданных критериев.
     *
     * @param criteria параметры фильтрации
     * @return спецификация для применения в репозитории
     */
    public static Specification<Users> withFilters(UserSearchCriteriaDto criteria) {
        return (root, query, cb) -> {
            if(query != null) {
                query.distinct(true);
            }
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
