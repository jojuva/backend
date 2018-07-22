package com.jojuva.backend.repository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.jojuva.backend.dto.SummaryDto;
import com.jojuva.backend.model.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepositoryService {
	
	
	@PersistenceContext
	private EntityManager em;

	@Transactional
	@SuppressWarnings("unchecked")
	public boolean createTransaction(double amount, long timestamp) {
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setTimestamp(timestamp);
		em.persist(transaction);

		long latest = LocalDateTime.now()
				.minus(60, ChronoUnit.SECONDS)
				.atZone(ZoneOffset.UTC)
				.toInstant()
				.toEpochMilli();

		if (timestamp >= latest){
			return true;
		} else {
			return false;
		}

	}

	@SuppressWarnings("unchecked")
	public SummaryDto getSummary() {

		SummaryDto summaryDto = new SummaryDto();
		summaryDto.setSum(getSum());
		summaryDto.setAvg(getAvg());
		summaryDto.setMax(getMax());
		summaryDto.setMin(getMin());
		summaryDto.setCount(getCount());

		return summaryDto;
	}

	private double getSum() {
		String sql= "SELECT COALESCE(SUM(t.amount),0) FROM Transaction t WHERE t.timestamp >=:latest";
		Query query = em.createQuery(sql);
		query.setParameter("latest", LocalDateTime.now()
				.minus(60, ChronoUnit.SECONDS)
				.atZone(ZoneOffset.UTC)
				.toInstant()
				.toEpochMilli());
		double sum = (Double) query.getSingleResult();

		return sum;
	}

	private double getAvg() {
		String sql= "SELECT COALESCE(AVG(t.amount),0) FROM Transaction t WHERE t.timestamp >=:latest";
		Query query = em.createQuery(sql);
		query.setParameter("latest", LocalDateTime.now()
				.minus(60, ChronoUnit.SECONDS)
				.atZone(ZoneOffset.UTC)
				.toInstant()
				.toEpochMilli());
		double avg = (Double) query.getSingleResult();

		return avg;
	}

	private double getMax() {
		String sql= "SELECT COALESCE(MAX(t.amount),0) FROM Transaction t WHERE t.timestamp >=:latest";
		Query query = em.createQuery(sql);
		query.setParameter("latest", LocalDateTime.now()
				.minus(60, ChronoUnit.SECONDS)
				.atZone(ZoneOffset.UTC)
				.toInstant()
				.toEpochMilli());
		double max = (Double) query.getSingleResult();

		return max;
	}

	private double getMin() {
		String sql= "SELECT COALESCE(MIN(t.amount),0) FROM Transaction t WHERE t.timestamp >=:latest";
		Query query = em.createQuery(sql);
		query.setParameter("latest", LocalDateTime.now()
				.minus(60, ChronoUnit.SECONDS)
				.atZone(ZoneOffset.UTC)
				.toInstant()
				.toEpochMilli());
		double min = (Double) query.getSingleResult();

		return min;
	}

	private long getCount() {
		String sql= "SELECT COALESCE(COUNT(t),0) FROM Transaction t WHERE t.timestamp >=:latest";
		Query query = em.createQuery(sql);
		query.setParameter("latest", LocalDateTime.now()
				.minus(60, ChronoUnit.SECONDS)
				.atZone(ZoneOffset.UTC)
				.toInstant()
				.toEpochMilli());
		long count = (Long) query.getSingleResult();

		return count;
	}
}
