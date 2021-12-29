package com.example.mydynamicviewapp

import android.app.Application

class MyApplication  : Application() {
    var mApiComponent: UserComponent? = null
    override fun onCreate() {
        super.onCreate()
        ///  val database by lazy { DatabaseHelper.getDatabase(this) }


        mApiComponent = DaggerUserComponent.builder()
            // .userModule( UserModule(this))

            .aPIModule(APIModule("https://demo2.cedcommerce.com/"))

            .build()
    }

    fun getNetComponent(): UserComponent? {
        return mApiComponent

    }
}