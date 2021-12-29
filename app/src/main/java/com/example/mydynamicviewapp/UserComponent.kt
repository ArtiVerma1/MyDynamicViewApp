package com.example.mydynamicviewapp

import dagger.Component
import javax.inject.Singleton

@Component(modules = [APIModule::class])
@Singleton
interface UserComponent {
    // public fun provideRetrofit(): Retrofitobj?

    // open fun getRetrofit(): Retrofit?
    fun inject(activity: MainActivity)

    //fun inject(homefrag: HomeFragment)
    // fun inject(viewhome: HomeViewModel)
}