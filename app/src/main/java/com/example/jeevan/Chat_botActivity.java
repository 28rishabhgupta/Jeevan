package com.example.jeevan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.jeevan.Adapter.CustomAdapter;
import com.example.jeevan.HelperChat.HttpDataHandler;
import com.example.jeevan.Models.ChatModel;
import com.example.jeevan.Models.SimsimiModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Chat_botActivity extends AppCompatActivity {

    ListView listView;
    EditText editText;
    List<ChatModel> list_chat = new ArrayList<>();
    FloatingActionButton btn_send_message;
    private Object ChatModel;
    private com.example.jeevan.Models.ChatModel model;
    private List<com.example.jeevan.Models.ChatModel>[] params;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);

        listView = (ListView)findViewById(R.id.list_of_message);
        editText = findViewById(R.id.user_message);
        btn_send_message = findViewById(R.id.fab);

        btn_send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                ChatModel = new ChatModel(text,true);
                list_chat.add(model);
                new SimsimiAPI().execute(list_chat);
                editText.setText("'");


            }
        });
    }

    private class SimsimiAPI extends AsyncTask<List<ChatModel>,Void,String> {

        String streams = null;
        List<ChatModel> models;
        String text = editText.getText().toString();

        @Override
        protected String doInBackground(List<ChatModel>... lists) {
          String url = String.format("http://sandbox.api.simsimi.com/request.p?key=%s&lc=en&ft1.0&text=%s",getString(R.string.simsimi_api),text);
          models = params[0];
            HttpDataHandler httpDataHandler = new HttpDataHandler();
            String stream = httpDataHandler.GetHTTpData(url);
            return stream;
        }

        @Override
        protected void onPreExecute() {
            Gson gson = new Gson();
           String s = "";
            SimsimiModel response = gson.fromJson( s ,SimsimiModel.class);

           try{
               ChatModel chatModel = new ChatModel(response.getResponse(),false);
               models.add(chatModel);
           }catch (NullPointerException ignored){

           }
            CustomAdapter adapter = new CustomAdapter(models,getApplicationContext());
            listView.setAdapter(adapter);


        }
    }
}
