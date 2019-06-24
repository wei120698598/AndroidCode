package com.wei.sample.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import com.wei.sample.R
import kotlinx.android.synthetic.main.activity_recyclerview.*
import kotlinx.android.synthetic.main.adapter_item_recyclerview.view.*

class AsyncListUtilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)

        supportActionBar!!.title = "RecyclerView"
        initView()
    }

    private fun initView() {
        val adapter = AsyncAdapter()


        recyclerView?.apply {


            this.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
            val horizontalItemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.HORIZONTAL)
            horizontalItemDecoration.setDrawable(this.context.resources.getDrawable(android.R.drawable.divider_horizontal_textfield))
            this.addItemDecoration(horizontalItemDecoration)
            this.adapter = adapter

            addOnScrollListener(ScrollListener(adapter.listUtil))

        }

        add.setOnClickListener {
            adapter.listUtil.refresh()
        }
    }
}

class AsyncAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var listUtil: AsyncListUtil<User>

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        var name: String? = null
        //第一个参数：实体类
        //第二个参数：分页大小
        //第三个参数：数据处理回调
        //第四个参数：View处理回调
        listUtil = object : AsyncListUtil<User>(User::class.java, 10, object : AsyncListUtil.DataCallback<User>() {
            //刷新数据，返回新的数据个数，也就是
            override fun refreshData(): Int {
                name = if (name === null) {
                    "name1"
                } else {
                    "name2"
                }
                return Int.MAX_VALUE
            }

            //加载数据，是在异步线程中
            override fun fillData(data: Array<User>, startPosition: Int, itemCount: Int) {
                for (i in 0 until itemCount) {
                    data[i] = User("$name ${i + startPosition}", i + startPosition)
                    Thread.sleep(100)
                }
            }

            //RecyclerView Recycle时会回调，可以在这里面回收大的资源，比如Bitmap
            override fun recycleData(data: Array<out User>, itemCount: Int) {
                super.recycleData(data, itemCount)
                data.forEach {
                    it.name = "假装在回收资源"
                }
            }

            //配置最大缓存个数
            override fun getMaxCachedTiles(): Int {
                return 20
            }
        }, object : AsyncListUtil.ViewCallback() {
            //数据加载完成后会回调这个方法
            override fun onDataRefresh() {
                recyclerView.adapter?.notifyDataSetChanged()
            }
            // 用来计算平滑滚动所需要加载的额外数据，一般不用实现这个函数，默认实现即可。
            override fun extendRangeInto(range: IntArray, outRange: IntArray, scrollHint: Int) {
                super.extendRangeInto(range, outRange, scrollHint)
            }

            //配置可见页面大小
            // outRange[0] is the position of the first visible item (in the order of the backing storage).
            // outRange[1] is the position of the last visible item (in the order of the backing
            override fun getItemRangeInto(outRange: IntArray) {
                (recyclerView.layoutManager as LinearLayoutManager).let { llm ->
                    outRange[0] = llm.findFirstVisibleItemPosition()
                    outRange[1] = llm.findLastVisibleItemPosition()
                }

                if (outRange[0] == -1 && outRange[1] == -1) {
                    outRange[0] = 0
                    outRange[1] = 0
                }
            }

            //单个数据更新完成会回调
            override fun onItemLoaded(position: Int) {
                recyclerView.adapter?.notifyItemChanged(position)
            }
        }) {
            override fun getItem(position: Int): User? {
                return super.getItem(position)
            }

            override fun getItemCount(): Int {
                return super.getItemCount()
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val contentView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_item_recyclerview, parent, false)
        return object : RecyclerView.ViewHolder(contentView) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //绑定数据直接从listUtil中取
        holder.itemView.tv_text.text = listUtil.getItem(position)?.toString()
        holder.itemView.setOnClickListener { v -> Toast.makeText(v.context, "点击：$position", Toast.LENGTH_SHORT).show() }
    }

    override fun getItemCount(): Int {
        return listUtil.itemCount
    }
}

private class ScrollListener(val listUtil: AsyncListUtil<in User>) : RecyclerView.OnScrollListener() {
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        //滚动的时候通知加载数据
        listUtil.onRangeChanged()
    }
}