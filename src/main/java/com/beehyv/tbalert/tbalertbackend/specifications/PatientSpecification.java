package com.beehyv.tbalert.tbalertbackend.specifications;

import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.entity.Person;
import com.beehyv.tbalert.tbalertbackend.entity.TBDetails;
import com.beehyv.tbalert.tbalertbackend.mapper.LocalDateMapper;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Component
@AllArgsConstructor
public class PatientSpecification {

    private final LocalDateMapper localDateMapper;

    public Specification<Patient> getPatientsByFilter(Map<String, Object> criteria) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            Join<Patient, Person> personJoin = root.join("person", JoinType.INNER);

            if (criteria.containsKey("startDate") && criteria.get("startDate") != null &&
                    criteria.containsKey("endDate") && criteria.get("endDate") != null) {
                LocalDate startDate = localDateMapper.toLocalDate((String) criteria.get("startDate"));
                LocalDateTime startDateTime = startDate.atStartOfDay();

                LocalDate endDate = localDateMapper.toLocalDate((String) criteria.get("endDate"));
                LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

                predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(personJoin.get("createdOn"), startDateTime, endDateTime));
            }

            if (criteria.containsKey("gender") && criteria.get("gender") != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(personJoin.get("gender"), criteria.get("gender")));
            }

            if (criteria.containsKey("age")) {
                Integer ageInt = (Integer) criteria.get("age");
                if (ageInt > 0) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("age"), ageInt, ageInt + 1));
                }
            }

            if (criteria.containsKey("currentStatus") && criteria.get("currentStatus") != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("currentStatus"), criteria.get("currentStatus")));
            }

            if (criteria.containsKey("cured") && criteria.get("cured") != null) {
                boolean curedBool = (boolean) criteria.get("cured");
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("cured"), curedBool));
            }
            if(criteria.containsKey("state") && criteria.get("state") != null && !criteria.get("state").equals("")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(personJoin.get("address").get("state"), criteria.get("state")));
            }
            return predicate;
        };

    }
}
