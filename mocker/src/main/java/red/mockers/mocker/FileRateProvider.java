package red.mockers.mocker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import red.mockers.common.Rate;

public class FileRateProvider implements RateProvider{

    private BufferedReader reader;
    
    @Override
    public Rate getNextRate() throws IOException  {
        String line = reader.readLine();
        
        if(line == null)
            return null;
        
        String[] fields = line.split(";");       
        return new Rate(new Date(), fields[2], Double.parseDouble(fields[1]), Double.parseDouble(fields[1]));
    }
        
    public FileRateProvider(String filename) throws IOException {
        reader = new BufferedReader(new FileReader(filename));
    }
    
    public FileRateProvider(InputStream stream) throws IOException {
        reader = new BufferedReader(new InputStreamReader(stream));
    }
}
