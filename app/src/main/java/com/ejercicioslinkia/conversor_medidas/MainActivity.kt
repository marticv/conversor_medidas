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




        //damos funcionalidad al boton
        btConvert.setOnClickListener {
            var result:Double=0.0
            var units:String

            result=convertUnits(spEntry,spExit,etNumberEntry)
            units=findUnit(spExit)
            tvResult.text="Total: ${result.toString()} $units"
        }

    }

    /**
     * funcion que rellena la informacion de los spinners
     */
    private fun fillSpiner(spinner:Spinner){
        var sistemUnits : Array<String> = resources.getStringArray(R.array.units)
        var adapter : ArrayAdapter<String> = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, sistemUnits)
        spinner.setAdapter(adapter)
    }



    /**
     * funcion que permite convertir una unidad de medida a otra
     */
    private fun convertUnits(spEntry:Spinner,spExit:Spinner, etEntry: EditText):Double{
        var exitValue : Double=0.0
        var entryValue:Double=findValue(etEntry)
        exitValue = convertUnitToM2(spEntry,entryValue)
        exitValue = convertM2toUnit(spExit,exitValue)
        return exitValue
    }

    /**
     * función que convierte el double entrado a m2
     */
    private fun convertUnitToM2(spEntry:Spinner, value:Double):Double{
        var entry:String=findUnit(spEntry)
        var squareMeters: Double = 0.0
        when(entry){
            "Kilómetro cuadrado"->squareMeters=value*1000000
            "Metro cuadrado"->squareMeters=value
            "Milla cuadrada"->squareMeters=value*2590000
            "Yarda cuadrada"->squareMeters=value/1.196
            "Pie cuadrado"->squareMeters=value/10.764
            "Pulgada cuadrada"->squareMeters=value/1500
            "Hectárea"->squareMeters=value*10000
            "Acre"->squareMeters=value*4047
        }
        return squareMeters
    }

    /**
     * funcion que pasa de m2 a la unidad seleccionada
     */
    private fun convertM2toUnit(spExit:Spinner,value: Double):Double{
        var exit:String=findUnit(spExit)
        var result: Double = 0.0
        when(exit){
            "Kilómetro cuadrado"->result=value/1000000
            "Metro cuadrado"->result=value
            "Milla cuadrada"->result=value/2590000
            "Yarda cuadrada"->result=value*1.196
            "Pie cuadrado"->result=value*10.764
            "Pulgada cuadrada"->result=value*1500
            "Hectárea"->result=value/10000
            "Acre"->result=value/4047
        }
        return result
    }

    /**
     * funcion que busca la unidad seleccionada en un spinner
     */
    private fun findUnit(spinner: Spinner):String{
        var unit:String
        unit=spinner.selectedItem.toString()
        return unit
    }

    /**
     * funcion que busca el valor introducido por el usuario
     */
    private fun findValue(etEntry:EditText):Double{
        var value:Double
        var valueString:String
        valueString=etEntry.text.toString()
        value=valueString.toDouble()
        return value
    }
}