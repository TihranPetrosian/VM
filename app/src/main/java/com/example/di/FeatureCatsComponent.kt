package com.example.di

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.vm.share_domain.use_case.fetch_cats.FetchCatsUseCase
import com.example.vm.share_domain.use_case.fetch_dogs.FetchDogsUseCase
import dagger.BindsInstance
import dagger.Component
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FeatureCatsModuleDependencies {

    fun fetchCatsUseCase(): FetchCatsUseCase
}

@Component(dependencies = [FeatureDogsModuleDependencies::class])
interface FeatureCatsComponent {

    fun inject(screen: Fragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(featureCatsModuleDependencies: FeatureCatsModuleDependencies): Builder
        fun build(): FeatureCatsComponent
    }
}