package com.intuit.developer.sampleapp.crud.entities.report;

import java.text.ParseException;
import java.util.List;

import com.intuit.developer.sampleapp.crud.qbo.ReportServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.Report;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.ReportName;
import com.intuit.ipp.services.ReportService;
import com.intuit.ipp.util.Logger;

/**
 * 
 * 
 * @author dderose
 *
 */
public class ReportQuery {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
		
	public static void main(String[] args) {
		try {
			queryReport();
		} catch (Exception e) {
			LOG.error("Error during CRUD", e.getCause());
		}
	}
	
	public static void queryReport() throws FMSException, ParseException {
		
		try {
			
			//build ReportService
			ReportService service = ReportServiceFactory.getReportService();
			
			//call BalanceSheet report
			service.setStart_date("2018-01-01"); 
			service.setAccounting_method("Accrual");
			Report report = service.executeReport(ReportName.BALANCESHEET.toString());
			LOG.info("ReportName -> name: " + report.getHeader().getReportName().toLowerCase());
			
			
			//call P&L report
			report = service.executeReport(ReportName.PROFITANDLOSS.toString());
			LOG.info("ReportName -> name: " + report.getHeader().getReportName().toLowerCase());
			
			
		} catch (FMSException e) {
			List<Error> list = e.getErrorList();
			list.forEach(error -> LOG.error("Error while calling executeQuery :: " + error.getMessage()));
		}
		
	}
}
