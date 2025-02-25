package com.beehyv.tbalert.tbalertbackend.specifications;

import com.beehyv.tbalert.tbalertbackend.entity.NikshayMitra;
import com.beehyv.tbalert.tbalertbackend.entity.Patient;
import com.beehyv.tbalert.tbalertbackend.entity.Person;
import com.beehyv.tbalert.tbalertbackend.entity.TBDetails;
import com.beehyv.tbalert.tbalertbackend.mapper.LocalDateMapper;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Slf4j
@Component
@AllArgsConstructor
public class TBDeailsSpecification {

    private final LocalDateMapper localDateMapper;

    public Specification<TBDetails> getPatientsByFilter(Map<String, Object> criteria) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            Join<TBDetails, Patient> patientJoin = root.join("patient", JoinType.INNER);
            Join<Patient, Person> personJoin = patientJoin.join("person", JoinType.INNER);

            if (criteria.containsKey("dateOfDiagnosis") && criteria.get("dateOfDiagnosis") != null) {
                LocalDate diagnosisDate = localDateMapper.toLocalDate((String) criteria.get("dateOfDiagnosis"));
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("dateOfDiagnosis"), diagnosisDate));
            }

            if (criteria.containsKey("dstbOrDrtb") && criteria.get("dstbOrDrtb") != null && !criteria.get("dstbOrDrtb").toString().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("dstbOrDrtb"), criteria.get("dstbOrDrtb")));
            }

            if (criteria.containsKey("currentStatus") && criteria.get("currentStatus") != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(patientJoin.get("currentStatus"), criteria.get("currentStatus")));
            }

            if (criteria.containsKey("gender") && criteria.get("gender") != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(personJoin.get("gender"), criteria.get("gender")));
            }

            if (criteria.containsKey("state") && !criteria.get("state").toString().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(personJoin.get("address").get("state"), criteria.get("state")));
            }

            if(criteria.containsKey("createdBy") && !criteria.get("createdBy").toString().isEmpty()) {
                predicate=criteriaBuilder.and(predicate, criteriaBuilder.equal(personJoin.get("createdBy"), criteria.get("createdBy")));
            }

            predicate = addNikshayMitraSubquery(predicate, query, criteriaBuilder, patientJoin, criteria, "udstStatus");
            predicate = addNikshayMitraSubquery(predicate, query, criteriaBuilder, patientJoin, criteria, "dbtStatus");

            return predicate;
        };
    }

    private Predicate addNikshayMitraSubquery(Predicate predicate, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder,
                                              Join<TBDetails, Patient> patientJoin, Map<String, Object> criteria, String field) {
        if (criteria.containsKey(field) && !criteria.get(field).toString().isEmpty()) {
            Boolean status = Boolean.parseBoolean(criteria.get(field).toString());

            // Create a single subquery
            Subquery<Boolean> subquery = query.subquery(Boolean.class);
            Root<NikshayMitra> nikshayMitraRoot = subquery.from(NikshayMitra.class);

            subquery.select(nikshayMitraRoot.get(field))
                    .where(criteriaBuilder.equal(nikshayMitraRoot.get("patient"), patientJoin));

            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(subquery, status));
        }
        return predicate;
    }
}
