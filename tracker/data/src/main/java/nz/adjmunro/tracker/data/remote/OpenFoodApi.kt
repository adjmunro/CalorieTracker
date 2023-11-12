package nz.adjmunro.tracker.data.remote

import nz.adjmunro.tracker.data.remote.dto.SearchDto
import retrofit2.http.GET
import retrofit2.http.Query

typealias SearchTerms = String
typealias Page = Int
typealias PageSize = Int

interface OpenFoodApi {
    @GET("cgi/search.pl?search_simple=1&json=1&action=process&fields=product_name,nutriments,image_front_thumb_url")
    suspend fun searchFood(
        @Query("search_terms") searchTerms: SearchTerms,
        @Query("page") page: Page,
        @Query("page_size") pageSize: PageSize,
    ): SearchDto

    companion object {
        const val BASE_URL = "http://nz.openfoodfacts.org/"
    }
}
