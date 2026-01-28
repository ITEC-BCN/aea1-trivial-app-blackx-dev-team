package com.example.trivialapp_base.network

import android.content.Context
import android.content.SharedPreferences
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import androidx.core.content.edit

private const val BASE_URL = "https://opentdb.com/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

data class TokenResponse(
    val response_code: Int,
    val response_message: String? = null,
    val token: String
)

data class Category(
    val id: Int,
    val name: String
)

data class CategoryResponse(
    val trivia_categories: List<Category>
)

data class Question(
    val category: String,
    val difficulty: String,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>
)

data class QuestionResponse(
    val response_code: Int,
    val results: List<Question>
)

interface TriviaApiService {
    @GET("api_token.php")
    suspend fun retrieveSessionToken(
        @Query("command") command: String = "request"
    ): TokenResponse

    @GET("api_token.php")
    suspend fun resetSessionToken(
        @Query("command") command: String = "reset",
        @Query("token") token: String? = TokenManager.getToken()
    ): TokenResponse

    @GET("api_category.php")
    suspend fun getCategories(): CategoryResponse

    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount") amount: Int = 10,
        @Query("category") category: Int? = null,
        @Query("difficulty") difficulty: String? = null,
        @Query("type") type: String = "multiple",
        @Query("token") token: String? = TokenManager.getToken()
    ): QuestionResponse
}

object OTDBApi {
    val retrofitService: TriviaApiService by lazy {
        retrofit.create(TriviaApiService::class.java)
    }

}

object TokenManager {
    private const val PREFS_NAME = "otdb_prefs"
    private const val KEY_TOKEN = "session_token"
    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveToken(token: String) {
        prefs.edit { putString(KEY_TOKEN, token) }
    }

    fun getToken(): String? = prefs.getString(KEY_TOKEN, null)

    fun clearToken() {
        prefs.edit { remove(KEY_TOKEN) }
    }

    suspend fun resetToken() {
        val token = getToken()
        if (token != null) {
            OTDBApi.retrofitService.resetSessionToken(token = token)
        }
    }
}
