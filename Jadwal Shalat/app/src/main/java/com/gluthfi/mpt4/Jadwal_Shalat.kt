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
import kotlinx.android.synthetic.main.activity_jadwal__shalat.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

class Jadwal_Shalat : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jadwal__shalat)

        val context = this

        backjsBtn.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        updatejsBtn.setOnClickListener {
            startActivity(Intent(this, Update_shalat::class.java))
            finish()
        }

        getdariServer()
    }

    fun getdariServer(){

        AndroidNetworking.get("http://192.168.0.90/jadwal_sholat/jadwal_sholat.php")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject){
                    Log.e("_kotlinResponse", response.toString())

                    val jsonArray = response.getJSONArray("result")
                    for (i in 0 until jsonArray.length()){
                        val jsonObject = jsonArray.getJSONObject(i)
                        Log.e("_kotlinTitle", jsonObject.optString("shubuh"))
                        Log.e("_kotlinTitle", jsonObject.optString("dhuha"))
                        Log.e("_kotlinTitle", jsonObject.optString("dhuhur"))
                        Log.e("_kotlinTitle", jsonObject.optString("ashar"))
                        Log.e("_kotlinTitle", jsonObject.optString("maghrib"))
                        Log.e("_kotlinTitle", jsonObject.optString("isha"))

                        txt1.setText(jsonObject.optString("shubuh"))
                        txt2.setText(jsonObject.optString("dhuha"))
                        txt3.setText(jsonObject.optString("dhuhur"))
                        txt4.setText(jsonObject.optString("ashar"))
                        txt5.setText(jsonObject.optString("maghrib"))
                        txt6.setText(jsonObject.optString("isha"))
                    }
                }

                override fun onError(anError: ANError) {
                    Log.i("_err", anError.toString())
                }
            })
    }
}