package com.example.recyclerveiwdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.recyclerveiwdemo.adapter.MyAdapter;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //listView的形式展示
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //gridview的形式展示,可以通过setSpanSizeLookup 来自定义每个item占的列数
        gridLayoutManager = new GridLayoutManager(this, 3);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return 3 - position % 3;
//            }
//        });

        //瀑布流的形式
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);

        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                //增加和删除的话 不直接对postion做处理,因为此处的position是没有刷新以前的position
//                myAdapter.add(position);
                myAdapter.selectedAll();
            }
        });

        myAdapter.setOnItemLongClickListener(new MyAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, int position) {

                //remove的操作
                // myAdapter.remove(position);

                //更新的操作
//                myAdapter.updata(position, "这是我更新的内容");

                //去反选
                myAdapter.revertSelected();
            }
        });
        recyclerView.addItemDecoration(new MyDecoration(this, LinearLayoutManager.VERTICAL));
    }


    //切换页面布局
    public void onClick(View v) {

        //切换布局
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager == null) {
            return;
        }
        //if和elseif是有先后顺序的,先判断范围小的,然后再判断范围大的,
        //因为GridLayoutManager 是继承 LinearLayoutManager ,所以他本质上也是LinearLayoutManager,
        //所以不能先判断是否是LinearLayoutManager (LinearLayoutManager范围大)
        if (layoutManager instanceof GridLayoutManager) {
            recyclerView.setLayoutManager(linearLayoutManager);

            //重新设置adapter之后 ,会重新走oncreateViewholder可以改变我们的布局
            recyclerView.setAdapter(myAdapter);
        } else if (layoutManager instanceof LinearLayoutManager) {
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(myAdapter);
        }
    }
}
