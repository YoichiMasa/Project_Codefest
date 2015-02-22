package comprojectcodefest.foodstamps;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Laurence on 2/22/2015.
 */
public class PairSet
{
    LatLng location;
    int healthy;
    String desc;

    public PairSet(LatLng location, int healthy)
    {
        this.location = location;
        this.healthy = healthy;
    }

    public LatLng returnLoc(){
        return location;
    }

    public int returnHealthy(){
        return healthy;
    }

}
