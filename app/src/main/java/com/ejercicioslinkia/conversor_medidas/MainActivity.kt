package com.ejercicioslinkia.conversor_medidas

import android.icu.number.Notation
import android.icu.number.ScientificNotation
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Proyecto para M8. App para convertir el valor de medidas de superficie
 *
 * @author Martí Curto Vendrell
 *
 */
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
        fillSpinner(spEntry)
        fillSpinner(spExit)

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
                //si al cambiar el texto esta vacio, deshabilitamos el boton para evitar errores
                btConvert.isEnabled = checkValue(etNumberEntry)
            }
        })

        //damos funcionalidad al boton cuando se haga click
        btConvert.setOnClickListener {
            //convertimos las unidades
            val result:Double=findResult(spEntry,spExit,etNumberEntry)
            val convertedResult=changeResultTo2Decimals(result,spEntry,spExit)
            val units:String = findUnit(spExit)

            //creamos el texto con el resultado y lo hacemos visible
            tvResult.visibility= View.VISIBLE
            tvResult.text = "Total: $convertedResult $units"
        }
    }

    /**
     * funcion que rellena la informacion de los spinners
     *
     * @param spinner
     */
    private fun fillSpinner(spinner:Spinner){
        //obtenemos valores de Values/Strings y los pasamos a un adaptador que los envia al spinner
        val systemUnits : Array<String> = resources.getStringArray(R.array.units)
        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, systemUnits)
        spinner.adapter = adapter
    }

    /**
     * Funcion que obtiene el resultado de la converions de la unidades entradas
     *
     * @param spEntry
     * @param spExit
     * @param etEntry
     * @return double con el valor ya convertido segun las unidades entradas
     */
    private fun findResult(spEntry: Spinner, spExit: Spinner, etEntry: EditText): Double {
        //obtenemos la posicion de las unidades de entrada y salida
        val entryUnit: Int = spEntry.selectedItemPosition
        val exitUnit: Int = spExit.selectedItemPosition

        //obtenemos el valor del editext que entra el usuario
        val entryValue: Double = findValue(etEntry)

        //creamos un array con las conversiones de cada unidad de superficie a metros cuadrados
        val systemConversions: Array<Double> =
            arrayOf(1000000.0, 1.0, 2590000.0, 1.196, 10.764, 1500.0, 10000.0, 4047.0)

        //hacemos un factor de conversion para obtener el resultado en las unidades deseadas
        return (systemConversions[entryUnit] / systemConversions[exitUnit]) * entryValue
    }

    /**
     * funcion que redondea el resultado a 2 decimales
     *
     * @param entry
     * @param spEntry
     * @param spExit
     * @return string con el resultado redondeado
     */
    private fun changeResultTo2Decimals(entry:Double, spEntry: Spinner, spExit: Spinner):String{
        //si las 2 unidades son iguales no modificamos el numero resultado
        if(spEntry.selectedItemPosition==spExit.selectedItemPosition) {
            return entry.toString()
        //si el reusltado es mas grande que 0.01 redondeamos a 2 decimales
        }else if(entry>0.01) {
            val decimal: BigDecimal = BigDecimal(entry).setScale(2, RoundingMode.HALF_EVEN)
            return decimal.toString()
        }else{
        //en caso contrario el sistema ya actua con notacion exponencial
            return entry.toString()
        }
    }

    /**
     * Funcion que busca el texto seleccionado en un spinner al momento de invocarla
     *
     * @param spinner
     * @return String con la seleccion del spinner
     */
    private fun findUnit(spinner: Spinner): String {
        return spinner.selectedItem.toString()
    }

    /**
     * Funcion que busca el valor introducido por el usuario
     *
     * @param etEntry
     * @return DOuble con el importe introducido
     */
    private fun findValue(etEntry: EditText): Double {
        val valueString: String = etEntry.text.toString()
        return valueString.toDouble()
    }

    /**
     * funcion que controla que se inserte un valor a un editText
     *
     * @param etEntry
     * @return
     */
    private fun checkValue(etEntry: EditText):Boolean{
        val entryText:String=etEntry.text.toString()
        val entryNumber = entryText.toDouble()

        //comprobamos se entre un numero y que sea posistivo
        return if(entryNumber>=0) {
            entryText.isNotEmpty()
        }else{
            false
        }
    }
}