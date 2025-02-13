package com.beehyv.tbalert.tbalertbackend.specifications;

import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Map;

public class PatientSpecification {

    public static Specification<Patient> getPatientsByFilter(Map<String, Object> criteria) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (criteria.containsKey("startDate") && criteria.containsKey("endDate")) {
                LocalDate startDate = (LocalDate) criteria.get("startDate");
                LocalDate endDate = (LocalDate) criteria.get("endDate");
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("person").get("dateOfBirth"), startDate, endDate));
            }
            if (criteria.containsKey("gender")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("person").get("gender"), criteria.get("gender")));
            }
            if (criteria.containsKey("age")) {
                Integer ageInt = (Integer) criteria.get("age");
                LocalDate date = LocalDate.now();
                LocalDate minDob = date.minusYears((ageInt) + 1L);
                LocalDate maxDob = date.minusYears(ageInt);
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("person").get("dateOfBirth"), minDob, maxDob));
            }
            if (criteria.containsKey("currentStatus")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("currentStatus"), criteria.get("currentStatus")));
            }
            if (criteria.containsKey("cured")) {
                boolean curedBool = (boolean) criteria.get("cured");
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("cured"), curedBool));
            }
            return predicate;
        };
    }
}
