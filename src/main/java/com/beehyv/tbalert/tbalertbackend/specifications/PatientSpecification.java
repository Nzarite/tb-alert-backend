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

            String gender="gender";
            String name="name";
            String dateOfBirth="dateOfBirth";
            String currentStatus="currentStatus";
            String healthLevel="healthLevel";
            String age="age";
            String cured="cured";

            if(criteria.containsKey("startDate") && criteria.containsKey("endDate")) {
                LocalDate startDate = (LocalDate) criteria.get("startDate");
                LocalDate endDate = (LocalDate) criteria.get("endDate");
                predicate=criteriaBuilder.and(predicate,criteriaBuilder.between(root.get("dateOfBirth"), startDate, endDate));
            }
            if(criteria.containsKey(gender)) {
                predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get(gender), criteria.get(gender)));
            }
            if(criteria.containsKey(age)) {
                Integer ageInt = (Integer) criteria.get(age);
                LocalDate date=LocalDate.now();
                LocalDate minDob=date.minusYears((ageInt)+(long)1);
                LocalDate maxDob=date.minusYears(ageInt);
                predicate= criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("dateOfBirth"), minDob, maxDob));
            }
            if(criteria.containsKey(currentStatus) ) {
                String currentStatusStr = (String) criteria.get(currentStatus);
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(currentStatus), currentStatusStr));
            }

            if(criteria.containsKey(cured))
            {
                boolean curedBool=(boolean) criteria.get(cured);
                predicate= criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(cured), curedBool));
            }

            if(criteria.containsKey(healthLevel))
            {
                Integer healthLevelInt = (Integer) criteria.get(healthLevel);
                predicate= criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(healthLevel), healthLevelInt));
            }

            return predicate;
        };
    }
}
