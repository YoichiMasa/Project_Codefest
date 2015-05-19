package comprojectcodefest.foodstamps;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

/**
 * Created by Laurence on 2/22/2015.
 */
public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////        Toast msg = Toast.makeText(this, "clicked", Toast.LENGTH_LONG);
////        msg.show();
//        if(parent.getItemAtPosition(position).equals("Healthy"))//if select healthy
//        {
//            for(int i = 0; i < markerList.size(); i++) {//make non-healthy invisible
//                if (!markerList.get(i).getSnippet().equals("Healthy")) {
//                    markerList.get(i).setVisible(false);
//                }
//            }
//        }
//        if(parent.getItemAtPosition(position).equals("Average"))
//        {
//            for(int i = 0; i < markerList.size(); i++) {
//                if (!markerList.get(i).getSnippet().equals("Average")){
//                    markerList.get(i).setVisible(false);
//                }
//            }
//        };
//        if(parent.getItemAtPosition(position).equals("Unhealthy"))
//        {
//            for(int i = 0; i < markerList.size(); i++) {
//                if (!markerList.get(i).getSnippet().equals("Unhealthy")) {
//                    markerList.get(i).setVisible(false);
//                }
//            }
//
//        }
//        if(parent.getItemAtPosition(position).equals("All"))
//        {
//            for(int i = 0; i < markerList.size(); i++) {
//                markerList.get(i).setVisible(true);
//            }
//        }
//

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
