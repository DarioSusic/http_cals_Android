package com.example.http_calls;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    TextView container;
    EditText inputId;
    EditText title;
    EditText text;
    EditText userId;
    Retrofit retrofit;
    RestApi restApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = (TextView) findViewById(R.id.get_container);
        inputId = (EditText) findViewById(R.id.id);

        title = (EditText) findViewById(R.id.input_title);
        text = (EditText) findViewById(R.id.input_text);
        userId = (EditText) findViewById(R.id.input_id);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        restApi = retrofit.create(RestApi.class);

    }
    public void onGetClick(View view){
        String input = inputId.getText().toString();
        if (input.isEmpty()){
            getAllPosts();
        } else {
            getSinglePost(Integer.parseInt(input));
        }

    }

    private void getSinglePost(int parseInt) {
        Call<Post> call = restApi.getPostById(parseInt);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()){
                    Post post = response.body();
                    String postContent = "";
                    postContent += "ID is " + post.getId() + "\n";
                    postContent += "User ID is " + post.getUserId() + "\n";
                    postContent += "Title is " + post.getTitle() + "\n";
                    postContent += "Text is " + post.getText() + "\n";

                    container.append(postContent);
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getAllPosts() {
        Call<List<Post>> call = restApi.getAllPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()){
                    List<Post> posts = response.body();
                    int i = 0;
                    container.setText("");
                    for (Post post: posts){
                        if (i < 2){
                            String postContent = "";
                            postContent += "ID is " + post.getId() + "\n";
                            postContent += "User ID is " + post.getUserId() + "\n";
                            postContent += "Title is " + post.getTitle() + "\n";
                            postContent += "Text is " + post.getText() + "\n";

                            container.append(postContent);
                        }
                        i++;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void onPostClick(View view){
        Post post = new Post(Integer.parseInt(userId.getText().toString()),
                                            title.getText().toString(),
                                            text.getText().toString());
        Call<Post> postCall = restApi.addNewPost(post);
        postCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()){
                    Post post = response.body();
                    String postContent = "";
                    postContent += "ID is " + post.getId() + "\n";
                    postContent += "User ID is " + post.getUserId() + "\n";
                    postContent += "Title is " + post.getTitle() + "\n";
                    postContent += "Text is " + post.getText() + "\n";

                    container.append(postContent);
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}
