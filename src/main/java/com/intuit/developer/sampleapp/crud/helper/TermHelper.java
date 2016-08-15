package com.intuit.developer.sampleapp.crud.helper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.intuit.ipp.data.ReferenceType;
import com.intuit.ipp.data.Term;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;

/**
 * @author dderose
 *
 */
public final class TermHelper {

	private TermHelper() {
		
	}

	public static Term getTermFields() throws FMSException {

		Term term = new Term();
		term.setName("Term_" + RandomStringUtils.randomAlphanumeric(5));
		term.setActive(true);
		term.setType("STANDARD");
		term.setDiscountPercent(new BigDecimal("50.00"));
		term.setDueDays(50);
		return term;
	}

	public static Term getTerm(DataService service) throws FMSException {
		List<Term> terms = (List<Term>) service.findAll(new Term());
		if (!terms.isEmpty()) {
			return terms.get(0);
		}
		return createTerm(service);
	}

	private static Term createTerm(DataService service) throws FMSException {
		return service.add(getTermFields());
	}

	public static ReferenceType getTermRef(Term term) {
		ReferenceType termRef = new ReferenceType();
		termRef.setName(term.getName());
		termRef.setValue(term.getId());
		return termRef;
	}

}
