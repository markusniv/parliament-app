package com.example.membersofparliamentapp.network

import com.example.membersofparliamentapp.model.Member
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

/** (c) Markus Nivasalo, 16.9.2021
 *
 *      A singleton for handling the Retrofit and Moshi calls for retrieving the parliament members
 *      from backend. Call is initiated by the DownloadWorker class.
 */

private const val BASE_URL = "https://users.metropolia.fi/~markuniv/mps/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MemberApiService {
    @GET("mps.json")
    suspend fun getMembers() : List<Member>
}

object MemberApi {
    val retrofitService : MemberApiService by lazy {
        retrofit.create(MemberApiService::class.java)
    }
}