package list.complete.to.daily.app.v.dailytodolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

import adapter.TaskAdapter;
import io.realm.Realm;
import io.realm.RealmResults;
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

        mAdapter = new TaskAdapter(MainActivity.this, listOfTasks);
        mTaskListRecyclerview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

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


    @Override
    protected void onResume() {
        super.onResume();

        getTasksList();

    }

    private void getTasksList() {

        RealmResults<Task> categoryResults =
                realm.where(Task.class).findAll();

        listOfTasks.clear();
        for (Task c : categoryResults) {
            final Task tempTask = new Task();
            tempTask.setId(c.getId());
            tempTask.setTask(c.getTask());
            tempTask.setTaskDescription(c.getTaskDescription());
            tempTask.setDone(c.isDone());
            listOfTasks.add(tempTask);
        }

        // Toast.makeText(this, "Total Taks : " + listOfTasks.size(), Toast.LENGTH_SHORT).show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {

            case R.id.settings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
