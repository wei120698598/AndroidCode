package com.wei.sample.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.wei.sample.R
import kotlinx.android.synthetic.main.activity_recyclerview.*
import kotlinx.android.synthetic.main.adapter_item_recyclerview.view.*

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description
 * @date 2019/4/9
 * @email weishuxin@icourt.cc
 */
open class AsyncListDifferActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)

        supportActionBar!!.title = "RecyclerView"
        initView()
    }

    open fun initView() {
        add.setOnClickListener {
            //新的数据
            val data = ArrayList<User>()
            for (i in 42..100) {
                data.add(User("name $i", i))
            }

            (recyclerView.adapter as RecyclerViewAdapter2).submitList(data)
        }

        recyclerView.initRecyclerView()
    }

    fun RecyclerView.initRecyclerView() {
        this.layoutManager = GridLayoutManager(this.context, 2, RecyclerView.VERTICAL, false)
        val verticalItemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        val horizontalItemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.HORIZONTAL)
        verticalItemDecoration.setDrawable(this.context.resources.getDrawable(android.R.drawable.divider_horizontal_textfield))
        horizontalItemDecoration.setDrawable(this.context.resources.getDrawable(android.R.drawable.divider_horizontal_textfield))
        this.addItemDecoration(verticalItemDecoration)
        this.addItemDecoration(horizontalItemDecoration)
        val data = ArrayList<User>()
        for (i in 0..59) {
            data.add(User("name $i", i))
        }
        val viewAdapter2 = RecyclerViewAdapter2()
        this.adapter = viewAdapter2
        viewAdapter2.submitList(data)
    }
}


class RecyclerViewAdapter2(diffCallback: ItemCallback<User>) : ListAdapter<User, UserViewHolder>(diffCallback) {
    constructor() : this(object : ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.name == newItem.name
        }
    })


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_item_recyclerview, parent, false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.itemView.tv_text.text = getItem(position).toString()
        holder.itemView.setOnClickListener { v -> Toast.makeText(v.context, "点击：$position", Toast.LENGTH_SHORT).show() }
    }
}

class UserViewHolder(itemView: View) : ViewHolder(itemView)