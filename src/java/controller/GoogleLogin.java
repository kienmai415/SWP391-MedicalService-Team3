///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package controller;
//
//import com.google.gson.Gson;
//import java.io.IOException;
//import org.apache.http.client.ClientProtocolException;
//import Iconstrant.Iconstrant;
//import com.google.gson.JsonObject;
//import model.GoogleAccount;
//import org.apache.http.client.fluent.Request;
//import org.apache.http.client.fluent.Form;
//
///**
// *
// * @author BB-MT
// */
//public class GoogleLogin {
//
//    public static String getToken(String code) throws ClientProtocolException, IOException {
//
//        String response = Request.Post(Iconstrant.GOOGLE_LINK_GET_TOKEN)
//                .bodyForm(
//                        Form.form()
//                                .add("client_id", Iconstrant.GOOGLE_CLIENT_ID)
//                                .add("client_secret", Iconstrant.GOOGLE_CLIENT_SECRET)
//                                .add("redirect_uri", Iconstrant.GOOGLE_REDIRECT_URI)
//                                .add("code", code)
//                                .add("grant_type", Iconstrant.GOOGLE_GRANT_TYPE)
//                                .build()
//                )
//                .execute().returnContent().asString();
//
//        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
//
//        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
//
//        return accessToken;
//
//    }
//
//    public static GoogleAccount getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
//
//        String link = Iconstrant.GOOGLE_LINK_GET_USER_INFO + accessToken;
//
//        String response = Request.Get(link).execute().returnContent().asString();
//
//        GoogleAccount googlePojo = new Gson().fromJson(response, GoogleAccount.class);
//
//        return googlePojo;
//
//    }
//}
