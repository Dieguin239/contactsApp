package com.upn.contactsapp.service;

import com.upn.contactsapp.entities.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api10 {
    @GET("contacts")
    Call<List<Contact>> getContacts(
            @Query("limit") int limit,
            @Query("page") int page
    );
}
