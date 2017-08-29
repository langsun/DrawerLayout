package cn.ablecloud.drawlayouttest;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;

/**
 * ToolBar自定义图标，关联DrawerLayout
 * http://blog.csdn.net/static_zh/article/details/52621240
 */
public class MainActivity extends AppCompatActivity {

    private CoordinatorLayout right;
    private NavigationView left;
    private LinearLayout leftView;
    private boolean isDrawer = false;

    private TextView logout;

    private ImageView leftImageView;
    private TextView CenterTitle;

    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CenterTitle = (TextView) LayoutInflater.from(this).inflate(R.layout.toolbar_center_title, toolbar, false);
        toolbar.addView(CenterTitle);
        CenterTitle.setText("sfasdfasd");
        CenterTitle.setTextColor(Color.RED);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_launcher);
//        toolbar.setNavigationOznClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"mmmm",Toast.LENGTH_SHORT).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        StatusBarUtil.setColorForDrawerLayout(MainActivity.this, drawer, getResources().getColor(R.color.colorAccent));
        right = (CoordinatorLayout) findViewById(R.id.right);
        leftView = (LinearLayout) findViewById(R.id.left_view);
        logout = (TextView) findViewById(R.id.tv_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "yes", Toast.LENGTH_SHORT).show();
            }
        });
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, 0, 0);
        drawer.setDrawerListener(toggle);
//        toggle.setDrawerIndicatorEnabled(false);
//        toggle.syncState();


        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (isDrawer) {
                    return leftView.dispatchTouchEvent(motionEvent);
                } else {
                    return false;
                }
            }
        });
        drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                isDrawer = true;
                //获取屏幕的宽高
                WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                //设置右面的布局位置  根据左面菜单的right作为右面布局的left   左面的right+屏幕的宽度（或者right的宽度这里是相等的）为右面布局的right
                int l = leftView.getRight();
                int r = leftView.getRight() + display.getWidth();
                right.layout(l, 0, r, display.getHeight());
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                isDrawer = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        toggle.onOptionsItemSelected(item);
//        return super.onOptionsItemSelected(item);
//    }
}
