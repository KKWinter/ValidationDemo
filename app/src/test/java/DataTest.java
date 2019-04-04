import org.junit.Test;

import java.sql.Time;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class DataTest {



    @Test
    public void testTime() {


        //获取Date
        Date date = new Date();
        long time = date.getTime();

        System.out.println(time);
        System.out.println(System.currentTimeMillis());

        //把Date格式化为字符串
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = sdf.format(date);
        System.out.println(timeStr);

        //把字符串转化为Date时间
        String timeStr2 = "12:09:55";
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date date2 = sdf2.parse(timeStr, pos);
        System.out.println(date2.getTime());
    }



}
