package edu.msc.lambdas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by caldama on 5/30/17.
 */
public class PayChecks {
    private List<Date> holidays;

    public PayChecks() {
        holidays.add(new Date(2017, 0, 2 ));

    }

    public static List<PayPeriod> populatePaychecks(Date startDate) {
        List<PayPeriod> paychecks = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        for (int i = 1; i <= 26; i++) {
            Calendar end = Calendar.getInstance();
            end.setTime(c.getTime());
           // PayPeriod pp = new PayPeriod(start, end);
           // paychecks.add(pp);
            c.add(Calendar.DAY_OF_YEAR, 14);
        }
        return paychecks;
    }


    public static void main(String[] args) {
        Date d = new Date(2017, 0, 13);
        List<PayPeriod> payments = PayChecks.populatePaychecks(d);
       // payments.forEach(c -> System.out.println("c = " + c));

        String s = null;
        if (s.isEmpty()) {
            System.out.println("string is empty but no NPE???");
        }
    }
}


class PayPeriod {
    private Date startDate;
    private Date endDate;

    //Number of working days within the project.
    private int workingDays;
    public PayPeriod(Date startDate) {
        this(startDate, null, 10);
    }

    public PayPeriod(Date startDate, Date endDate, int workingDays) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.workingDays = workingDays;
    }

    public Date getStartDate() {

        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(int workingDays) {
        this.workingDays = workingDays;
    }
}
