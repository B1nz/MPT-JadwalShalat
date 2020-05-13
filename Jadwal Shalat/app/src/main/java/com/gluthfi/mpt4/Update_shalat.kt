package com.gluthfi.mpt4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import kotlinx.android.synthetic.main.activity_update_shalat.*
import org.json.JSONArray

class Update_shalat : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_shalat)

        val context = this

        upSave.setOnClickListener {
            var subuh = subuhUp.text.toString()
            var duha= duhahUp.text.toString()
            var dzuhur = dzuhurUp.text.toString()
            var ashar = asharhUp.text.toString()
            var maghrib = maghribUp.text.toString()
            var isya = isyaUp.text.toString()

            postkeserver(subuh, duha, dzuhur, ashar, maghrib, isya)

            startActivity(Intent(this, Jadwal_Shalat::class.java))
            finish()
        }

        upBack.setOnClickListener {
            val intent = Intent(context, Jadwal_Shalat::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun postkeserver(data1:String, data2:String, data3:String, data4:String, data5:String, data6:String) {
        AndroidNetworking.post("http://11.11.11.97/jadwal_sholat/update_shalat.php")
            .addBodyParameter("dhuha", data2)
            .addBodyParameter("shubuh", data1)
            .addBodyParameter("dhuhur", data3)
            .addBodyParameter("ashar", data4)
            .addBodyParameter("maghrib", data5)
            .addBodyParameter("isha", data6)
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
