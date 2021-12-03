package fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quick_quiz_v20.MainActivity;
import com.example.quick_quiz_v20.R;

import java.util.List;
import java.util.Random;

import database.Questions;

public class FragmentLevel extends Fragment {

    private static int counter = 0;
    private FragmentLevel fragmentLevel;
    private Questions questions, storeQuestions;
    private TextView textViewQuestion;
    private int buttonsId[] = {
            R.id.idButtonTopLeft, R.id.idButtonBottomLeft, R.id.idButtonTopRight, R.id.idButtonBottomRight
    };
    private Button buttons[] = new Button[buttonsId.length];
    private Button whereIsRight;
    private List<String> temp;
    private String str;
    public FragmentLevel(Questions questions){
        this.questions = questions;
        storeQuestions = questions;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_level, null);
        init(view);

        onClickButtons(view);
        return view;
    }

    private void init(View view){
        textViewQuestion = (TextView) view.findViewById(R.id.idTextViewQuestions);
        textViewQuestion.setEnabled(false);
        for (int i=0; i<buttons.length; ++i)
        {
            buttons[i] = (Button) view.findViewById(buttonsId[i]);
        }
        fillTextAndButtons(view);
    }

    private void fillTextAndButtons(View view){
        Random r = new Random();
        int random = r.nextInt(questions.getQuestions().size());
        String test = questions.getQuestions().remove(random).toString();
        textViewQuestion.setText(test);

        temp = questions.getAnswers().remove(random);
        for (int i=0; i<buttons.length; ++i)
            buttons[i].setText(temp.get(i).toString());
        str = temp.get(4);


    }

    private boolean test(String s) {
        for (int j = 0; j < s.length(); j++) {
            boolean hasAllUpper = s.equals(s.toUpperCase());
            if (hasAllUpper)
                return true;
        }
        return false;
    }

    private void setRightButton(View view){
        for (int i=0; i<4; i++) {
            if (test(buttons[i].getText().toString())) {
                whereIsRight = (Button) view.findViewById(buttonsId[i]);
                whereIsRight = buttons[i];
                String test = whereIsRight.getText().toString();

            }
        }
    }

    public Questions getQuestions(){
        return questions;
    }

    public void refresh(){
        questions = storeQuestions;
    }
    public static int getCounter(){
        return counter;
    }

    private void onClickButtons(View view) {
        for (int i=0; i<buttons.length; ++i)
        {
            setRightButton(view);
            buttons[i].setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button temp = (Button) view;
                    if (whereIsRight == temp){
                        whereIsRight.setBackgroundColor(Color.parseColor("#69B65B"));
                        counter++;
                    }
                    else {
                        temp.setBackgroundColor(Color.parseColor("#DDE46060"));
                        whereIsRight.setBackgroundColor(Color.parseColor("#69B65B"));
                    }

                    for (int i=0; i<buttons.length; i++)
                        buttons[i].setEnabled(false);

                    textViewQuestion.setEnabled(true);
                }
            });
        }
        if (!textViewQuestion.isEnabled())
            onClickTextView();
    }
    private void onClickTextView() {
        textViewQuestion.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).createNextLvl();
            }
        });
    }

}
