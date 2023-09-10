package com.example.mapusing

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.mapusing.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task

class MainActivity : AppCompatActivity() {

    lateinit var  flsp:FusedLocationProviderClient
    lateinit var ls:Task<Location>
    lateinit var  binding: ActivityMainBinding
    private var izinkontrol=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        flsp=LocationServices.getFusedLocationProviderClient(this)


        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {


            izinkontrol=ContextCompat.checkSelfPermission(this@MainActivity,Manifest.permission.ACCESS_FINE_LOCATION)


            if (izinkontrol!=PackageManager.PERMISSION_GRANTED){

                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),100)

            }
            else{

                ls=flsp.lastLocation
                locationinfo()

            }
        }
    }
fun locationinfo(){
    ls.addOnSuccessListener {

        if (it!=null){
            binding.en.text=it.latitude.toString()
            binding.uzun.text=it.longitude.toString()
        }else{
            binding.en.text="en alinmadi"
            binding.uzun.text="uzunluq alinmadi"
        }


    }
}

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {

        if (requestCode==100){

            if (grantResults.size>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                izinkontrol=ContextCompat.checkSelfPermission(this@MainActivity,Manifest.permission.ACCESS_FINE_LOCATION)

                Toast.makeText(applicationContext,"icaze verildi",Toast.LENGTH_SHORT)
                ls=flsp.lastLocation
                locationinfo()
            }
            else{
                Toast.makeText(applicationContext,"icaze verilmedi",Toast.LENGTH_SHORT)

            }
        }

    }
}