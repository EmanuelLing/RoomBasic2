package com.example.roombasic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button buttonInsert, buttonUpdate, buttonDelete, buttonClear;
    WordViewModel wordViewModel;
    RecyclerView recyclerView;
    MyAdapter myAdapter1, myAdapter2;
    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);

        recyclerView = findViewById(R.id.recycleView);
        myAdapter1 = new MyAdapter(false, wordViewModel);
        myAdapter2 = new MyAdapter(true, wordViewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter1);
        aSwitch = findViewById(R.id.switch1);
        // Check the change of the switch
        // if the switch is open, means that use the layout in 'cell_card.xml'
        // else, use the layout in 'cell_normal.xml'
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    recyclerView.setAdapter(myAdapter2);
                }
                else {
                    recyclerView.setAdapter(myAdapter1);
                }
            }
        });

        // observe the change in livedata
        wordViewModel.getAllWordsLive().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                int temp = myAdapter1.getItemCount();
                myAdapter1.setAllWords(words);
                myAdapter2.setAllWords(words);
                if (temp != words.size()) {
                    myAdapter1.notifyDataSetChanged();
                    myAdapter2.notifyDataSetChanged();
                }
            }
        });

        buttonInsert = findViewById(R.id.button);
        buttonUpdate = findViewById(R.id.button2);
        buttonClear = findViewById(R.id.button3);
        buttonDelete = findViewById(R.id.button4);

        // when click the 'Insert' button, add two 'Word' type value
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Word word1 = new Word("Hello", "你好！");
                Word word2 = new Word("World", "世界！");
                wordViewModel.insertWords(word1,word2);
            }
        });

        // when click the 'Clear' button, clear all the data
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wordViewModel.deleteAllWords();
            }
        });

        // when click the 'Update' button, change the data in 'Word' which id is 20
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Word word = new Word("Hi", "你好啊！");
                word.setId(20);
                wordViewModel.updateWords(word);
            }
        });

        // when click the 'Delete' button, delete the data in 'Word' which id is 17
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Word word = new Word("Hi", "你好啊！");
                word.setId(17);
                wordViewModel.deleteWords(word);
            }
        });
    }
}