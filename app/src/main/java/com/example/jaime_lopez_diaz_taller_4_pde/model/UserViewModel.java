package com.example.jaime_lopez_diaz_taller_4_pde.model;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.jaime_lopez_diaz_taller_4_pde.dataBase.repos.UserRepository;


public class UserViewModel extends AndroidViewModel {

    private final MutableLiveData<String> userName;
    private final SharedPreferences sharedPreferences;
    private final UserRepository userRepository;

    public UserViewModel(Application application) {
        super(application);
        sharedPreferences = application.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        userRepository = new UserRepository(application);
        userName = new MutableLiveData<>();
        loadUserName();
    }

    public LiveData<String> getUserName() {
        return userName;
    }

    public void saveUserName(String name) {
        userName.setValue(name);

        // Guardar en SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName", name);
        editor.apply();

        // Guardar en SQLite y Firestore
        userRepository.open();
        userRepository.saveUserName(name);
        userRepository.close();
    }

    private void loadUserName() {
        // Cargar desde SharedPreferences
        String name = sharedPreferences.getString("userName", "");

        if (name == null || name.isEmpty()) {
            // Si no se encuentra en SharedPreferences, cargar desde SQLite
            userRepository.open();
            name = userRepository.getUserName();
            userRepository.close();
        }

        userName.setValue(name);
    }
}
