package com.wei.sample

import android.app.ListActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import java.util.*


class MainActivity : ListActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val itemList = Arrays.asList("测试", "测试2")
        listAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemList)
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {

    }
}
