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
import kotlinx.android.synthetic.main.activity_pengumuman_masjid.*
import kotlinx.android.synthetic.main.activity_tagline.*
import kotlinx.android.synthetic.main.activity_tagline.taglineTV
import org.json.JSONArray
import org.json.JSONObject

class Pengumuman_masjid : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengumuman_masjid)

        pmBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        pengSave.setOnClickListener {
            var data1 = jpmInput.text.toString()
            var data2 = ipmInput.text.toString()

            postkeserver(data1, data2)

            val intent = getIntent()
            startActivity(intent)
            finish()
        }

        getdariServer()
    }

    fun getdariServer() {

        AndroidNetworking.get("http://192.168.0.90/jadwal_sholat/pengumuman_masjid.php")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Log.e("_kotlinResponse", response.toString())

                    val jsonArray = response.getJSONArray("result")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        Log.e("_kotlinTitle", jsonObject.optString("judul_pengumuman"))

                        judulTV.setText(jsonObject.optString("judul_pengumuman"))
                        isiTV.setText(jsonObject.optString("isi_pengumuman"))
                    }
                }

                override fun onError(anError: ANError) {
                    Log.i("_err", anError.toString())
                }
            })
    }

    fun postkeserver(data1:String, data2:String) {
        AndroidNetworking.post("http://192.168.0.90/jadwal_sholat/update_pengumuman.php")
            .addBodyParameter("judul_pengumuman", data1)
            .addBodyParameter("isi_pengumuman", data2)
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
