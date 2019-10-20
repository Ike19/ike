package com.example.smkcoding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.smkcoding.conection.database.DatabaseContract
import com.example.smkcoding.conection.database.database
import com.example.smkcoding.model.ResultsItem
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.item_movie.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

class DetailMovieActivity : AppCompatActivity() {

    var isMovieFavorite : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        val movie = intent.getParcelableExtra<ResultsItem>("movie")

        tv_title_movie.text = movie?.title
        tv_rating_movie.text = "Rating : $(movie?.voteAverage)"
        tv_description_movie.text = movie?.overview
        Glide.with ( this).load("https://image.tmdb.org/t/p/w500"+movie?.posterPath)
            .into(iv_poster_movie)

        // cek dulu movie udah favorite atau belum
        checkMovieFavorite(movie)

        btn_favorit.onClick {
            // jika merupakan movie favorite, untuk menghapus movie dari daftar favorite
            // jika bukan, maka tombol difungsikan untuk menambah ke daftar favorite
            if (isMovieFavorite) {
                deleteMovieFromFavorite(movie)
            } else {
                addMovieFavorite(movie)
            }
            addMovieFavorite(movie)
        }
    }

    private fun deleteMovieFromFavorite(movie: ResultsItem?) {
        database.use {
            delete(ResultsItem.TABLE_FAVORITE,
            "${ResultsItem.COLUMN_TITLE} = {title}",
            "title" to movie?.title.toString()
            )
            toast("Movie dihapus dari daftar favorit")
            isMovieFavorite = false
            btn_favorit.text = "Tambah Favorit"
        }
    }

    private fun addMovieFavorite(movie: ResultsItem?) {
        database.use {
            insert(
                ResultsItem.TABLE_FAVORITE,
                ResultsItem.COLUMN_TITLE to movie?.title,
                ResultsItem.COLUMN_POSTERPATH to movie?.popularity,
                ResultsItem.COLUMN_RATING to movie?.popularity,
                ResultsItem.COLUMN_DESCRIPTION to movie?.popularity
            )

            toast("Berhasil ditambah ke favorit!!")

            isMovieFavorite = true
            btn_favorit.text = "Hapus Favorit"
        }
    }

    private fun checkMovieFavorite(movie: ResultsItem?) {
        // pengecekan film ini sudah favorite atau belum
        database.use {
            val isFavorite = select(ResultsItem.TABLE_FAVORITE)
                .whereArgs(ResultsItem.COLUMN_TITLE+" = {title}",
             "title" to movie?.title.toString())
            val dataMovie : DatabaseContract? = isFavorite.parseOpt(classParser<DatabaseContract>())

            Log.d( "FAVORITEMOVIE", dataMovie.toString())

            if (dataMovie!=null){
                isMovieFavorite = true
                btn_favorit.text = "Hapus Favorit"
            } else {
                isMovieFavorite = false
                btn_favorit.text = "Tambah Favorit"

            }
        }
    }
}
