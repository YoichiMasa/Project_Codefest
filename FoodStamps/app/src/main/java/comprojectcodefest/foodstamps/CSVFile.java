package comprojectcodefest.foodstamps;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
/**
 * Created by Laurence on 2/21/2015.
 */
public class CSVFile {
    InputStream inputStream;

    public CSVFile(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public Hashtable read(){
        Hashtable storesAL = new Hashtable();
        LatLng store = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            reader.readLine();
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                if(row[9].equalsIgnoreCase("PHILADELPHIA ")) {
                    double lng = Double.parseDouble(row[1]);
                    double lat = Double.parseDouble(row[2]);
                    store = new LatLng(lat, lng);
                    storesAL.put(row[0], store);
                }

            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }
        return storesAL;
    }
}
