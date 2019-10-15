package org.pursuit.wordtrivia.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import org.pursuit.wordtrivia.R

class AlphabetAdapter(context: Context) : BaseAdapter() {

    private val letterArray = arrayOf(
        "A",
        "B",
        "C",
        "D",
        "E",
        "F",
        "G",
        "H",
        "I",
        "K",
        "L",
        "M",
        "N",
        "O",
        "P",
        "Q",
        "R",
        "S",
        "T", "U", "V", "W", "X", "Y", "Z"


    )
    private val layoutInflater = LayoutInflater.from(context)
    var key: Button? = null

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        if (p1 != null) {
            p1 as Button

        } else {
            key = layoutInflater?.inflate(R.layout.button_layout, p2, false) as Button?
        }
        key?.text = letterArray[p0]
        return key as Button

    }

    override fun getItem(p0: Int): Any {
        return letterArray[p0]

    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return letterArray.size
    }
}