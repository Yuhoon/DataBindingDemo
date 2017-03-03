package com.wms.databinding;

import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wms.databinding.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bingding初始化
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.setPresenter(new Presenter());

        //设置参数
        binding.setBean(new Student("张三", "23"));

//        DataBindingUtil.setDefaultComponent(new Util());
    }

    @BindingAdapter("imgUrl")
    public static void loadImg(ImageView view,String url) {
        Glide.with(view.getContext()).load(url).into(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public class Presenter {
        public void onShow1(View view) {
            Toast.makeText(MainActivity.this, "show1", Toast.LENGTH_LONG).show();
        }

        public void onShow2() {
            Toast.makeText(MainActivity.this, "show2", Toast.LENGTH_LONG).show();
        }

        public void onShow3(View view) {
            Toast.makeText(MainActivity.this, "show3", Toast.LENGTH_LONG).show();
            view.setVisibility(View.GONE);
        }

        public void onShow4(final Student student, String msg) {
            Toast.makeText(MainActivity.this, student.getName() + "--" + msg, Toast.LENGTH_LONG).show();
        }

        public void onRecyclerView() {
            startActivity(new Intent(MainActivity.this,RecyclerViewActivity.class));
        }
    }

}
