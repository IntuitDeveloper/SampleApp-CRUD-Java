package com.intuit.developer.sampleapp.crud.entities.recurringtransaction;


import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.IntuitEntity;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;
import com.intuit.ipp.data.RecurringTransaction;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.util.List;

public class RecurringTransactionQuery {

    private static final org.slf4j.Logger LOG = Logger.getLogger();

    public static void main(String[] args) {
        try {
            queryRecurringTxn();
        } catch (Exception e) {
            LOG.error("Error during CRUD", e.getCause());
        }
    }

    public static void queryRecurringTxn() throws Exception {

        try {
            DataService service = DataServiceFactory.getDataService();
            String sql = "select * from RecurringTransaction";
            QueryResult queryResult = service.executeQuery(sql);
            int count = queryResult.getEntities().size();

            LOG.info("Total number of Recurring Transactions: " + count);

            List<RecurringTransaction> txns = (List<RecurringTransaction>)queryResult.getEntities();

            for (RecurringTransaction txn : txns) {
                IntuitEntity intuitEntity = txn.getIntuitObject().getValue();
                LOG.info("Recurring txn type: " + intuitEntity.getClass().getName());
                LOG.info("Recurring txn ID: " + intuitEntity.getId());
            }


        }

        catch (FMSException e) {
            List<Error> list = e.getErrorList();
            list.forEach(error -> LOG.error("Error while calling entity add:: " + error.getMessage()));
        }

        catch (Exception e) {
            LOG.error("" + e.getMessage());
            LOG.error("" + e.getStackTrace());
        }
    }
}
