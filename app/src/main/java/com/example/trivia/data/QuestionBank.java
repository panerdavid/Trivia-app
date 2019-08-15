package com.example.trivia.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.trivia.controller.AppController;
import com.example.trivia.data.AnswerListAsyncResponse;
import com.example.trivia.model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuestionBank {
    private String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
    ArrayList<Question> questionArrayList = new ArrayList<>();
    private Object JsonArrayRequest;

    public List<Question> getQuestions(final AnswerListAsyncResponse callBack) {
        JsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                Question question = new Question(response.getJSONArray(i).get(0).toString(), response.getJSONArray(i).getBoolean(1));
                                questionArrayList.add(question);
//                                question.setAnswer(response.getJSONArray(i).get(0).toString());
                                Log.d("outside", "FUCKkkkkkkk");
                                Log.d("JSON: ", "onResponse: " + question);
//                                Log.d("JSON2: " , "onResponse: " + response.getJSONArray(i).getBoolean(1));


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (callBack != null) {
                            callBack.processFinish(questionArrayList);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override

                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", Objects.requireNonNull(error.getMessage()));
                    }
                });
        AppController.getInstance().addToRequestQueue((Request<Object>) JsonArrayRequest);
        return questionArrayList;


    }
}