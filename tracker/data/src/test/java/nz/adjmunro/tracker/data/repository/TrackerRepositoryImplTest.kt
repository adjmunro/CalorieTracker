package nz.adjmunro.tracker.data.repository

import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import nz.adjmunro.tracker.data.remote.OpenFoodApi
import nz.adjmunro.tracker.data.remote.malformedFoodResponse
import nz.adjmunro.tracker.data.remote.validFoodResponse
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

@OptIn(ExperimentalCoroutinesApi::class)
class TrackerRepositoryImplTest {

    private lateinit var trackerRepositoryImpl: TrackerRepositoryImpl
    private lateinit var mockWebServer: MockWebServer
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var api: OpenFoodApi

    @Before
    fun setUp() {
        mockWebServer = MockWebServer().apply { start() }

        okHttpClient = OkHttpClient.Builder()
            .writeTimeout(1.seconds.toJavaDuration())
            .readTimeout(1.seconds.toJavaDuration())
            .connectTimeout(1.seconds.toJavaDuration())
            .build()

        api = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(mockWebServer.url(path = "/"))
            .build()
            .create()

        trackerRepositoryImpl = TrackerRepositoryImpl(
            dao = mockk(relaxed = true),
            api = api,
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `search food, valid response, returns results`() = runTest {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200).setBody(validFoodResponse)
        )

        val result = trackerRepositoryImpl.searchFood(
            query = "banana",
            page = 1,
            pageSize = 40,
        )

        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isNotNull()
        assertThat(result.getOrNull()).isNotEmpty()
    }

    @Test
    fun `search food, invalid response, returns failure`() = runTest {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(403).setBody(validFoodResponse)
        )

        val result = trackerRepositoryImpl.searchFood(
            query = "banana",
            page = 1,
            pageSize = 40,
        )

        assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `search food, malformed response, returns failure`() = runTest {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200).setBody(malformedFoodResponse)
        )

        val result = trackerRepositoryImpl.searchFood(
            query = "banana",
            page = 1,
            pageSize = 40,
        )

        assertThat(result.isFailure).isTrue()
    }
}
