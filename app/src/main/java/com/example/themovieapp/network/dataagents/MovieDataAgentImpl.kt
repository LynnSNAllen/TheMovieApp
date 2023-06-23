package com.example.themovieapp.network.dataagents

import android.os.AsyncTask
import android.util.Log
import com.example.themovieapp.data.vos.ActorVO
import com.example.themovieapp.data.vos.GenreVO
import com.example.themovieapp.data.vos.MovieVO
import com.example.themovieapp.network.responses.MovieListResponse
import com.example.themovieapp.utils.API_GET_POPULAR_MOVIES
import com.example.themovieapp.utils.APT_GET_NOW_PLAYING
import com.example.themovieapp.utils.BASE_URL
import com.example.themovieapp.utils.MOVIE_API_KEY
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

object MovieDataAgentImpl : MovieDataAgent {

    override fun getNowPlayingMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
         GetNowPlayingMovieTask().execute()
    }

    override fun getPopularMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {

    }

    override fun getTopRatedMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {

    }

    override fun getGenres(onSuccess: (List<GenreVO>) -> Unit, onFailure: (String) -> Unit) {

    }

    override fun getMoviesByGenres(
        genreId: String,
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {

    }

    override fun getActors(onSuccess: (List<ActorVO>) -> Unit, onFailure: (String) -> Unit) {

    }

    override fun getMovieDetails(
        movieId: String,
        onSuccess: (MovieVO) -> Unit,
        onFailure: (String) -> Unit
    ) {

    }

    override fun getCreditsByMovie(
        movieId: String,
        onSuccess: (Pair<List<ActorVO>, List<ActorVO>>) -> Unit,
        onFailure: (String) -> Unit
    ) {

    }

    class GetNowPlayingMovieTask() : AsyncTask<Void, Void, MovieListResponse?>(){

        override fun doInBackground(vararg params: Void?): MovieListResponse? {
           val url: URL
           var reader: BufferedReader? = null
           val stringBuilder : java.lang.StringBuilder

           try {
               //create the HttpURLConnection
               url = URL("""$BASE_URL$APT_GET_NOW_PLAYING?api_key=$MOVIE_API_KEY&language=en-US&page=1""") //1.url preparation

               val connection = url.openConnection() as HttpURLConnection //2.connection

               //Set HTTP Method
               connection.requestMethod = "Get" //3.method to connect URL (get post delete put)

               //Determine how long to wait ,what if url(endpoint) do not respond
               connection.readTimeout = 15 * 1000 //4.give time

               connection.doInput = true //5. accept data from URL
               connection.doOutput = false

               connection.connect() //6.connect url

               //read the output from the server
               reader = BufferedReader(
                   InputStreamReader(connection.inputStream) //7.read the data from the URL in BufferedReader line by line with InputStreamReader
               )
               stringBuilder = StringBuilder()

               for (line in reader.readLines()){
                   stringBuilder.append(line + "\n")  //8.read line from reader with stringBuilder
               }
               val respondString = stringBuilder.toString()
               Log.d("NowPlayingMovies" , respondString) //9.Respond the lines from stringBuilder

               val movieListResponse = Gson().fromJson(
                   respondString,
                   MovieListResponse::class.java
               )
               return movieListResponse
           }catch (e:Exception) {
               e.printStackTrace()
               Log.e("NewsError" , e.message ?: "")
           } finally {
               if (reader != null) {
                   try {
                       reader.close()
                   }catch (ioe : IOException) {
                       ioe.printStackTrace()
                   }
               }
           }
           return null
        }

        override fun onPostExecute(result: MovieListResponse?) {
            super.onPostExecute(result)
        }

    }
}