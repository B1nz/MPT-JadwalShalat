package com.gluthfi.mpt4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import kotlinx.android.synthetic.main.activity_identitas__masjid.*
import kotlinx.android.synthetic.main.activity_marquee.*
import org.json.JSONArray

class Marquee : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marquee)

        marqSave.setOnClickListener {
            var data1 = marqInput.text.toString()

            postkeserver(data1)

            val intent = getIntent()
            startActivity(intent)
            finish()
        }

        marqBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun postkeserver(data1:String) {
        AndroidNetworking.post("http://192.168.0.90/jadwal_sholat/update_marquee.php")
            .addBodyParameter("isi_marquee", data1)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray?) {
                    Log.i("Uji Coba", "Sukses")
                }

                override fun onError(anError: ANError?) {
                    Log.i("Uji Coba", "Mandul")
                }
            })
    }
}
