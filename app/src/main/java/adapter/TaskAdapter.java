package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import list.complete.to.daily.app.v.dailytodolist.R;
import model.Task;

/**
 * Adapter class to handle each indiviudal tasks!
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.RecyclerViewHolder> {

    private List<Task> data;
    private Context mContext;

    public TaskAdapter(Context context, ArrayList<Task> data) {
        this.mContext = context;
        this.data = data;
        // setHasStableIds(true);
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

        viewHolder.mTaskName.setText(capitalizeFirstLetter(tempTask.getTask()));

    }

    public String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
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

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            placeHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
            mTaskName = (TextView) itemView.findViewById(R.id.category_name);
        }

    }


}