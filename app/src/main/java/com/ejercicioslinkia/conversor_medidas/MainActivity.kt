package com.ejercicioslinkia.conversor_medidas

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.core.widget.addTextChangedListener

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

        //hacemos que el boton este desactivado hasta que se introduzca un numero
        btConvert.isEnabled=false

        //damos funcionalidad el editext para controlarlo
        etNumberEntry.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                btConvert.isEnabled = checkValue(etNumberEntry)
            }
        })

        //damos funcionalidad al boton
        btConvert.setOnClickListener {
            var result:Double=0.0
            var units:String

            result=convertUnits(spEntry,spExit,etNumberEntry)

            units = findUnit(spExit)
            tvResult.text = "Total: ${result.toString()} $units"
        }

    }


    /**
     * funcion que rellena la informacion de los spinners
     *
     * @param spinner
     */
    private fun fillSpiner(spinner:Spinner){
        var sistemUnits : Array<String> = resources.getStringArray(R.array.units)
        var adapter : ArrayAdapter<String> = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, sistemUnits)
        spinner.setAdapter(adapter)
    }

    /**
     * funcion que permite convertir una unidad de medida a otra
     *
     * @param spEntry
     * @param spExit
     * @param etEntry
     * @return Double con el resultado de la conversion
     */
    private fun convertUnits(spEntry:Spinner,spExit:Spinner, etEntry: EditText):Double{
        var exitValue : Double=0.0
        var entryValue:Double=findValue(etEntry)

        exitValue = convertUnitToM2(spEntry, entryValue)
        exitValue = convertM2toUnit(spExit, exitValue)
        return exitValue
    }

    /**
     * Funcion que pasa la unidad seleccionada a metros quadrados
     *
     * @param spEntry
     * @param value
     * @return Double con los metros cuadrados
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
     * funcion quepasa los metros cuadrados a la unidad elegida
     *
     * @param spExit
     * @param value
     * @return el valor de los metros cuadros a la unidad seleccionada
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
     * Funcion que busca el texto seleccionado en un spinner al momento de invocarla
     *
     * @param spinner
     * @return String con la seleccion del spinner
     */
    private fun findUnit(spinner: Spinner):String{
        var unit:String
        unit=spinner.selectedItem.toString()
        return unit
    }


    /**
     * Funcion que busca el valor introducido por el usuario
     *
     * @param etEntry
     * @return DOuble con el importe introducido
     */
    private fun findValue(etEntry:EditText):Double{
        var value: Double
        var valueString: String
        valueString = etEntry.text.toString()
        value = valueString.toDouble()
        return value
    }

    /**
     * funcion que controla que se inserte un valor a un editText
     *
     * @param etEntry
     * @return
     */
    private fun checkValue(etEntry: EditText):Boolean{
        var entrada:String=etEntry.text.toString()
        return !entrada.isNullOrEmpty()
    }
}