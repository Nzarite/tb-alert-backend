package com.beehyv.tbalert.tbalertbackend.specifications;


import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

public class PatientSpecification {

    public static Specification<Patient> getPatientsByFilter(Map<String, Object> criteria) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            String gender="gender";
            if(criteria.containsKey("startDate") && criteria.containsKey("endDate")) {
                LocalDate startDate = (LocalDate) criteria.get("startDate");
                LocalDate endDate = (LocalDate) criteria.get("endDate");
                predicate=criteriaBuilder.and(predicate,criteriaBuilder.between(root.get("dateOfBirth"), startDate, endDate));
            }
            if(criteria.containsKey(gender)) {
                predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get(gender), criteria.get(gender)));
            }
            if(criteria.containsKey("age")) {
                Integer age = (Integer) criteria.get("age");
                LocalDate date=LocalDate.now();
                LocalDate minDob=date.minusYears((age)+(long)1);
                LocalDate maxDob=date.minusYears(age);
                predicate= criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("dateOfBirth"), minDob, maxDob));
            }
            return predicate;
        };
    }
}
