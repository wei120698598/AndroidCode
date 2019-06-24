package com.wei.sample.recyclerview

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.wei.sample.R
import com.wei.sample.rxbus.RxBus2
import kotlinx.android.synthetic.main.activity_recyclerview.*
import kotlinx.android.synthetic.main.adapter_item_recyclerview.view.*

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description
 * @date 2019/4/9
 * @email weishuxin@icourt.cc
 */
open class RecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)

        supportActionBar!!.title = "RecyclerView"
        initView()

        RxBus2.getInstance().post("hahahahha")

    }

    open fun initView() {
        add.setOnClickListener {
            val data = ArrayList<User>()
            for (i in 42..100) {
                data.add(User("name $i", i))
            }
            val calculateDiff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return recyclerView.adapter?.itemCount ?: 0
                }

                override fun getNewListSize(): Int {
                    return data.size
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return data[newItemPosition] == (recyclerView.adapter as RecyclerViewAdapter).data[oldItemPosition]
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return data[newItemPosition].name === (recyclerView.adapter as RecyclerViewAdapter).data[oldItemPosition].name
                }

            })
            (recyclerView.adapter as RecyclerViewAdapter).data = data
            calculateDiff.dispatchUpdatesTo(recyclerView.adapter!!)
        }

        recyclerView.initRecyclerView()
    }
}

class RecyclerViewAdapter : RecyclerView.Adapter<ViewHolder>() {
    var data: List<User> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val contentView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_item_recyclerview, parent, false)
        return object : ViewHolder(contentView) {}
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tv_text.text = data[position].toString()
        holder.itemView.setOnClickListener { v -> Toast.makeText(v.context, "点击：$position", Toast.LENGTH_SHORT).show() }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

fun RecyclerView.initRecyclerView() {
    this.layoutManager = GridLayoutManager(this.context, 2, RecyclerView.VERTICAL, false)
    val verticalItemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
    val horizontalItemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.HORIZONTAL)
    verticalItemDecoration.setDrawable(this.context.resources.getDrawable(android.R.drawable.divider_horizontal_textfield))
    horizontalItemDecoration.setDrawable(this.context.resources.getDrawable(android.R.drawable.divider_horizontal_textfield))
    this.addItemDecoration(verticalItemDecoration)
    this.addItemDecoration(horizontalItemDecoration)
    val adapter = RecyclerViewAdapter()
    val data = ArrayList<User>()
    for (i in 0..59) {
        data.add(User("name $i", i))
    }
    adapter.data = data
    this.adapter = adapter
    adapter.notifyDataSetChanged()
}

data class User(var name: String, var age: Int) {
    override fun toString(): String {
        return "$name : $age"
    }
}