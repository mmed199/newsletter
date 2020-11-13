package com.mbds.newsletter.utils

import java.util.*

class Countries {
    private var  ISONumbers : Array<String>
    private var  countriesNames: Array<String?>

    init {
        ISONumbers = Locale.getISOCountries();
        countriesNames = arrayOfNulls<String>(ISONumbers.size)

        for (i in 0..(ISONumbers.size-1)) {
            countriesNames[i] = Locale("", ISONumbers[i]).getDisplayCountry()
        }
    }

    val names get() = countriesNames!!

    fun  getISOCode(index: Int) : String? {
        if(index >= ISONumbers.size)
            return null

        return ISONumbers[index]
    }
}