package com.sankalp.library_api.dao;

import com.sankalp.library_api.models.Book;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class BookDao {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Book> fetchAvailableBooks() {
        return entityManager.createNamedQuery("Book.findAllAvailableBooks", Book.class).getResultList();
    }

    public BigDecimal getLateFee(int daysLate) {
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("Book.calculateLateFee");

        query.setParameter("in_days_late", daysLate);

        query.execute();

        return (BigDecimal) query.getOutputParameterValue("out_total_fee");
    }
}
