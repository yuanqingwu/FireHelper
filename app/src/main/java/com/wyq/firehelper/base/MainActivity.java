package com.wyq.firehelper.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.architecture.mvp.translation.view.MvpActivity;
import com.wyq.firehelper.article.ArticleMainActivity;
import com.wyq.firehelper.connectivity.ConnectMainActivity;
import com.wyq.firehelper.developKit.DevelopKitMainActivity;
import com.wyq.firehelper.encryption.EncryptActivity;
import com.wyq.firehelper.kotlin.mvpGitHub.view.GitHubMainActivity;
import com.wyq.firehelper.ui.UiMainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Uni.W on 2016/8/10.
 */
public class MainActivity extends BaseActivity {

    private String[] items = {"Article", "Communication", "UI", "Encryption", "DevelopKit", "Architecture", "kotlin"};

    @BindView(R.id.activity_main_list)
    public ListView listView;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        initView();
    }

    public void initView() {
        AppCompatDelegate.setDefaultNightMode(NightThemeConfig.getInstance(this).getNightMode());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.this, android.R.layout.simple_list_item_1,
                items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(MainActivity.this,
                                ArticleMainActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this,
                                ConnectMainActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this,
                                UiMainActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this,
                                EncryptActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this,
                                DevelopKitMainActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(MainActivity.this,
                                MvpActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(MainActivity.this,
                                GitHubMainActivity.class));
                        break;
                    default:
                        break;

                }

            }
        });

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
                menu.findItem(R.id.menu_night_mode_system).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_AUTO:
                menu.findItem(R.id.menu_night_mode_auto).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                menu.findItem(R.id.menu_night_mode_night).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_NO:
                menu.findItem(R.id.menu_night_mode_day).setChecked(true);
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_night_mode_system:
                setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case R.id.menu_night_mode_day:
                setNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case R.id.menu_night_mode_night:
                setNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case R.id.menu_night_mode_auto:
                setNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * AppCompatDelegate.setDefaultNightMode(int mode);只作用于新生成的组件，对原先处于任务栈中的Activity不起作用。如果直接在Activity的onCreate()中调用这句代码，那当前的Activity中直接生效，不需要再调用recreate(),但我们通常是在监听器中调用这句代码，那就需要调用recreate()，否则对当前Activity无效(新生成的Activity还是生效的)。当然可以提前在onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState)中保存好相关属性值,重建时使用。
     *
     * @param nightMode
     */
    private void setNightMode(@AppCompatDelegate.NightMode int nightMode) {
        AppCompatDelegate.setDefaultNightMode(nightMode);
        NightThemeConfig.getInstance(this).setNightMode(nightMode);
        if (Build.VERSION.SDK_INT >= 11) {
            recreate();
        }
    }
}
