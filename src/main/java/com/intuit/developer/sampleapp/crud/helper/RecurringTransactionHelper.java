package com.intuit.developer.sampleapp.crud.helper;

import com.intuit.ipp.core.IEntity;
import com.intuit.ipp.data.*;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.DateUtils;
import org.apache.commons.lang.RandomStringUtils;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RecurringTransactionHelper {

    private RecurringTransactionHelper() {

    }

    public static RecurringInfo getRecurringTransactionFields(DataService service) throws FMSException, ParseException {
        RecurringInfo recurringInfo = new RecurringInfo();
        recurringInfo.setRecurType("Automated");
        recurringInfo.setName(RandomStringUtils.randomAlphabetic(6));
        recurringInfo.setActive(true);


        RecurringScheduleInfo scheduleInfo = new RecurringScheduleInfo();
        scheduleInfo.setStartDate(Calendar.getInstance().getTime());
        scheduleInfo.setMaxOccurrences(10);
        scheduleInfo.setIntervalType("Monthly");
        scheduleInfo.setNumInterval(1);
        scheduleInfo.setDayOfMonth(1);
        scheduleInfo.setDaysBefore(2);

        recurringInfo.setScheduleInfo(scheduleInfo);
        return recurringInfo;
    }
}
