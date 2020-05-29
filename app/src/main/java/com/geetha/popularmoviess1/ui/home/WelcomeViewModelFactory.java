package com.geetha.popularmoviess1.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.geetha.popularmoviess1.database.AppDatabase;
import com.geetha.popularmoviess1.network.MoviesApi;

public class WelcomeViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private MoviesApi api;
    private AppDatabase database;

    public WelcomeViewModelFactory(MoviesApi api, AppDatabase database) {
        this.api = api;
        this.database = database;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new WelcomeActivityViewModel (api, database);
    }
}
