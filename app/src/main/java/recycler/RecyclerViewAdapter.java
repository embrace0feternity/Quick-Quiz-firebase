package recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quick_quiz_v20.R;

import java.util.ArrayList;
import java.util.List;

import database.User;

public class RecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<User> users;

    public RecyclerViewAdapter(ArrayList<User> users){
        this.users = users;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext(); // context need for creating a new ViewHolder

        // LayoutInflater creates a viewHolder's XML.
        // LayoutInflater is used only in ViewGroup to create internal views
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.fragment_leaders_view_holder, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName, textViewScore;

        public ViewHolder(@NonNull View itemView) {     //Viewholder constr
            super(itemView);
            init(itemView);     // initialization method
        }

        private void init(View itemView) {
            textViewName = (TextView) itemView.findViewById(R.id.idTextViewName);
            textViewScore = (TextView) itemView.findViewById(R.id.idTextViewScore);
        }

        public void bind(User user){
            textViewName.setText(user.getName());
            textViewScore.setText(user.getScore());
        }
    }
}
