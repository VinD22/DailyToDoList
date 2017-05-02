package list.complete.to.daily.app.v.dailytodolist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;
import model.Task;

/**
 * Activity to add a new task!
 */

public class AddTask extends AppCompatActivity {

    private Toolbar mToolbar;
    private Button mAddTask;
    private EditText mTaskName, mTaskDescription;

    Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);

        realm = Realm.getDefaultInstance();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mAddTask = (Button) findViewById(R.id.add_task);
        mTaskName = (EditText) findViewById(R.id.task_name);
        mTaskDescription = (EditText) findViewById(R.id.task_description);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mToolbar.setTitle(R.string.add_task);
            getSupportActionBar().setTitle(R.string.add_task);
            // getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String taskName = mTaskName.getText().toString();
                String taskDescription = mTaskDescription.getText().toString();

                if(taskName.isEmpty() || taskDescription.isEmpty()) {
                    Toast.makeText(AddTask.this, R.string.empty_fields, Toast.LENGTH_SHORT).show();
                } else {

                    // Save to Realm and go to Main Activity!

                    realm.beginTransaction();

                    Task newTask = realm.createObject(Task.class);
                    int nextKey = getNextKey();
                    newTask.setId(nextKey);
                    newTask.setTask(capitalizeFirstLetter(taskName));
                    newTask.setTaskDescription(taskDescription);
                    newTask.setDone(false);

                    realm.commitTransaction();

                    Toast.makeText(AddTask.this, R.string.add_task + " :D ", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(AddTask.this, MainActivity.class);
//                    startActivity(intent);
                    finish();

                }

            }
        });

    }

    public String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    public int getNextKey()
    {
        try {
            return realm.where(Task.class).max("id").intValue() + 1;
        } catch (ArrayIndexOutOfBoundsException e)
        { return 0; }
    }


}
