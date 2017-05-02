package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import list.complete.to.daily.app.v.dailytodolist.R;
import list.complete.to.daily.app.v.dailytodolist.ViewTask;
import model.Task;

/**
 * Adapter class to handle each indiviudal tasks!
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.RecyclerViewHolder> {

    private List<Task> data;
    private Context mContext;
    Realm realm;

    public TaskAdapter(Context context, ArrayList<Task> data) {
        this.mContext = context;
        this.data = data;
        // setHasStableIds(true);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.task_list_item, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder viewHolder, final int position) {

        final Task tempTask = data.get(viewHolder.getAdapterPosition());

        viewHolder.mTaskName.setText(tempTask.getTask());

        viewHolder.mCheckBox.setChecked(tempTask.isDone());

        viewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                Task toEdit = realm.where(Task.class).equalTo("id", tempTask.getId()).findFirst();
                realm.beginTransaction();
                toEdit.setDone(b);
                realm.commitTransaction();

            }
        });

        viewHolder.mTaskName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, ViewTask.class);
                intent.putExtra("id", tempTask.getId());
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

//    @Override
//    public long getItemId(int position) {
//        return data.get(position).getProductInsertNumber();
//    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout placeHolder;
        protected TextView mTaskName;
        protected CheckBox mCheckBox;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            placeHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
            mTaskName = (TextView) itemView.findViewById(R.id.task_name);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }

    }


}