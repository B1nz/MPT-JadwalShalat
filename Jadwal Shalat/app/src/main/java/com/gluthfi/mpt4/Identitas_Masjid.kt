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
import kotlinx.android.synthetic.main.activity_jadwal__shalat.*
import org.json.JSONArray
import org.json.JSONObject

class Identitas_Masjid : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_identitas__masjid)

        val context = this

        saveimBtn.setOnClickListener {
            var data_namamasjid = nmInput.text.toString()
            var data_alamatmasjid= amInput.text.toString()

            postkeserver(data_namamasjid, data_alamatmasjid)

            val intent = getIntent()
            startActivity(intent)
            finish()
        }

        backimBtn.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        getdariServer()
    }

    fun getdariServer(){

        AndroidNetworking.get("http://192.168.0.90/jadwal_sholat/identitas_masjid.php")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject){
                    Log.e("_kotlinResponse", response.toString())

                    val jsonArray = response.getJSONArray("result")
                    for (i in 0 until jsonArray.length()){
                        val jsonObject = jsonArray.getJSONObject(i)
                        Log.e("_kotlinTitle", jsonObject.optString("nama_masjid"))
                        Log.e("_kotlinTitle", jsonObject.optString("alamat_masjid"))

                        namamTxt.setText(jsonObject.optString("nama_masjid"))
                        alamatmTxt.setText(jsonObject.optString("alamat_masjid"))
                    }
                }

                override fun onError(anError: ANError) {
                    Log.i("_err", anError.toString())
                }
            })
    }

    fun postkeserver(data1:String, data2:String) {
        AndroidNetworking.post("http://192.168.0.90/jadwal_sholat/proses_identitas.php")
            .addBodyParameter("nama_masjid", data1)
            .addBodyParameter("alamat_masjid", data2)
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
