package com.orumgames.data.network.service

import com.orumgames.data.model.BeerDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BeersService {

    @GET("beers")
    suspend fun getAllBeers(@Query ("page") page: Int, @Query ("per_page") pageCount: Int): Response<List<BeerDTO>>

    @GET("beers/{id}")
    suspend fun getDetailBeer(@Path ("id") id: Int): Response<List<BeerDTO>>
}