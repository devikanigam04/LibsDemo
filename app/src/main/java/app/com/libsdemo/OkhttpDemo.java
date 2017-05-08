package app.com.libsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class OkhttpDemo extends AppCompatActivity {

    // should be singletom
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp_demo);
        ButterKnife.bind(this);

        // without params
        final Request request1 = new Request.Builder()
                .url("http://www.vogella.com/index.html")
                .build();

        // with params
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.github.help").newBuilder();
        urlBuilder.addQueryParameter("v", "1.0");
        urlBuilder.addQueryParameter("user", "vogella");
        String url = urlBuilder.build().toString();

        Request request2 = new Request.Builder()
//                .header("Authorization", "your token") // authenticcation header
                .url(url)
                .build();

        //to make synchronous network call
//        try {
//            Response response1 = client.newCall(request1).execute();
//            // or
//            Response response2 = client.newCall(request2).execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // to make asynchronous network call
        client.newCall(request1/*or request2*/).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    // do something wih the result
                    Log.d("Response : ", response.toString());
                }
            }
        });
    }

    @OnClick(R.id.button)
    void volleyLink(){
        Intent intent = new Intent(this, VolleyDemo.class);
        startActivity(intent);
    }
}
