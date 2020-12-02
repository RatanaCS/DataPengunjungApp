package com.sumuzu.datapengunjung

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sumuzu.datapengunjung.config.NetworkModule
import com.sumuzu.datapengunjung.model.ResponseAction
import com.sumuzu.datapengunjung.model.getData.DataItem
import kotlinx.android.synthetic.main.activity_input.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        val getData = intent.getParcelableExtra<DataItem>("data")

        if(getData != null){
            etNama.setText(getData.nama)
            etNoHP.setText(getData.nohp)
            etKeperluan.setText(getData.keperluan)
            btnSimpan.text = "Update"
        }

        when(btnSimpan.text){
            "Update" ->{
                btnSimpan.setOnClickListener {
                    if(etNama.text.isNullOrEmpty() || etNoHP.text.isNullOrEmpty() || etKeperluan.text.isNullOrEmpty()){
                        Toast.makeText(applicationContext, "Nama, No Hp, Keperluan Anda harus terisi!!", Toast.LENGTH_LONG).show()
                    }else{
                        updateData(getData?.id, etNama.text.toString(), etNoHP.text.toString(), etKeperluan.text.toString())
                    }
                }
            }
            else -> {
                btnSimpan.setOnClickListener {
                    if(etNama.text.isNullOrEmpty() || etNoHP.text.isNullOrEmpty() || etKeperluan.text.isNullOrEmpty()){
                        Toast.makeText(applicationContext, "Nama, No Hp, Keperluan Anda harus terisi!!", Toast.LENGTH_LONG).show()
                    }else{
                        inputData(etNama.text.toString(), etNoHP.text.toString(), etKeperluan.text.toString())
                    }
                }
            }
        }

        btnCancel.setOnClickListener {
            finish()
        }


    }


    private fun inputData(nama : String?, nohp : String?, keperluan : String?){
        val input = NetworkModule.service().insertData(nama ?:"", nohp ?:"", keperluan ?:"")
        input.enqueue(object  : Callback<ResponseAction> {
            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
                if(response.isSuccessful){
                    Toast.makeText(applicationContext, "Data berhasil ditambahkan", Toast.LENGTH_LONG).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun updateData(id : String?, nama : String?, nohp : String?, keperluan : String?){
        val update = NetworkModule.service().updateData(id ?:"", nama ?:"", nohp ?:"", keperluan ?:"")
        update.enqueue(object : Callback<ResponseAction>{
            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
                if(response.isSuccessful){
                    Toast.makeText(applicationContext, "Data berhasil diubah", Toast.LENGTH_LONG).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }




}