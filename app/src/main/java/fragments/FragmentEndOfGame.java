package fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quick_quiz_v20.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import database.User;

public class FragmentEndOfGame extends Fragment {

    private TextView textViewGameResult;
    private ImageView imageView;
    private EditText editUserName;
    private Button buttonSaveAndClose;
    private DatabaseReference mDatabase;
    private final String USER_KEY = "User";
    private Fragment fr;
    private static int counter;

    public FragmentEndOfGame(DatabaseReference mDatabase, int counter) {
        this.mDatabase = mDatabase;
        this.counter = counter;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_end_of_game, null);
        fr = this;
        init(view);
        onClickButtonSaveAndClose(view);
        return view;
    }

    private void init(View view) {
        textViewGameResult = (TextView) view.findViewById(R.id.idTextViewGameResult);
        editUserName = (EditText) view.findViewById(R.id.idEditUserName);
        buttonSaveAndClose = (Button) view.findViewById(R.id.idButtonSaveAndClose);
        imageView = (ImageView) view.findViewById(R.id.idImageView);
        mDatabase = FirebaseDatabase.getInstance().getReference(USER_KEY);

        fillTextView();
    }

    void fillTextView() {
        textViewGameResult.append("Поздравляю!\nТвой результат: ");
        textViewGameResult.append(Integer.toString(counter));
        textViewGameResult.append(" из 10!");
       // imageView.setImageResource(R.drawable.megabrain);
    }

    private void onClickButtonSaveAndClose(View view) {

        buttonSaveAndClose.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (saveUser())
                    getActivity().getSupportFragmentManager().beginTransaction().
                            remove(fr).commit();
            }
        });
    }

    private boolean saveUser() {
        String id = mDatabase.getKey();
        String name = editUserName.getText().toString();
        String score = Integer.toString(counter);
        User newUser = new User(id, name, score);

        // if a saving has gone successfully, i return TRUE and close the fragment
        return invokeSaveNotificationSuccessfully(name, newUser);
    }

    private boolean invokeSaveNotificationSuccessfully(String name, User newUser) {
        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(getContext(), "Поле пустое!", Toast.LENGTH_SHORT).show(); // create notification EMPTY_SIZE or
            return false;
        }
        else
        {
            mDatabase.push().setValue(newUser);                                     // push user to the database
            Toast.makeText(getContext(), "Сохранено", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
