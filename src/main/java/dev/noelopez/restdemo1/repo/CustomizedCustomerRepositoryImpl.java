package dev.noelopez.restdemo1.repo;

import dev.noelopez.restdemo1.model.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.util.*;

public class CustomizedCustomerRepositoryImpl implements CustomizedCustomerRepository{
    @PersistenceContext
    private EntityManager entityManager;
    public List<Customer> findByAllFields(Customer customer) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> query = cb.createQuery(Customer.class);
        Root<Customer> root = query.from(Customer.class);
        query.select(root).where(buildPredicates(customer,cb,root));

        return entityManager.createQuery(query).getResultList();
    }

    private Predicate[] buildPredicates(Customer customer, CriteriaBuilder cb, Root<Customer> root) {
        List<Predicate> predicates = new ArrayList<>();
        Join<Object, Object> details = (Join<Object, Object>) root.fetch("details");

        if (Objects.nonNull(customer.getName()))
            predicates.add(cb.like(root.get("name"), "%"+customer.getName()+"%"));
        if (Objects.nonNull(customer.getStatus()))
            predicates.add(cb.equal(root.get("status"), customer.getStatus()));
        if (Objects.nonNull( customer.getDetails().getInfo()))
            predicates.add(cb.equal(details.get("info"), customer.getDetails().getInfo()));
        if (Objects.nonNull(customer.getDetails().isVip()))
            predicates.add(cb.equal(details.get("vip"), customer.getDetails().isVip()));

        return predicates.toArray(new Predicate[0]);
    }
//
//    public List<Customer> findByAllFields(Customer customer) {
//        EntityGraph<Customer> graph = entityManager.createEntityGraph(Customer.class);
//        graph.addAttributeNodes("details");
//
//        Map<String, Object> hints = new HashMap<>();
//        hints.put("javax.persistence.loadgraph", graph);
//
//        return (List<Customer>) entityManager.find(Customer.class, customer, hints);
//    }
}
