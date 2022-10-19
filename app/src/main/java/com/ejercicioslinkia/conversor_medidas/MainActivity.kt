package com.ejercicioslinkia.conversor_medidas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {

    private lateinit var spEntry :Spinner
    private lateinit var spExit: Spinner
    private lateinit var etNumberEntry : EditText
    private lateinit var btConvert: Button
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //asociamos las variables a los views del xml
        spEntry = findViewById(R.id.spEntry)
        spExit = findViewById(R.id.spExit)
        etNumberEntry = findViewById(R.id.etNumberEntry)
        btConvert = findViewById(R.id.btConvert)
        tvResult = findViewById(R.id.tvResult)

        //llenamos spinners
        fillSpiner(spEntry)
        fillSpiner(spExit)

        //damos funcionalid a los spinners
        // TODO: dar funcionalidad a los spinners


        //damos funcionalidad al boton
        btConvert.setOnClickListener {
            convertUnits()
        }

    }

    private fun fillSpiner(spinner:Spinner){
        var sistemUnits : Array<String> = resources.getStringArray(R.array.units)
        var adapter : ArrayAdapter<String> = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, sistemUnits)
        spinner.setAdapter(adapter)
    }

    private fun convertUnits(){
        var entryUnit : String=""
        var exitUnit : String=""
        var entryValue : Double=0.0
        var exitValue : Double=0.0


    }

    private fun convertUnitToM2(unit:String, value:Double):Double{
        var result: Double = 0.0
        when(unit){
            "Kilómetro cuadrado"->result=value*1000000
            "Metro cuadrado"->result=value
            "Milla cuadrada"->result=value*2590000
            "Yarda cuadrada"->result=value/1.196
            "Pie cuadrado"->result=value/10.764
            "Pulgada cuadrada"->result=value/1500
            "Hectárea"->result=value*10000
            "Acre"->result=value*4047
        }
        return result
    }

    private fun convertM2toUnit(unit:String,value: Double):Double{
        var result: Double =0.0
        return result
    }
}