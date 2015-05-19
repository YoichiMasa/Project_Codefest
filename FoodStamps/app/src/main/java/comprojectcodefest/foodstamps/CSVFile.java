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

    public Hashtable<String, PairSet> read(){
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
                    int healthy = 0;
                    double temp = Math.random();
                    if ((0.0 < temp) && (temp < 0.5 )) {
                        healthy = 1;
                    }
                    else if ((0.5 < temp) && (temp < 0.8)) {
                        healthy = 2;
                    }
                    else if ((0.8 < temp) && (temp < 1.0)) {
                        healthy = 3;
                    }
                    PairSet current = new PairSet(store, healthy);
                    storesAL.put(row[0], current);
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

