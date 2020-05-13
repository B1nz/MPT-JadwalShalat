package com.gluthfi.mpt4

import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val context = this

        marq12.setSelected(true)

        jsBtn.setOnClickListener {
            val intent = Intent(context, Jadwal_Shalat::class.java)
            startActivity(intent)
            finish()
        }

        imBtn.setOnClickListener {
            val intent = Intent(context, Identitas_Masjid::class.java)
            startActivity(intent)
            finish()
        }

        slideBtn.setOnClickListener {
            val intent = Intent(context, Slideshow::class.java)
            startActivity(intent)
            finish()
        }

        tagBtn.setOnClickListener {
            startActivity(Intent(this, Tagline::class.java))
            finish()
        }

        pmBtn.setOnClickListener {
            startActivity(Intent(this, Pengumuman_masjid::class.java))
            finish()
        }

        marqBtn.setOnClickListener {
            startActivity(Intent(this, Marquee::class.java))
            finish()
        }


        getdariServer()
    }

    fun getdariServer() {

        AndroidNetworking.get("http://192.168.0.90/jadwal_sholat/marquee.php")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Log.e("_kotlinResponse", response.toString())

                    val jsonArray = response.getJSONArray("result")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        Log.e("_kotlinTitle", jsonObject.optString("isi_marquee"))

                        marq12.setText(jsonObject.optString("isi_marquee"))
                    }
                }

                override fun onError(anError: ANError) {
                    Log.i("_err", anError.toString())
                }
            })
    }
}
