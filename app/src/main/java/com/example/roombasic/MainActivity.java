package com.example.roombasic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button buttonInsert, buttonUpdate, buttonDelete, buttonClear;
    WordViewModel wordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);

        textView = findViewById(R.id.textView);

        // observe the change in livedata
        wordViewModel.getAllWordsLive().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                StringBuilder text = new StringBuilder();
                for(int i=0;i<words.size();i++) {
                    Word word = words.get(i);
                    text.append(word.getId()).append(":").append(word.getWord()).
                            append("=").append(word.getChineseMeaning()).append("\n");
                }
                textView.setText(text.toString());
            }
        });

        buttonInsert = findViewById(R.id.button);
        buttonUpdate = findViewById(R.id.button2);
        buttonClear = findViewById(R.id.button3);
        buttonDelete = findViewById(R.id.button4);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Word word1 = new Word("Hello", "你好！");
                Word word2 = new Word("World", "世界！");
                wordViewModel.insertWords(word1,word2);
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wordViewModel.deleteAllWords();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Word word = new Word("Hi", "你好啊！");
                word.setId(20);
                wordViewModel.updateWords(word);
            }
        });

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