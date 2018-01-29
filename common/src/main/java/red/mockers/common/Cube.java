package red.mockers.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Cube {
    private final static String DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss";
    
    private String reportName;
    private String[] columnNames = {"pair", "bid", "ask", "time:PK"};
    private String[] columnTypes = {"string", "double", "double", "string"};
    private String[][] rows;
    
    public Cube(){
        
    }
    
    public Cube(String reportName, String pair, double bid, double ask, Date date){
        this.reportName = reportName;
        
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        
        this.rows = new String[][] {new String[]{
            "pair=" + pair,
            "bid=" + Double.toString(bid),
            "ask=" + Double.toString(ask),
            "time=" + df.format(date)
        }};
    }

    public Cube(String reportName, Rate rate){
        this(reportName, rate.getPair(), rate.getBid(), rate.getAsk(), rate.getDate());
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
