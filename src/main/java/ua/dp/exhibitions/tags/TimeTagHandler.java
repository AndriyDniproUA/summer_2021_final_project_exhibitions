package ua.dp.exhibitions.tags;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * TimeTagHandler is a custom tag
 * which outputs current date and time
 */
public class TimeTagHandler extends SimpleTagSupport {
    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out=getJspContext().getOut();
        Date dateTime=Calendar.getInstance().getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        out.print(dateFormat.format(dateTime));
    }
}
