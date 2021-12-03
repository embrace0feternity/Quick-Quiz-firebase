package com.example.quick_quiz_v20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import database.Questions;
import database.User;
import fragments.FragmentEndOfGame;
import fragments.FragmentLeaders;
import fragments.FragmentLevel;
import fragments.FragmentRules;
import recycler.RecyclerViewAdapter;

public class MainActivity extends AppCompatActivity{

    FragmentRules fragmentRules;
    FragmentLeaders fragmentLeaders;
    FragmentEndOfGame fragmentEndOfGame;
    FragmentLevel fragmentLevel;
    Button buttonStart, buttonRules, buttonLeaders;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        //writeQuestions();

    }
    public void init(){
        mDatabase = FirebaseDatabase.getInstance().getReference("QUESTIONS");

    }

    public void onButtonStartClick(View view) {
        buttonStart = (Button) view;
        getDataFromDB();
        buttonStart.setEnabled(false);
    }

    public void onButtonRulesClick(View view) {
        buttonRules = (Button) view;
        fragmentRules = new FragmentRules();
        getSupportFragmentManager().beginTransaction().
                replace(R.id.FragmentMainMenu, fragmentRules).commit();
    }

    public void onButtonLeadersClick(View view) {
        buttonLeaders = (Button) view;
        fragmentLeaders = new FragmentLeaders(mDatabase);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.FragmentMainMenu, fragmentLeaders).commit();
    }

    public void getDataFromDB(){
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Questions questions = null;
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    questions = ds.getValue(Questions.class);
                }
                fragmentLevel = new FragmentLevel(questions);

                getSupportFragmentManager().beginTransaction().
                        replace(R.id.FragmentMainMenu, fragmentLevel).commit();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(vListener);
    }

    public void createNextLvl(){
        if (fragmentLevel.getQuestions().getQuestions().size() != 0)
        {
            fragmentLevel = new FragmentLevel(fragmentLevel.getQuestions());
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.FragmentMainMenu, fragmentLevel).commit();
        }
        else
        {
            fragmentLevel.refresh();
            fragmentEndOfGame = new FragmentEndOfGame(mDatabase, fragmentLevel.getCounter());
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.FragmentMainMenu, fragmentEndOfGame).commit();
            buttonStart.setEnabled(true);
        }
    }
/*
    public void writeQuestions(){
        List<String> questionsList = new ArrayList<>();

        String mQuestions[] = {
                "Автор песни \"Я уеду в соляного\"",
                "Бывший фронтмен группы Suicide Silence",
                "Самый продаваемый альбом в мире",
                "Город расвета блюза, соула. Столица рок-н-ролла",
                "В каком жанре музыки прославился LXST CXNTURY",
                "Самая большая музыкальная группа в мире, которая имеет 1200+ участников",
                "Город, в котором поставили оперы \"Севильский цирюльник\" и \"Женитьба Фигаро\"",
                "Имя самого старого музыканта",
                "Король регги",
                "Действующий фронтмен группы If i were you"
        };
        for (int i=0; i<10; i++){
            questionsList.add(mQuestions[i]);
        }

        List<List<String>> answersList = new ArrayList<>();

        String mAswers[][] = {
                {"Игорь Скляр", "ВИТАЛИЙ ЦАЛЬ", "Оксимирон", "Владимир Кузьмин", "ВИТАЛИЙ ЦАЛЬ"},
                {"MITCH LUCKER", "Eddie Hermida", "Verena Selis", "D.Randall Blythe", "MITCH LUCKER"},
                {"Clarity", "THRILLER", "Black & Blue", "Come on over", "THRILLER"},
                {"Ливерпуль", "Нью-Орлеан", "Кливленд", "МЕМФИС", "МЕМФИС"},
                {"Рок-н-ролл", "ФОНК", "Блюз", "Инди", "ФОНК"},
                {"Wu-Tang Clan", "Guns N'Roses", "Slipknot", "ROCKIN", "ROCKIN"},
                {"СЕВИЛЬЯ", "Богота", "Ганновер", "Нэшвилл", "СЕВИЛЬЯ"},
                {"Игги Поп", "Роберт Плант", "АЛЕКСЕЙ КОЗЛОВ", "Ринго Старр", "АЛЕКСЕЙ КОЗЛОВ"},
                {"Поль Мориа", "БОБ МАРЛИ", "Ли Скрэтч Перри", "Тревор Оливер Тэйлор", "БОБ МАРЛИ"},
                {"KYLE STRANG", "Marcus Bridge", "Cocaine krueger", "Blessed Mane", "KYLE STRANG"}
        };
        for (int i=0; i<10; i++){
            List<String> tempList = new ArrayList<>();
            for (int j=0; j<5; j++)
                tempList.add(mAswers[i][j]);
            answersList.add(tempList);
        }

        Questions questions = new Questions(questionsList, answersList);
        mDatabase.push().setValue(questions);

    }

 */

}