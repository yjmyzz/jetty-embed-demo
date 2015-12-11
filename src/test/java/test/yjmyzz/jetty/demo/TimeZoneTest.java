package test.yjmyzz.jetty.demo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * {type your description }
 *
 * @since: 12/11/15.
 * @author: yangjunming
 */
public class TimeZoneTest {

    public static void main(String[] args) {
        System.out.println(TimeZone.getDefault().getID());

        SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");

        Calendar tzCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));

        System.out.println(sdt.format(tzCal.getTime()));
    }
}
