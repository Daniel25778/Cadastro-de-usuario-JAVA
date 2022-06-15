package helpers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormat {


    private String dateFormat = "dd-MM-yyyy HH:mm:ss";
    private String locale = "pt-BR";
    private SimpleDateFormat simpleDateFormat = null;

    public DateFormat(){
        this.simpleDateFormat = new SimpleDateFormat(this.dateFormat, new Locale(this.locale));
    }


    public String getDateFormat(){

        return  this.simpleDateFormat.format(new Date());
    }



}
