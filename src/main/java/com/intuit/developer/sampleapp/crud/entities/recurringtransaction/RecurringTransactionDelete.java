package com.intuit.developer.sampleapp.crud.entities.recurringtransaction;


import com.intuit.developer.sampleapp.crud.helper.InvoiceHelper;
import com.intuit.developer.sampleapp.crud.helper.RecurringTransactionHelper;
import com.intuit.developer.sampleapp.crud.qbo.DataServiceFactory;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.*;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;

import java.util.List;

public class RecurringTransactionDelete {

    private static final org.slf4j.Logger LOG = Logger.getLogger();

    public static void main(String[] args) {
        try {
            deleteRecurringTxn();
        } catch (Exception e) {
            LOG.error("Error during CRUD", e.getCause());
        }
    }

    public static void deleteRecurringTxn() throws Exception {
        try {
            DataService service = DataServiceFactory.getDataService();
            Invoice invoice = InvoiceHelper.getInvoiceFields(service);
            RecurringInfo recurringInfo = RecurringTransactionHelper.getRecurringTransactionFields(service);

            invoice.setRecurringInfo(recurringInfo);
            RecurringTransaction recurringTransaction = new RecurringTransaction();

            //takes the JaxBElement = use Object factory to set the desired recurring txn
            recurringTransaction.setIntuitObject(new ObjectFactory().createInvoice(invoice));
            RecurringTransaction rt = service.add(recurringTransaction);

            //Use the line below if you dont know which entity it is
            //IntuitEntity intuitEntity = rt.getIntuitObject().getValue();

            //grab inner entity and cast it to the it's entity type to work with it.
            Invoice theTransaction = (Invoice) rt.getIntuitObject().getValue();
            LOG.info("Recurring Invoice " + theTransaction.getId() +
                    " created for customer: "  + theTransaction.getCustomerRef().getName());

            RecurringTransaction deletedRT = service.delete(rt);


            Invoice deletedTxn = (Invoice) deletedRT.getIntuitObject().getValue();

            LOG.info("Recurring Txn deleted : " + deletedTxn.getId() + ". Entity status ::: " + deletedRT.getStatus());
        } catch (FMSException e) {
            List<Error> list = e.getErrorList();
            list.forEach(error -> LOG.error("Error while calling entity add:: " + error.getMessage()));
        } catch (Exception e) {
            LOG.error("" + e.getMessage());
            LOG.error("" + e.getStackTrace());
        }
    }
}
