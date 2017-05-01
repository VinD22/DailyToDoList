package list.complete.to.daily.app.v.dailytodolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

import adapter.TaskAdapter;
import io.realm.Realm;
import model.Task;

public class MainActivity extends AppCompatActivity {


    private FirebaseAnalytics mFirebaseAnalytics;

    Realm realm;

    ArrayList<Task> listOfTasks = new ArrayList<>();


    private Toolbar mToolbar;
    private RecyclerView mTaskListRecyclerview;
    private TaskAdapter mAdapter;
    private FloatingActionButton mAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTaskListRecyclerview = (RecyclerView) findViewById(R.id.task_list_recyclerview);
        mAddTask = (FloatingActionButton) findViewById(R.id.fab_add_task);


        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mToolbar.setTitle(R.string.app_name);
            mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
            // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Home screen - 1");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Home screen - 2");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Main Activity");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


        final LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mTaskListRecyclerview.setLayoutManager(layoutManager);
        mTaskListRecyclerview.setHasFixedSize(true);

        mTaskListRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0)
                    mAddTask.hide();
                else if (dy < 0)
                    mAddTask.show();
            }
        });

        mAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AddTask.class);
                startActivity(intent);

            }
        });



    }
}
