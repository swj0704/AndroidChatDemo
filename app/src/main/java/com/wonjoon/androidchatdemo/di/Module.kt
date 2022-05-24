package com.wonjoon.androidchatdemo.di

import android.content.Context
import androidx.room.Room
import com.wonjoon.data.ChatRepositoryImpl
import com.wonjoon.data.UserRepositoryImpl
import com.wonjoon.data.room.chat.ChatDao
import com.wonjoon.data.room.chat.ChatDatabase
import com.wonjoon.data.room.user.UserDao
import com.wonjoon.data.room.user.UserDatabase
import com.wonjoon.domain.ChatRepository
import com.wonjoon.domain.UserRepository
import com.wonjoon.domain.usecase.GetChatRoomUseCase
import com.wonjoon.domain.usecase.LoginUseCase
import com.wonjoon.domain.usecase.SearchChatRoomUseCase
import com.wonjoon.domain.usecase.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Provides
    @Singleton
    fun provideGetChatRoomUseCase(repository: ChatRepository) : GetChatRoomUseCase{
        return GetChatRoomUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideLoginUseCase(repository: UserRepository) : LoginUseCase{
        return LoginUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSearchChatRoomUseCase(repository: ChatRepository) : SearchChatRoomUseCase{
        return SearchChatRoomUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideSignUpUseCase(repository: UserRepository) : SignUpUseCase{
        return SignUpUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideChatRepository(dao: ChatDao) : ChatRepository {
        return ChatRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideUserRepository(dao: UserDao) : UserRepository {
        return UserRepositoryImpl(dao)
    }

    @Singleton
    @Provides
    fun provideUserDao(dataBase: UserDatabase) : UserDao {
        return dataBase.getDao()
    }

    @Singleton
    @Provides
    fun provideUserDataBase(@ApplicationContext appContext: Context) : UserDatabase{
        return provideUserDB(appContext)
    }

    @Singleton
    @Provides
    fun provideChatDao(dataBase: ChatDatabase) : ChatDao {
        return dataBase.getDao()
    }

    @Singleton
    @Provides
    fun provideChatDataBase(@ApplicationContext appContext: Context) : ChatDatabase {
        return provideChatDB(appContext)
    }
}

internal fun provideUserDB(context: Context): UserDatabase =
    Room.databaseBuilder(context, UserDatabase::class.java, "userDatabase.db").build()

internal fun provideChatDB(context: Context): ChatDatabase =
    Room.databaseBuilder(context, ChatDatabase::class.java, "userDatabase.db").build()