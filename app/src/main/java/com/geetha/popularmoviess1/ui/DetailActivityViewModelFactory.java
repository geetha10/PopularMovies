package com.geetha.popularmoviess1.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.geetha.popularmoviess1.database.AppDatabase;
import com.geetha.popularmoviess1.models.Movie;
import com.geetha.popularmoviess1.network.MoviesApi;

public class DetailActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private MoviesApi api;
    private AppDatabase database;
    private Movie movie;

    public DetailActivityViewModelFactory(MoviesApi api, AppDatabase database, Movie movie) {
        this.api = api;
        this.database = database;
        this.movie = movie;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailsActivityViewModel (api, database, movie);
    }
}
