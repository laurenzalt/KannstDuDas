package at.fhj.kannstdudas.di

import at.fhj.kannstdudas.data.datasource.FirestoreUserDataSource
import at.fhj.kannstdudas.data.datasource.FirestoreSkillDataSource
import at.fhj.kannstdudas.data.repository.FirestoreSkillRepository
import at.fhj.kannstdudas.data.repository.UserRepository
import at.fhj.kannstdudas.domain.datasource.SkillDataSource
import at.fhj.kannstdudas.domain.datasource.UserDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * at.fhj.kannstdudas.di
 * Created by Noah Dimmer on 12/06/2024
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    @Singleton
    fun provideUserDataSource(
        firestore: FirebaseFirestore,
        storage: FirebaseStorage
    ): UserDataSource = FirestoreUserDataSource(firestore, storage)

    @Provides
    @Singleton
    fun provideSkillDataSource(
        firestore: FirebaseFirestore
    ): SkillDataSource = FirestoreSkillDataSource(firestore)

    @Provides
    @Singleton
    fun provideUserRepository(
        firebaseAuth: FirebaseAuth,
        userDataSource: UserDataSource
    ): UserRepository = UserRepository(firebaseAuth, userDataSource)
}