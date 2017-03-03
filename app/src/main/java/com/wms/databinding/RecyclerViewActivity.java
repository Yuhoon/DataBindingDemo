package com.wms.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.github.markzhai.recyclerview.BaseViewAdapter;
import com.github.markzhai.recyclerview.BindingViewHolder;
import com.github.markzhai.recyclerview.MultiTypeAdapter;
import com.github.markzhai.recyclerview.SingleTypeAdapter;
import com.wms.databinding.databinding.ActivityRecyclerViewBinding;
import com.wms.databinding.databinding.ItemMultiTitleBinding;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private MultiTypeAdapter multiTypeAdapter;
    private SingleTypeAdapter singleTypeAdapter;

    ActivityRecyclerViewBinding binding;

    private final int TITLE_TYPE = 0;
    private final int ITEM_TYPE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_recycler_view);

        loadSingleRv();
        loadMultiRv();
    }

    private void loadSingleRv() {
        singleTypeAdapter = new SingleTypeAdapter(this, R.layout.item_rv);
        //设置适配器监听
        singleTypeAdapter.setPresenter(new Presenter());
        singleTypeAdapter.set(getData());
        binding.rvSingle.setLayoutManager(new LinearLayoutManager(this));
        binding.rvSingle.setAdapter(singleTypeAdapter);
    }

    private void loadMultiRv() {
        multiTypeAdapter = new MultiTypeAdapter(this);
        multiTypeAdapter.addViewTypeToLayoutMap(TITLE_TYPE, R.layout.item_multi_title);
        multiTypeAdapter.addViewTypeToLayoutMap(ITEM_TYPE, R.layout.item_rv);
        //设置适配器监听
        multiTypeAdapter.setPresenter(new Presenter());
        //设置装饰器
        multiTypeAdapter.setDecorator(new TitleDecorator());
        multiTypeAdapter.add(null, TITLE_TYPE);
        multiTypeAdapter.addAll(getData(), ITEM_TYPE);
        binding.rvMulti.setLayoutManager(new LinearLayoutManager(this));
        binding.rvMulti.setAdapter(multiTypeAdapter);
    }

    private List<Student> getData() {
        List<Student> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            list.add(new Student(i % 2 == 0 ? "张三" : "李四", String.valueOf(i + 10)));
        }
        return list;
    }

    public class Presenter implements BaseViewAdapter.Presenter {

        public void onItemClick(Student item) {
            Toast.makeText(RecyclerViewActivity.this, item.getName() + "---" + item.getAge(), Toast.LENGTH_SHORT).show();
        }

        public void onBtnTitle(String msg) {
            Toast.makeText(RecyclerViewActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public class TitleDecorator implements BaseViewAdapter.Decorator {

        @Override
        public void decorator(BindingViewHolder holder, int position, int viewType) {
            if (TITLE_TYPE == viewType) {
                ItemMultiTitleBinding titleBinding = (ItemMultiTitleBinding) holder.getBinding();
                titleBinding.title.setText("This is multi title");
            }
        }
    }
}
