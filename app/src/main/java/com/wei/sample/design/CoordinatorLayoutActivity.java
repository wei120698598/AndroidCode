package com.wei.sample.design;

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description
 * @date 2019-04-18
 * @email weishuxin@icourt.cc
 */

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.wei.sample.R;
import com.wei.sample.recyclerview.RecyclerViewActivity;
import com.wei.sample.recyclerview.RecyclerViewActivityKt;

import java.net.Socket;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class CoordinatorLayoutActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);
        initView();
    }

    private void initView() {
//        Toolbar mToolbar =  findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });


//        ViewPager viewpager =  findViewById(R.id.viewpager);
//        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(), Arrays.asList(new Fragment1(), new Fragment2(), new Fragment3()), new String[]{"主页", "销售", "我的"});
//        viewpager.setAdapter(adapter);
//
//        TabLayout mTablayout =  findViewById(R.id.mTable);
//        mTablayout.setupWithViewPager(viewpager);

        Hashtable<Object, Object> objectObjectHashtable = new Hashtable<>();

        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
//        CollapsingToolbarLayout mCollapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_layout);
//        mCollapsingToolbarLayout.setTitle("CollapsingToolbarLayout");
        //通过CollapsingToolbarLayout修改字体颜色
//        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
//        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.GREEN);//设置收缩后Toolbar上字体的颜色
        RecyclerViewActivityKt.initRecyclerView(findViewById(R.id.recyclerView));

        Socket socket = new Socket();
    }

    public static class Fragment1 extends Fragment {

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.activity_recyclerview, null);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            RecyclerViewActivityKt.initRecyclerView(view.findViewById(R.id.recyclerView));
        }
    }

    public static class Fragment2 extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.activity_recyclerview, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            RecyclerViewActivityKt.initRecyclerView(view.findViewById(R.id.recyclerView));
        }
    }

    public static class Fragment3 extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.activity_main, null);
        }
    }


    public class MyAdapter extends FragmentPagerAdapter {
        private List<Fragment> list;
        private String[] title;

        public MyAdapter(FragmentManager fm, List<Fragment> list, String[] title) {
            super(fm);
            this.list = list;
            this.title = title;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }
    }


}
