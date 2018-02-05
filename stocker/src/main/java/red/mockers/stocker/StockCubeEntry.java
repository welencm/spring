package red.mockers.stocker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class StockCubeEntry {
    private final static String TIME_FORMAT = "HH:mm:ss";
    private final static SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
    private static final ObjectMapper mapper = new ObjectMapper();
    
    private String reportName;
    private String[] columnNames = {"id:PK", "symbol:PK", "companyName", "latestPrice", "latestTime"};
    private String[] columnTypes = {"long", "string", "string", "double", "time"};
    private String[][] rows;
    
    public StockCubeEntry() {
        
    }
    
    public StockCubeEntry(String reportName, long id, Quote quote) {        
        this.reportName = reportName;
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+1"));        
        String time = sdf.format(quote.getLatestTime());        
        this.rows = new String[][] {new String[]{
            "id=" + Long.toString(id),
            "symbol=" + quote.getSymbol(),
            "companyName=" + quote.getCompanyName(),
            "latestPrice=" + Double.toString(quote.getLatestPrice()),
            "latestTime=" + time
        }};
    }
    
    @Override
    public String toString() {
        String json = null;
        try {            
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
