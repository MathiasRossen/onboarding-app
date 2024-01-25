package dk.mathiasrossen.onboardingapp.dependency_injection

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dk.mathiasrossen.onboardingapp.data.AppDatabase
import dk.mathiasrossen.onboardingapp.data.room_type_converters.LocalDateTimeConverter

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "onboarding-db")
            .addTypeConverter(LocalDateTimeConverter())
            .build()
}