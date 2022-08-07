package sale.ljw.clinic_administrator_background_system.utils.timeUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimeFormatSeparate {
    public static List<String> getIntervalTimeList(String start,String end,int interval){
        Date startDate = DateUtils.convertString2Date("HH:mm",start);
        Date endDate = DateUtils.convertString2Date("HH:mm",end);
        List<String> list = new ArrayList<>();
        while(startDate.getTime()<=endDate.getTime()){
            list.add(DateUtils.convertDate2String("HH:mm",startDate));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.MINUTE,interval);
            if(calendar.getTime().getTime()>endDate.getTime()){
                if(!startDate.equals(endDate)){
                    list.add(DateUtils.convertDate2String("HH:mm",endDate));
                }
                startDate = calendar.getTime();
            }else{
                startDate = calendar.getTime();
            }
        }
        return list;
    }

}
