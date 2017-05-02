package list.complete.to.daily.app.v.dailytodolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import model.Task;

/**
 * View the task and mark as done!
 * Also Ability to delete the task!
 */

public class ViewTask extends AppCompatActivity {

    Realm realm;

    private Button mDeleteTask, mMarkTaskAsDone;
    private TextView mTaskName, mTaskDescription;

    int taskId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_task);

        realm = Realm.getDefaultInstance();

        mDeleteTask = (Button) findViewById(R.id.delete_task);
        mMarkTaskAsDone = (Button) findViewById(R.id.mark_task_as_done);

        mTaskName = (TextView) findViewById(R.id.task_name);
        mTaskDescription = (TextView) findViewById(R.id.task_description);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Toast.makeText(this, "Error in intents! (Contact Developer)", Toast.LENGTH_SHORT).show();
        } else {
            taskId = extras.getInt("id");

            Task tempTask = realm.where(Task.class).equalTo("id", taskId).findFirst();
            realm.beginTransaction();

            mTaskName.setText(tempTask.getTask());
            mTaskDescription.setText(tempTask.getTaskDescription());

            realm.commitTransaction();

        }

        mDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                realm.beginTransaction();
                Task task = realm.where(Task.class).equalTo("id", taskId).findFirst();
                task.deleteFromRealm();
                realm.commitTransaction();

                goToMainActivity();

            }
        });


        mMarkTaskAsDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Task toEdit = realm.where(Task.class).equalTo("id", taskId).findFirst();
                realm.beginTransaction();
                toEdit.setDone(true);
                realm.commitTransaction();

                goToMainActivity();

            }
        });


    }

    private void goToMainActivity() {

        Intent intent = new Intent(ViewTask.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }
}
