package com.example.horoscopo.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.horoscopo.data.Horoscope
import com.example.horoscopo.R
import com.example.horoscopo.utils.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class DetailActivity : AppCompatActivity() {

    lateinit var session: SessionManager

    lateinit var horoscope: Horoscope
    var isFavorite = false

    lateinit var favoriteMenuItem: MenuItem


    lateinit var signImageView: ImageView
    lateinit var nameTextView: TextView
    lateinit var datesTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        signImageView = findViewById(R.id.signImageView)
        nameTextView = findViewById(R.id.nameTextView)
        datesTextView = findViewById(R.id.datesTextView)


        session = SessionManager(this)

        val id = intent.getStringExtra("HOROSCOPE_ID")!!

        horoscope = Horoscope.getById(id)

        supportActionBar?.setTitle(horoscope.name)
        supportActionBar?.setSubtitle(horoscope.date)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        nameTextView.setText(horoscope.name)
        datesTextView.setText(horoscope.date)
        signImageView.setImageResource(horoscope.sign)

        //Preguntar si horoscopo es favorito para rellenar el corazon del menu
        isFavorite = session.isFavorite(id)

        CoroutineScope(Dispatchers.IO).launch {
            val prediction = getHoroscopePrediction()

            runOnUiThread {
                // Alternativa para volver al hilo principal, pero solo esta disponible en Activity.
            }

            CoroutineScope(Dispatchers.Main).launch {
                nameTextView.text = prediction
            }
        }
    }
    //52:30

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_detail_menu, menu)

        favoriteMenuItem = menu.findItem(R.id.menu_favorite)

        setFavoriteIcon()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                Toast.makeText(this, "Atrás", Toast.LENGTH_SHORT).show()
                finish()
                true
            }

            R.id.menu_favorite -> {
                //Pregunta si el horoscopo es favorito o no, para guar en session o eliminarlo
                if (isFavorite) {
                    session.setFavorite("")
                } else {
                    session.setFavorite(horoscope.id)
                }
                isFavorite = !isFavorite
                setFavoriteIcon()
                true
            }

            R.id.menu_share -> {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                sendIntent.type = "text/plain"

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    fun setFavoriteIcon() {
        if (isFavorite) {
            favoriteMenuItem.setIcon(R.drawable.ic_favorite_selected)

        } else {
            favoriteMenuItem.setIcon(R.drawable.ic_favorite)
        }
    }

    fun getHoroscopePrediction(): String? {
        var result: String? = null

        val urlGetRequest =
            URL("https://freehoroscopeapi.com/api/v1/get-horoscope/daily?sign=aries")

        val apiConnection = urlGetRequest.openConnection() as HttpsURLConnection

        apiConnection.setRequestMethod("GET")

        try {
            val responseCode = apiConnection.getResponseCode()

            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = readInputStream(apiConnection.getInputStream())

                Log.i("API REST", response)
                result = JSONObject(response).getJSONObject("data").getString("horoscope")

            } else {
                Log.w("API REST", "Status code: $responseCode")
            }
        } catch (e: Exception) {
            Log.e("API REST", e.localizedMessage, e)
        } finally {
            apiConnection.disconnect()
        }
        return result
    }
}

fun readInputStream(inputStream: InputStream): String {
    val `in` = BufferedReader(InputStreamReader(inputStream))
    val response = StringBuffer()
    var inputLine: String? = null

    while ((`in`.readLine().also { inputLine = it }) != null) {
        response.append(inputLine)
    }
    `in`.close()
    return response.toString()
}