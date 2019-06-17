package com.example.mirutapp.Fragment;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mirutapp.MiRutAppApplication;
import com.example.mirutapp.Model.DataParser;
import com.example.mirutapp.Model.Incident;
import com.example.mirutapp.Model.Route;
import com.example.mirutapp.R;

import com.example.mirutapp.Repository.RouteRepository;
import com.example.mirutapp.ViewModel.RouteViewModel;
import com.example.mirutapp.ViewModel.RouteViewModelFactory;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Attributes to RouteDialog Fragment. (add route)
    private FloatingActionButton addRouteButton;

    private GoogleMap mMap;
    ArrayList<LatLng> MarkerPoints;

    ArrayList<Incident> MarkerPointsUOCT = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    private Context mContext;

    boolean doubleBackToExitPressedOnce = false;

    @Inject
    RouteViewModelFactory routeViewModelFactory;
    private RouteViewModel viewModel;

    public MapsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapsFragment newInstance(String param1, String param2) {
        MapsFragment fragment = new MapsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        ((MiRutAppApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        View v =  inflater.inflate(R.layout.fragment_maps, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment)this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //initialize viewModel
        viewModel = ViewModelProviders.of(this, routeViewModelFactory).get(RouteViewModel.class);

        //button to open Route Dialog
        addRouteButton = v.findViewById(R.id.addRouteButton);
        addRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddRouteDialog dialog = new AddRouteDialog();
                dialog.setTargetFragment(MapsFragment.this, 1);
                dialog.show(MapsFragment.this.getFragmentManager(), "AddRouteDialog");
            }
        });

        MarkerPoints = new ArrayList<>();
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-33.4495857, -70.6823836);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marcador en tu posición").icon(BitmapDescriptorFactory.fromResource(R.drawable.iconuser)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        GetUOCT getUOCT = new GetUOCT();
        getUOCT.execute();

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                // Already two locations
                if (MarkerPoints.size() > 1) {
                    MarkerPoints.clear();
                }

                // Adding new item to the ArrayList
                MarkerPoints.add(point);

                // Creating MarkerOptions
                MarkerOptions options = new MarkerOptions();

                // Setting the position of the marker
                options.position(point);

                /**
                 * For the start location, the color of marker is GREEN and
                 * for the end location, the color of marker is RED.
                 */
                if (MarkerPoints.size() == 1) {
                    options.title("Punto de Inicio");
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                } else if (MarkerPoints.size() == 2) {
                    options.title("Punto Final");
//                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    options.icon(BitmapDescriptorFactory.fromResource(R.drawable.finish2));
                }

                // Add new marker to the Google Map Android API V2
                mMap.addMarker(options);

                // Checks, whether start and end locations are captured
                if (MarkerPoints.size() >= 2) {
                    LatLng origin = MarkerPoints.get(0);
                    LatLng dest = MarkerPoints.get(1);

                    // Getting URL to the Google Directions API
                    String url = getUrl(origin, dest);
                    Log.d("onMapClick", url.toString());
                    FetchUrl FetchUrl = new FetchUrl();

                    // Start downloading json data from Google Directions API
                    FetchUrl.execute(url);
                    //move map camera
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                }

            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker){
                String urlString = marker.getSnippet();
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(urlString));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try {
                    mContext.startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    // Chrome browser presumably not installed so allow user to choose instead
                    intent.setPackage(null);
                    mContext.startActivity(intent);
                }
            }

        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });
    }

    private String getUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters +"&key=AIzaSyCd9EduZIayU6ESWl8xB13Cily5Ju2y3hA";
        return url;
    }

    // Fetches data from url passed
    private class FetchUrl extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask",jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask","Executing routes");
                Log.d("ParserTask",routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask",e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;
            LinkedHashSet<Incident> hashSet = new LinkedHashSet<Incident>();

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    System.out.println("largo UOCT:"+MarkerPointsUOCT.size());

                    for (int k = 0; k < MarkerPointsUOCT.size(); k++) {
                        float distance = MarkerPointsUOCT.get(k).distanceInMeters(position);

                        System.out.println("Comparando:"+MarkerPointsUOCT.get(k).getLocation());
                        System.out.println("Comparando:"+position.toString());

                        if (distance < 300){
                            System.out.println("es menor a 100");
                            System.out.println("PUNTO QUE INFLUYE LA RUTA"+MarkerPointsUOCT.get(k).getDescription());

                            hashSet.add(MarkerPointsUOCT.get(k));
                        }
                    }

                    points.add(position);
                }


                // custom dialog
                final Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.custom);
                dialog.setTitle("Title...");

                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                final TimePicker timePicker = (TimePicker) dialog.findViewById(R.id.timePicker);
                final CheckBox cbMonday = (CheckBox) dialog.findViewById(R.id.checkBox);
                final CheckBox cbTuesday = (CheckBox) dialog.findViewById(R.id.checkBox2);
                final CheckBox cbWednesday = (CheckBox) dialog.findViewById(R.id.checkBox3);
                final CheckBox cbThursday = (CheckBox) dialog.findViewById(R.id.checkBox5);
                final CheckBox cbFriday = (CheckBox) dialog.findViewById(R.id.checkBox6);
                final CheckBox cbSaturday = (CheckBox) dialog.findViewById(R.id.checkBox7);
                final CheckBox cbSunday = (CheckBox) dialog.findViewById(R.id.checkBox8);
                final TextInputEditText textInput = (TextInputEditText) dialog.findViewById(R.id.textInput);
                timePicker.setIs24HourView(true); // to set 24 hours mode
                timePicker.setIs24HourView(false); // to set 12 hours mode

                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Set<Integer> days = new HashSet<Integer>();
                        String routeName = textInput.getText().toString();
                        if(!routeName.equals("")){
                            if(!cbMonday.isChecked() && !cbTuesday.isChecked() && !cbWednesday.isChecked() && !cbThursday.isChecked() && !cbFriday.isChecked() && !cbSaturday.isChecked() && !cbSunday.isChecked())
                                Toast.makeText(getContext(), "Debe seleccionar al menos un día.", Toast.LENGTH_LONG).show();
                            else{
                                if(cbMonday.isChecked()){
                                    days.add(1);
                                }
                                if(cbTuesday.isChecked()){
                                    days.add(2);
                                }
                                if(cbWednesday.isChecked()){
                                    days.add(3);
                                }
                                if(cbThursday.isChecked()){
                                    days.add(4);
                                }
                                if(cbFriday.isChecked()){
                                    days.add(5);
                                }
                                if(cbSaturday.isChecked()){
                                    days.add(6);
                                }
                                if(cbSunday.isChecked()){
                                    days.add(0);
                                }
                            }
                        int alarmHour = timePicker.getHour();
                        int alarmMinute = timePicker.getMinute();
                        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=-33.452367254631895,-70.6842477992177&destination=-33.45019899569667,-70.67649889737368&sensor=false&key=AIzaSyCd9EduZIayU6ESWl8xB13Cily5Ju2y3hA";

                        viewModel.saveRoute(url, routeName, alarmHour, alarmMinute, days);
                        dialog.dismiss();
                        }else{
                            Toast.makeText(getContext(), "¡Nombre de ruta vacía!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                dialog.show();

                Toast toast1 =
                        Toast.makeText(mContext,
                                "Hay "+hashSet.size()+" Incidentes que afectan tu ruta", Toast.LENGTH_SHORT);

                toast1.show();

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.BLUE);

                Log.d("onPostExecute","onPostExecute lineoptions decoded");

            }

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                mMap.addPolyline(lineOptions);
            }
            else {
                Log.d("onPostExecute","without Polylines drawn");
            }
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private class GetUOCT extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(Void... params)
        {

//            String str="http://www.uoct.cl/historial/ultimos-eventos/json-waze/";
            String str="https://api.myjson.com/bins/o16ox";

            System.out.printf(str);

            URLConnection urlConn = null;
            BufferedReader bufferedReader = null;
            try
            {
                URL url = new URL(str);
                urlConn = url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    stringBuffer.append(line);
                }

                return new JSONObject(stringBuffer.toString());
            }
            catch(Exception ex)
            {
                Log.e("App", "yourDataTask", ex);
                return null;
            }
            finally
            {
                if(bufferedReader != null)
                {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(JSONObject response)
        {
            if(response != null)
            {
                try {
                    JSONArray incidents = response.getJSONArray("incidents");
                    Gson gson=new Gson();
                    Incident[] incidentsObjects = new Incident[incidents.length()];

                    for (int i = 0; i < incidents.length(); i++) {
                        Incident incident =gson.fromJson(incidents.get(i).toString(),Incident.class);
                        incidentsObjects[i] = incident;

                        MarkerPointsUOCT.add(incident);
                    }

                    for (int i = 0; i < incidentsObjects.length ; i++) {
                        Log.e("IncidentsObjects"+i, incidentsObjects[i].toString());
                        Double lat = Double.parseDouble( incidentsObjects[i].getLocation().getPolyline()[0][0]);
                        Double longitude = Double.parseDouble( incidentsObjects[i].getLocation().getPolyline()[0][1]);

                        LatLng point = new LatLng(lat,longitude);

                        String title = incidentsObjects[i].getDescription();

                        MarkerPoints.add(point);

                        // Creating MarkerOptions
                        MarkerOptions options = new MarkerOptions();

                        // Setting the position of the marker
                        options.position(point);
                        options.title(title);
                        options.snippet(incidentsObjects[i].getLink());

                        /**
                         * For the start location, the color of marker is GREEN and
                         * for the end location, the color of marker is RED.
                         */

                        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.shieldwarning));

                        Log.e("Marker","options" +options);
                        // Add new marker to the Google Map Android API V2
                        mMap.addMarker(options);
                    }

                    Log.e("Incidents",incidents.toString());
                    Log.e("App", "Success: " + response.getString("incidents") );

                } catch (JSONException ex) {
                    Log.e("App", "Failure", ex);
                }
            }
        }
    }
}
