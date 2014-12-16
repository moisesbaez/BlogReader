package com.appacademy.blogreader;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BlogPostParser {
    private static BlogPostParser parser;
    public ArrayList<BlogPost> posts;

    private BlogPostParser() {
        posts = new ArrayList<BlogPost>();
    }

    public static BlogPostParser get() {
        if(parser == null) {
            parser = new BlogPostParser();
        }
        return parser;
    }

    public JSONObject parse(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        String line;
        JSONObject jsonObject = null;

        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            JSONTokener tokener = new JSONTokener(builder.toString());
            jsonObject = new JSONObject(tokener);
        }
        catch (IOException error) {
            Log.e("Blog Post Parser", "IOException: " + error);
        }
        catch (JSONException error) {
            Log.e("Blog Post Parser", "JSONException: " + error);
        }

        return jsonObject;
    }

    public void readFeed(JSONObject jsonObject) {
        try {
            JSONArray jsonPosts = jsonObject.getJSONArray("posts");

            for(int i = 0; i < jsonPosts.length(); i++) {
                JSONObject post = jsonPosts.getJSONObject(i);

                String title = post.getString("title");
                String url = post.getString("url");

                BlogPost blogPost = new BlogPost(title, url);
                posts.add(blogPost);
            }

        }
        catch (JSONException error) {
            Log.e("Blog Post Parser", "JSONException: " + error);
        }
//        Log.i("Blog Post Parser", ": " + jsonObject.toString());
    }

}
