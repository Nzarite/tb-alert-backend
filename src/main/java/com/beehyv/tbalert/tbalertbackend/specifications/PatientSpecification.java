package com.beehyv.tbalert.tbalertbackend.specifications;

import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.mapper.LocalDateMapper;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Component
@AllArgsConstructor
public class PatientSpecification {

    private final LocalDateMapper localDateMapper;

    public Specification<Patient> getPatientsByFilter(Map<String, Object> criteria) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (criteria.containsKey("startDate") && criteria.get("startDate")!=null &&  criteria.containsKey("endDate") && criteria.get("endDate")!=null) {
                LocalDate startDate = localDateMapper.toLocalDate((String)criteria.get("startDate"));
                LocalDateTime startDateTime=startDate.atStartOfDay();

                LocalDate endDate = localDateMapper.toLocalDate((String) criteria.get("endDate"));
                LocalDateTime endDateTime=endDate.atTime(23, 59, 59);

                predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("person").get("createdOn"), startDateTime, endDateTime));
            }
            if (criteria.containsKey("gender") && criteria.get("gender")!=null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("person").get("gender"), criteria.get("gender")));
            }
            if (criteria.containsKey("age")) {
                Integer ageInt = (Integer) criteria.get("age");
                if(ageInt>0) {
                    LocalDate date = LocalDate.now();
                    LocalDate minDob = date.minusYears((ageInt) + 1L);
                    LocalDate maxDob = date.minusYears(ageInt);
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("person").get("dateOfBirth"), minDob, maxDob));
                }
            }
            if (criteria.containsKey("currentStatus") && criteria.get("currentStatus")!=null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("currentStatus"), criteria.get("currentStatus")));
            }
            if (criteria.containsKey("cured") && criteria.get("cured")!=null) {
                boolean curedBool = (boolean) criteria.get("cured");
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("cured"), curedBool));
            }
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("person").get("isDeleted"), false));
            return predicate;
        };
    }
}
