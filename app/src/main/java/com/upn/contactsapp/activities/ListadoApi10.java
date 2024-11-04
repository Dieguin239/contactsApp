package com.upn.contactsapp.activities;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.upn.contactsapp.adapters.ContactAdaptar;
import com.upn.contactsapp.entities.Contact;
import com.upn.contactsapp.service.Api10;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.upn.contactsapp.R;

public class ListadoApi10 extends AppCompatActivity {

    private static final String BASE_URL = "https://66d5b903f5859a7042673752.mockapi.io/";
    private static final int LIMIT = 10;
    private int currentPage = 1;

    private RecyclerView recyclerView;
    private ContactAdaptar contactAdapter;
    private List<Contact> contactList = new ArrayList<>();
    private Api10 contactService;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_api10);

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.reciclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactAdapter = new ContactAdaptar(contactList);
        recyclerView.setAdapter(contactAdapter);

        // Configurar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        contactService = retrofit.create(Api10.class);

        // Cargar los primeros contactos
        loadContacts(currentPage);

        // Configurar scroll infinito
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading && layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == contactList.size() - 1) {
                    // Cargar más contactos
                    isLoading = true;
                    currentPage++;
                    loadContacts(currentPage);
                }
            }
        });
    }

    private void loadContacts(int page) {
        Call<List<Contact>> call = contactService.getContacts(LIMIT, page);
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(@NonNull Call<List<Contact>> call, @NonNull Response<List<Contact>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    contactList.addAll(response.body());
                    contactAdapter.notifyDataSetChanged();
                }
                isLoading = false;
            }

            @Override
            public void onFailure(@NonNull Call<List<Contact>> call, @NonNull Throwable t) {
                Log.e("ContactsActivity", "Error fetching contacts", t);
                isLoading = false;
            }
        });
    }
}
