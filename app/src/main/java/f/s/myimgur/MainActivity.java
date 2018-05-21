package f.s.myimgur;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private OkHttpClient httpClient;
    final private List<Photo> photos = new ArrayList<Photo>();
    private Button getBt;
    private View.OnClickListener getListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            fetchData();
        }
    };
    private Adapter mAdapter;

    public List<Photo> getPhotos() {
        return photos;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getBt = (Button) findViewById(R.id.sendMsg);
        getBt.setOnClickListener(getListener);
    }

    private void fetchData(){
        httpClient = new OkHttpClient.Builder().build();
        getImages();
    }

    private void getImages(){
        Request request = new Request.Builder()
                            .url("https://api.imgur.com/3/album/3SSLIHZ/images")
                            .header("Authorization","Client-ID b32fc763dc94d57")
                            .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Failure error: ", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{
                    JSONObject data = new JSONObject(response.body().string());
                    JSONArray items = data.getJSONArray("data");
                    for (int i = 0; i < items.length(); i++){
                        JSONObject item = items.getJSONObject(i);
                        Photo photo = new Photo(item.getString("id"));
                        photos.add(photo);
                    }
                    runOnUiThread(new Runnable() { // verify this operation runs on the UI
                        @Override
                        public void run() {
                            render();
                        }
                    });
                } catch (Exception e){
                    Log.e("Response error: ", e.getMessage());
                }
            }
        });
    }

    private void render(){
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        mAdapter = new Adapter(this);
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(mAdapter);
    }

}
