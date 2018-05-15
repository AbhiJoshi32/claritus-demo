package com.example.claritus.claritus.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.claritus.claritus.api.ClaritusService;
import com.example.claritus.claritus.db.ClaritusDb;
import com.example.claritus.claritus.db.UserDao;
import com.example.claritus.claritus.utils.LiveDataCallAdapterFactory;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Module(includes = ViewModelModule.class)
class AppModule {
    @Singleton@Provides
    Gson providesGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }
    @Singleton @Provides
    ClaritusService provideClaritusService() {
        return new Retrofit.Builder()
                .baseUrl("http://10.10.1.29/clarituscore/backend/web/index.php/")
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(ClaritusService.class);
    }

    @Singleton @Provides
    ClaritusDb provideDb(Application app) {
        return Room.databaseBuilder(app, ClaritusDb.class,"github.db").allowMainThreadQueries().build();
    }

    @Singleton @Provides
    UserDao provideUserDao(ClaritusDb db) {
        return db.userDao();
    }

    @Singleton @Provides
    SharedPreferences provideSharedPreferences(Application app) {
        return app.getSharedPreferences("ClaritusPref", Context.MODE_PRIVATE);
    }
}
