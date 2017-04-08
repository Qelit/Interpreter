package qelit.interpreter;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public final String URL = "https://translate.yandex.net";
    public final String KEY = "trnsl.1.1.20170407T125506Z.eb24387a1c539749.2a88d693d89c238fe52d8d45fc691416518c1bb9";

    TranslateFragment translateFragment = new TranslateFragment();
    private TextView tvTranslate;
    private EditText etInputText;
    private Gson gson;
    private Retrofit retrofit;
    private YandexTranslateService service;
    private Map<String, String> mapJson, mapGetResponse;
    ArrayList<String> text;
    List<TranslateData> data;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       //инициализация
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);


        gson = new GsonBuilder().create();
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(URL)
                .build();
        service = retrofit.create(YandexTranslateService.class);
    }
        //вкладки tablayout
    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TranslateFragment(), "Переводчик");
        adapter.addFragment(new TranslateFragment(), "История");
        adapter.addFragment(new TranslateFragment(), "Избранное");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // делаем меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.history:
                break;
            case R.id.settings:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickTranslate(View v){
        tvTranslate = (TextView) findViewById(R.id.tvTranslate);
        etInputText = (EditText) findViewById(R.id.etInputText);

        mapJson = new HashMap<>();
        mapJson.put("key", KEY);
        mapJson.put("text", etInputText.getText().toString());
        mapJson.put("lang", "en-ru");

        service.translate(mapJson).enqueue(new Callback<TranslateData>() {
            @Override
            public void onResponse(Call<TranslateData> call, Response<TranslateData> response) {
                TranslateData data = response.body();
                text = data.getText();
                tvTranslate.setText(text.get(text.size()-1));
            }

            @Override
            public void onFailure(Call<TranslateData> call, Throwable t) {

            }
        });
    }
}
