package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.quick_quiz_v20.MainActivity;
import com.example.quick_quiz_v20.R;

public class FragmentRules extends Fragment {

    Button buttonClear;
    Fragment fr;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rules, null);
        fr = this;
        onClickClear(view);
        return view;
    }

    public void onClickClear(View view){

        buttonClear = (Button) view.findViewById(R.id.idButtonClear);
        buttonClear.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
               FragmentManager fm = getActivity().getSupportFragmentManager();
               fm.beginTransaction().remove(fr).commit();

            }
        });
    }
}
