package fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quick_quiz_v20.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import database.User;
import recycler.RecyclerViewAdapter;

public class FragmentLeaders extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    private DatabaseReference mDatabase;
    private final String USER_KEY = "User";
    ArrayList<User> users = new ArrayList<>();

    public FragmentLeaders(DatabaseReference mDatabase){
        this.mDatabase = mDatabase;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaders, null);
        mDatabase = FirebaseDatabase.getInstance().getReference(USER_KEY);

        createRecyclerView(view);
        return view;
    }

    private void createRecyclerView(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.idRecyclerView);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext()); // create a manager
        recyclerView.setLayoutManager(linearLayout); // apply the manager to the recyclerView

        getDataFromDB();


    }


    private void getDataFromDB()
    {
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    User user = ds.getValue(User.class);
                    users.add(user);
                }
                recyclerViewAdapter = new RecyclerViewAdapter(users);    // create adapter
                recyclerView.setAdapter(recyclerViewAdapter); // apply the adapter to the recyclerView
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(vListener);
    }

}
