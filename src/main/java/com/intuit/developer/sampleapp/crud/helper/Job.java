package com.intuit.developer.sampleapp.crud.helper;

import java.text.ParseException;

import com.intuit.ipp.data.JobInfo;
import com.intuit.ipp.data.JobStatusEnum;
import com.intuit.ipp.util.DateUtils;

/**
 * @author dderose
 *
 */
public final class Job {
	
	private Job() {
		
	}

	public static JobInfo getJobInfo() throws ParseException {
		JobInfo jobInfo = new JobInfo();
		jobInfo.setDescription("In Progress");
		jobInfo.setStatus(JobStatusEnum.IN_PROGRESS);
		jobInfo.setStartDate(DateUtils.getDateWithPrevDays(2));
		jobInfo.setEndDate(DateUtils.getDateWithNextDays(5));
		jobInfo.setProjectedEndDate(DateUtils.getDateWithNextDays(5));
		return jobInfo;
	}

}
