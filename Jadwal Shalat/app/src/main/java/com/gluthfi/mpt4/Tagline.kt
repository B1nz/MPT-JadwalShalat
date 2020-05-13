package com.gluthfi.mpt4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_identitas__masjid.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_tagline.*
import org.json.JSONArray
import org.json.JSONObject

class Tagline : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tagline)

        idBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        tagSave.setOnClickListener {
            var data1 = tagInput.text.toString()

            postkeserver(data1)

            val intent = getIntent()
            startActivity(intent)
            finish()
        }

        getdariServer()
    }

    fun getdariServer() {

        AndroidNetworking.get("http://192.168.0.90/jadwal_sholat/tagline.php")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Log.e("_kotlinResponse", response.toString())

                    val jsonArray = response.getJSONArray("result")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        Log.e("_kotlinTitle", jsonObject.optString("isi_tagline"))

                        taglineTV.setText(jsonObject.optString("isi_tagline"))
                    }
                }

                override fun onError(anError: ANError) {
                    Log.i("_err", anError.toString())
                }
            })
    }

    fun postkeserver(data1:String) {
        AndroidNetworking.post("http://192.168.0.90/jadwal_sholat/update_tagline.php")
            .addBodyParameter("isi_tagline", data1)
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
