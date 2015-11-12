package com.cronutils.model.time;

import com.cronutils.model.Cron;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.SimpleTimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class ExecutionTimeExtraCustomDefinitionIntegrationTest {

    private CronDefinition cronDefinition;
    private CronParser parser;

    private String cronExpression;
    private DateTime localDate;
    private DateTime expectedPreviousDate1;
    private DateTime expectedPreviousDate3;

    @Before
    public void setUp() {
        cronDefinition = CronDefinitionBuilder.defineCron()
                .withMinutes().and()
                .withHours().and()
                .withDayOfMonth().and()
                .withMonth().and()
                .withDayOfWeek().withValidRange(0, 7).withMondayDoWValue(1).withIntMapping(7, 0).supportsHash().supportsL().supportsW().and()
                .instance();
        parser = new CronParser(cronDefinition);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Collection<Object[]> data = new ArrayList<Object[]>();

        // ALWAYS (every minute)
        data.add(new Object[]{ "* * * * *",   "2015-08-10T21:50:37Z", "2015-08-10T21:50:00Z", "2015-08-10T21:48:00Z" });

        // MINUTES
        data.add(new Object[]{ "10 * * * *",             "2015-08-10T21:47:27Z", "2015-08-10T21:10:00Z", "2015-08-10T19:10:00Z" });
        data.add(new Object[]{ "10,20,30 * * * *",       "2015-08-10T21:47:27Z", "2015-08-10T21:30:00Z", "2015-08-10T21:10:00Z" });
        data.add(new Object[]{ "*/2 * * * *",            "2015-08-10T21:47:27Z", "2015-08-10T21:46:00Z", "2015-08-10T21:42:00Z" });
        data.add(new Object[]{ "*/7 * * * *",            "2015-08-10T21:47:27Z", "2015-08-10T21:42:00Z", "2015-08-10T21:28:00Z" });
        //data.add(new Object[]{ "2/7 * * * *",            "2015-08-10T21:47:27Z", "2015-08-10T21:44:00Z", "2015-08-10T21:30:00Z" });
        //data.add(new Object[]{ "5/8,2/7 * * * *",        "2015-08-10T21:47:27Z", "2015-08-10T21:45:00Z", "2015-08-10T21:37:00Z" });
        data.add(new Object[]{ "15-17 * * * *",          "2015-08-10T21:47:27Z", "2015-08-10T21:17:00Z", "2015-08-10T21:15:00Z" });
        //data.add(new Object[]{ "11-12,46-48 * * * *",    "2015-08-10T21:47:27Z", "2015-08-10T21:46:00Z", "2015-08-10T21:11:00Z" });
        //data.add(new Object[]{ "11-12,22/24,44 * * * *", "2015-08-10T21:47:27Z", "2015-08-10T21:46:00Z", "2015-08-10T21:22:00Z" });
        //data.add(new Object[]{ "4-30/3 * * * *",         "2015-08-10T21:47:27Z", "2015-08-10T21:28:00Z", "2015-08-10T21:22:00Z" });
        //data.add(new Object[]{ "4-30/3 * * * *",         "2015-08-10T21:08:27Z", "2015-08-10T21:07:00Z", "2015-08-10T20:28:00Z" });
        //data.add(new Object[]{ "3-30/7 * * * *",         "2015-08-10T21:08:27Z", "2015-08-10T21:03:00Z", "2015-08-10T20:17:00Z" });
        //data.add(new Object[]{ "11-12,22/24,44 * * * *", "2015-08-10T21:47:27Z", "2015-08-10T21:46:00Z", "2015-08-10T21:22:00Z" });

        // HOURS
        data.add(new Object[]{ "20 10 * * *",            "2015-08-10T21:47:27Z", "2015-08-10T10:20:00Z", "2015-08-08T10:20:00" });
        data.add(new Object[]{ "20 10,11,12 * * *",      "2015-08-10T21:47:27Z", "2015-08-10T12:20:00Z", "2015-08-10T10:20:00" });
        data.add(new Object[]{ "20 */2 * * *",           "2015-08-10T21:47:27Z", "2015-08-10T20:20:00Z", "2015-08-10T16:20:00" });
        data.add(new Object[]{ "20 */7 * * *",           "2015-08-10T21:47:27Z", "2015-08-10T21:20:00Z", "2015-08-10T07:20:00" });
        //data.add(new Object[]{ "20 2/7 * * *",           "2015-08-10T21:47:27Z", "2015-08-10T16:20:00Z", "2015-08-10T02:20:00" });
        data.add(new Object[]{ "20 5/8,2/7 * * *",       "2015-08-10T21:47:27Z", "2015-08-10T21:20:00Z", "2015-08-10T13:20:00" });
        data.add(new Object[]{ "20 15-17 * * *",         "2015-08-10T21:47:27Z", "2015-08-10T17:20:00Z", "2015-08-10T15:20:00" });
        data.add(new Object[]{ "20 11-12,20-21 * * *",   "2015-08-10T21:47:27Z", "2015-08-10T21:20:00Z", "2015-08-10T12:20:00" });
        //data.add(new Object[]{ "20 11-12,16/3,14 * * *", "2015-08-10T21:47:27Z", "2015-08-10T19:20:00Z", "2015-08-10T14:20:00" });

        // DAYS OF THE MONTH
        data.add(new Object[]{ "20 20 10 * *",            "2015-08-10T21:47:27Z", "2015-08-10T20:20:00Z", "2015-06-10T20:20:00Z" });
        data.add(new Object[]{ "20 20 10,11,12 * *",      "2015-08-10T21:47:27Z", "2015-08-10T20:20:00Z", "2015-07-11T20:20:00Z" });
        data.add(new Object[]{ "20 20 */2 * *",           "2015-08-10T21:47:27Z", "2015-08-10T20:20:00Z", "2015-08-06T20:20:00Z" });
        data.add(new Object[]{ "20 20 */7 * *",           "2015-08-10T21:47:27Z", "2015-08-07T20:20:00Z", "2015-07-21T20:20:00Z" });
        //data.add(new Object[]{ "20 20 2/7 * *",           "2015-08-10T21:47:27Z", "2015-08-09T20:20:00Z", "2015-07-30T20:20:00Z" });
        //data.add(new Object[]{ "20 20 5/8,2/7 * *",       "2015-08-10T21:47:27Z", "2015-08-09T20:20:00Z", "2015-08-02T20:20:00Z" });
        //data.add(new Object[]{ "20 20 2/7,5/8 * *",       "2015-08-10T21:47:27Z", "2015-08-09T20:20:00Z", "2015-08-02T20:20:00Z" });
        //data.add(new Object[]{ "20 20 15-17 * *",         "2015-08-10T21:47:27Z", "2015-07-17T20:20:00Z", "2015-07-15T20:20:00Z" });
        //data.add(new Object[]{ "20 20 11-12,20-21 * *",   "2015-08-10T21:47:27Z", "2015-07-21T20:20:00Z", "2015-07-12T20:20:00Z" });
        //data.add(new Object[]{ "20 20 11-12,16/3,14 * *", "2015-08-10T21:47:27Z", "2015-07-31T20:20:00Z", "2015-07-25T20:20:00Z" });

        // MONTHS
        //data.add(new Object[]{ "20 20 9 10 *",         "2015-08-10T21:47:27Z", "2014-10-09T20:20:00Z", "2012-10-09T20:20:00Z" });
        data.add(new Object[]{ "20 20 9 5,7,9 *",      "2015-08-10T21:47:27Z", "2015-07-09T20:20:00Z", "2014-09-09T20:20:00Z" });
        data.add(new Object[]{ "20 20 9 */2 *",        "2015-08-10T21:47:27Z", "2015-08-09T20:20:00Z", "2015-04-09T20:20:00Z" });
        data.add(new Object[]{ "20 20 9 */5 *",        "2015-08-10T21:47:27Z", "2015-05-09T20:20:00Z", "2014-05-09T20:20:00Z" });
        //data.add(new Object[]{ "20 20 9 2/3 *",        "2015-08-10T21:47:27Z", "2015-08-09T20:20:00Z", "2015-02-09T20:20:00Z" });
        //data.add(new Object[]{ "20 20 9 5/2,2/4 *",    "2015-08-10T21:47:27Z", "2015-07-09T20:20:00Z", "2015-05-09T20:20:00Z" });
        //data.add(new Object[]{ "20 20 9 2/4,5/2 *",    "2015-08-10T21:47:27Z", "2015-07-09T20:20:00Z", "2015-05-09T20:20:00Z" });
        data.add(new Object[]{ "20 20 9 3-7 *",        "2015-08-10T21:47:27Z", "2015-07-09T20:20:00Z", "2015-05-09T20:20:00Z" });
        data.add(new Object[]{ "20 20 9 2-3,8-11 *",   "2015-08-10T21:47:27Z", "2015-08-09T20:20:00Z", "2015-02-09T20:20:00Z" });
        //data.add(new Object[]{ "20 20 9 1-2,9/2,12 *", "2015-08-10T21:47:27Z", "2015-02-09T20:20:00Z", "2014-12-09T20:20:00Z" });

        // DAYS OF THE WEEK
        data.add(new Object[]{ "20 20 * * 1",               "2015-04-23T21:47:27Z", "2015-04-20T20:20:00Z", "2015-04-06T20:20:00Z" });
        data.add(new Object[]{ "20 20 * * 2,4,7",           "2015-04-23T21:47:27Z", "2015-04-23T20:20:00Z", "2015-04-19T20:20:00Z" });
        data.add(new Object[]{ "20 20 * * 2,4,0",           "2015-04-23T21:47:27Z", "2015-04-23T20:20:00Z", "2015-04-19T20:20:00Z" });
        data.add(new Object[]{ "20 20 * * 0,4,2",           "2015-04-23T21:47:27Z", "2015-04-23T20:20:00Z", "2015-04-19T20:20:00Z" });
        data.add(new Object[]{ "20 20 * * SUN,4,TUE",       "2015-04-23T21:47:27Z", "2015-04-23T20:20:00Z", "2015-04-19T20:20:00Z" });
        data.add(new Object[]{ "20 20 * * THU,0,TUE",       "2015-04-23T21:47:27Z", "2015-04-23T20:20:00Z", "2015-04-19T20:20:00Z" });
        //data.add(new Object[]{ "20 20 * * */2",             "2015-04-23T21:47:27Z", "2015-04-23T20:20:00Z", "2015-04-19T20:20:00Z" });
        //data.add(new Object[]{ "20 20 * * */2",             "2015-04-22T21:47:27Z", "2015-04-21T20:20:00Z", "2015-04-18T20:20:00Z" });
        data.add(new Object[]{ "20 20 * * 2/3",             "2015-04-23T21:47:27Z", "2015-04-21T20:20:00Z", "2015-04-14T20:20:00Z" });
        data.add(new Object[]{ "20 20 * * 1/2,3/3",         "2015-04-23T21:47:27Z", "2015-04-22T20:20:00Z", "2015-04-18T20:20:00Z" });
        data.add(new Object[]{ "20 20 * * 2-3",             "2015-04-23T21:47:27Z", "2015-04-22T20:20:00Z", "2015-04-15T20:20:00Z" });
        data.add(new Object[]{ "20 20 * * 2-3,5-6",         "2015-04-23T21:47:27Z", "2015-04-22T20:20:00Z", "2015-04-18T20:20:00Z" });
        //data.add(new Object[]{ "20 20 * * 1-2,5/2,6",       "2015-04-23T21:47:27Z", "2015-04-21T20:20:00Z", "2015-04-18T20:20:00Z" });

        //data.add(new Object[]{ "20 20 * * 0-6",             "2015-04-23T21:47:27Z", "2015-04-23T20:20:00Z", "2015-04-21T20:20:00" });
        //data.add(new Object[]{ "20 20 * * 7-6",             "2015-04-23T21:47:27Z", "2015-04-23T20:20:00Z", "2015-04-21T20:20:00" });
        //data.add(new Object[]{ "20 20 * * SUN-SAT",         "2015-04-23T21:47:27Z", "2015-04-23T20:20:00Z", "2015-04-21T20:20:00" });
        //data.add(new Object[]{ "20 20 * * 6-7",             "2015-04-23T21:47:27Z", "2015-04-19T20:20:00Z", "2015-04-12T20:20:00" });
        data.add(new Object[]{ "20 20 * * TUE-FRI",         "2015-04-23T21:47:27Z", "2015-04-23T20:20:00Z", "2015-04-21T20:20:00" });
        data.add(new Object[]{ "20 20 * * THU-FRI,TUE-WED", "2015-04-23T21:47:27Z", "2015-04-23T20:20:00Z", "2015-04-21T20:20:00" });

         //	DAYS OF THE WEEK OF MONTH
        data.add(new Object[]{ "20 20 * * 1#2",            "2015-04-23T21:47:27Z", "2015-04-13T20:20:00Z", "2015-02-09T20:20:00Z" });
        data.add(new Object[]{ "20 20 * * 2#1,4L,7#2",     "2015-04-23T21:47:27Z", "2015-04-12T20:20:00Z", "2015-03-26T20:20:00Z" });
        data.add(new Object[]{ "20 20 * * 2#3,4#2,0#1",    "2015-04-23T21:47:27Z", "2015-04-21T20:20:00Z", "2015-04-05T20:20:00Z" });
        //data.add(new Object[]{ "20 20 * * SUNL,4#2,TUE#2", "2015-04-23T21:47:27Z", "2015-04-14T20:20:00Z", "2015-03-29T20:20:00Z" });
        //data.add(new Object[]{ "20 20 * * SUNL",           "2015-04-23T21:47:27Z", "2015-03-29T20:20:00Z", "2015-01-25T20:20:00Z" });

        data.add(new Object[]{ "0 10 * * 6L,0L",    "2015-11-28T00:01:00.000Z", "2015-10-31T10:00:00.000Z", "2015-09-27T10:00:00.000Z" });
        data.add(new Object[]{ "0 10 * * 1",        "2015-11-02T00:01:00.000Z", "2015-10-26T10:00:00.000Z", "2015-10-12T10:00:00.000+00:00" });
        data.add(new Object[]{ "0 10 * * 2#2,3#2",  "2015-11-10T00:01:00.000Z", "2015-10-14T10:00:00.000+01:00", "2015-10-13T10:00:00.000+01:00" });

        return data;
    }

    public ExecutionTimeExtraCustomDefinitionIntegrationTest(String cronExpression, String localDate, String previousDate1, String previousDate3) {
        this.cronExpression = cronExpression;

        this.localDate = DateTime.parse(localDate);
        this.expectedPreviousDate1 = DateTime.parse(previousDate1);
        this.expectedPreviousDate3 = DateTime.parse(previousDate3);
    }

    @Test
    public void testCronExpression() {
        Cron cron = parser.parse(this.cronExpression);

        DateTime previousExecution1 = ExecutionTime.forCron(cron).lastExecution(localDate);

        //DateTime previousExecution2 = ExecutionTime.forCron(cron).lastExecution(previousExecution1.minusSeconds(1));
        //DateTime previousExecution3 = ExecutionTime.forCron(cron).lastExecution(previousExecution2.minusSeconds(1));

        assertTrue(expectedPreviousDate1.compareTo(previousExecution1) == 0);
        //assertTrue(expectedPreviousDate3.compareTo(previousExecution3) == 0);
    }
}
