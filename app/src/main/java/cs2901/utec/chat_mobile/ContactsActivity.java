package cs2901.utec.chat_mobile;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ContactsActivity extends AppCompatActivity {
    private ArrayList<String>users =new ArrayList<>() ;


    public Activity getActivity(){
        return this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_contacts);
        //----------------------------------------//
        /////RECIBIR DATOS //////////
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                "http://10.0.2.2:8080/users",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //TODO
                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject user = response.getJSONObject(i);

                                // Get the current student (json object) data
                                String usuario = user.getString("username");
                                users.add(usuario);
                            }
                            RecyclerView recyclerView = findViewById(R.id.Lista);

                            // use this setting to improve performance if you know that changes
                            // in content do not change the layout size of the RecyclerView
                            recyclerView.setHasFixedSize(true);


                            // use a linear layout manager
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                            MyAdapter mAdapter = new MyAdapter(users);
                            recyclerView.setAdapter(mAdapter);


                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
        /////RECIBIR DATOS //////////
        //----------------------------------------//

        Intent nombre = getIntent();
        TextView contactsActivityTitle = findViewById(R.id.contactsActivityTitle);
        String username = nombre.getStringExtra("username");
        String title = username + "'s contacts";
        contactsActivityTitle.setText(title);
        /////RECICLER VIEW///////////



        /////RECICLER VIEW///////////
    }
}


