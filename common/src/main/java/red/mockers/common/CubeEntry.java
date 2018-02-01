package red.mockers.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CubeEntry {
    private final static String DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss";
    
    private String reportName;
    private String[] columnNames = {"id:PK", "currency:PK", "price", "date", "time"};
    private String[] columnTypes = {"long", "string", "double", "datetime", "time"};
    private String[][] rows;
    
    public CubeEntry(){
        
    }
    
    public CubeEntry(String reportName, long id, String currency, double price, Date date){
        this.reportName = reportName;
        
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        
        String dateTime = df.format(date);
        String time = dateTime.substring(11);
        this.rows = new String[][] {new String[]{
            "id=" + Long.toString(id),
            "currency=" + currency,
            "price=" + Double.toString(price),
            "date=" + dateTime,
            "time=" + time
        }};
    }

    public CubeEntry(String reportName, long id, Rate rate){
        this(reportName, id, rate.getPair(), rate.getAsk(), rate.getDate());
    }
    
    @Override
    public String toString() {
        String json = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(this);    
        } catch (JsonProcessingException exception) {
            exception.printStackTrace();
        }
        return json;
    }
    
    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public String[] getColumnTypes() {
        return columnTypes;
    }

    public void setColumnTypes(String[] columnTypes) {
        this.columnTypes = columnTypes;
    }

    public String[][] getRows() {
        return rows;
    }

    public void setRows(String[][] rows) {
        this.rows = rows;
    }
}
