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
public class TBDetailsSpecification {

    private final LocalDateMapper localDateMapper;
    private static final String START_DATE ="START_DATE";
    private static final String END_DATE ="END_DATE";
    private static final String STATE ="state";
    private static final String GENDER ="GENDER";
    private static final String AGE ="age";
    private static final String CURRENT_STATUS ="CURRENT_STATUS";
    private static final String DSTB_OR_DRTB ="DSTB_OR_DRTB";
    private static final String CREATED_BY ="CREATED_BY";
    
    public Specification<TBDetails> getPatientsByFilter(Map<String, Object> criteria) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            Join<TBDetails, Patient> patientJoin = root.join("patient", JoinType.INNER);
            Join<Patient, Person> personJoin = patientJoin.join("person", JoinType.INNER);

            if (criteria.containsKey(START_DATE) && criteria.get(START_DATE) != null && criteria.containsKey(END_DATE) && criteria.get(END_DATE) != null) {
                LocalDate diagnosisDateStart = localDateMapper.toLocalDate((String) criteria.get(START_DATE));
                LocalDate diagnosisDateEnd = localDateMapper.toLocalDate((String) criteria.get(END_DATE));
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("dateOfDiagnosis"), diagnosisDateStart, diagnosisDateEnd));
            }

            if (criteria.containsKey(DSTB_OR_DRTB) && criteria.get(DSTB_OR_DRTB) != null && !criteria.get(DSTB_OR_DRTB).toString().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(DSTB_OR_DRTB), criteria.get(DSTB_OR_DRTB)));
            }

            if (criteria.containsKey(CURRENT_STATUS) && criteria.get(CURRENT_STATUS) != null && !criteria.get(CURRENT_STATUS).toString().isEmpty()) {
                String currentStatusString = (String) criteria.get(CURRENT_STATUS);
                predicate = switch (currentStatusString) {
                    case "dead" ->
                            criteriaBuilder.and(predicate, criteriaBuilder.equal(patientJoin.get(CURRENT_STATUS), "dead"));
                    case "alive" ->
                            criteriaBuilder.and(predicate, criteriaBuilder.equal(patientJoin.get(CURRENT_STATUS), "alive"));
                    case "cured" ->
                            criteriaBuilder.and(predicate, criteriaBuilder.equal(patientJoin.get("cured"), true));
                    default -> predicate;
                };
            }

            if (criteria.containsKey(GENDER) && criteria.get(GENDER) != null && !criteria.get(GENDER).toString().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(personJoin.get(GENDER), criteria.get(GENDER)));
            }

            if (criteria.containsKey(STATE) && !criteria.get(STATE).toString().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(personJoin.get("address").get(STATE).get("stateName"), criteria.get(STATE)));
            }

            if(criteria.containsKey(AGE)) {
                Integer ageInt = criteria.get(AGE) instanceof Number ? ((Number) criteria.get(AGE)).intValue() : null;
                if (ageInt != null && ageInt > 0) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(patientJoin.get(AGE), ageInt));
                }
            }

            if(criteria.containsKey(CREATED_BY) && !criteria.get(CREATED_BY).toString().isEmpty()) {
                predicate=criteriaBuilder.and(predicate, criteriaBuilder.equal(personJoin.get(CREATED_BY), criteria.get(CREATED_BY)));
            }

            predicate = addNikshayMitraSubquery(predicate, query, criteriaBuilder, patientJoin, criteria, "udstStatus");
            predicate = addNikshayMitraSubquery(predicate, query, criteriaBuilder, patientJoin, criteria, "dbtStatus");

            if(criteria.containsKey("isDeleted") && criteria.get("isDeleted").equals(false))
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(personJoin.get("isDeleted"), Boolean.FALSE));
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
