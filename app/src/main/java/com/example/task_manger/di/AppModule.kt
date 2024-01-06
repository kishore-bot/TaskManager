package com.example.task_manger.di

import android.app.Application
import com.example.task_manger.data.interceptor.TaskHeaderInterceptor
import com.example.task_manger.data.interceptor.UserHeaderInterceptor
import com.example.task_manger.data.manager.LocalUserManagerImp
import com.example.task_manger.data.remote.TaskManagerApi
import com.example.task_manger.data.remote.UserApi
import com.example.task_manger.data.repository.TaskRepoImp
import com.example.task_manger.data.repository.UserRepoImp
import com.example.task_manger.domian.manager.LocalUserManager
import com.example.task_manger.domian.repository.TaskRepository
import com.example.task_manger.domian.repository.UserRepository
import com.example.task_manger.domian.usecase.AppEntryUseCases
import com.example.task_manger.domian.usecase.TaskUseCases
import com.example.task_manger.domian.usecase.UserUseCases
import com.example.task_manger.domian.usecase.app_entry.ReadAppEntry
import com.example.task_manger.domian.usecase.app_entry.SaveAppEntry
import com.example.task_manger.domian.usecase.taskRemote.AddSubTask
import com.example.task_manger.domian.usecase.taskRemote.DeleteSubTask
import com.example.task_manger.domian.usecase.taskRemote.DeleteTask
import com.example.task_manger.domian.usecase.taskRemote.GetSingleTask
import com.example.task_manger.domian.usecase.taskRemote.GetStars
import com.example.task_manger.domian.usecase.taskRemote.GetTask
import com.example.task_manger.domian.usecase.taskRemote.PostTask
import com.example.task_manger.domian.usecase.taskRemote.PostTaskComplete
import com.example.task_manger.domian.usecase.userRemote.UserLogin
import com.example.task_manger.domian.usecase.userRemote.UserSign
import com.example.task_manger.utils.Constants.API
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application,
    ): LocalUserManager = LocalUserManagerImp(context = application)

    @Provides
    @Singleton
    fun provideAppEntryUseCase(
        localUserManager: LocalUserManager,
    ): AppEntryUseCases = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager = localUserManager)
    )

    @Provides
    @Singleton
    fun provideTaskHeaderInterceptor(localUserManager: LocalUserManager): TaskHeaderInterceptor {
        return TaskHeaderInterceptor(localUserManager)
    }

    @Provides
    @Singleton
    fun provideTaskApi(taskHeaderInterceptor: TaskHeaderInterceptor): TaskManagerApi {
        val okHttpClient = OkHttpClient.Builder().addInterceptor(taskHeaderInterceptor).build()
        return Retrofit.Builder().baseUrl(API).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(TaskManagerApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(): UserApi {
        val okHttpClient = OkHttpClient.Builder().addInterceptor(UserHeaderInterceptor()).build()
        return Retrofit.Builder().baseUrl(API).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build().create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTaskRepository(
        taskApi: TaskManagerApi,
    ): TaskRepository = TaskRepoImp(
        taskManagerApi = taskApi
    )

    @Provides
    @Singleton
    fun provideUserRepo(
        userApi: UserApi,
    ): UserRepository = UserRepoImp(
        userApi = userApi
    )


    @Provides
    @Singleton
    fun provideTaskUseCases(
        taskRepository: TaskRepository,
    ): TaskUseCases {
        return TaskUseCases(
            getTask = GetTask(taskRepository),
            postTask = PostTask(taskRepository),
            getSingleTask = GetSingleTask(taskRepository),
            postTaskComplete = PostTaskComplete(taskRepository),
            getStars = GetStars(taskRepository),
            deleteTask = DeleteTask(taskRepository),
            deleteSubTask = DeleteSubTask(taskRepository),
            addSubTask = AddSubTask(taskRepository)
        )
    }

    @Provides
    @Singleton
    fun provideUserUseCases(
        userRepository: UserRepository,
    ): UserUseCases {
        return UserUseCases(
            userSign = UserSign(userRepository), userLogin = UserLogin(userRepository)
        )
    }

}