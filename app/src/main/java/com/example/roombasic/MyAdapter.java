package com.example.roombasic;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// Adapter is a bridge between data and the ui interface
/*
 * RecyclerView.Adapter let the RecycleView in 'activity_main.xml' can access the ui interface in
   'cell_normal.xml' and 'cell_card.xml'
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    List<Word> allWords = new ArrayList<>(); // used to store the list of the data in 'Word' type
    // used to determine whether need to access 'cell_normal.xml' or 'cell_card.xml'
    boolean useCardView;
    WordViewModel wordViewModel;

    // constructor to set the value in 'useCardView'
    public MyAdapter(boolean useCardView, WordViewModel wordViewModel) {
        this.useCardView = useCardView;
        this.wordViewModel = wordViewModel;
    }

    // function to set the value in 'allWords'
    public void setAllWords(List<Word> allWords) {
        this.allWords = allWords;
    }

    @NonNull
    @Override
    /*
     * 每当 RecyclerView 需要创建新的 ViewHolder 时，它都会调用此方法。
       此方法会创建并初始化 ViewHolder 及其关联的 View，但不会填充视图的内容，
       因为 ViewHolder 此时尚未绑定到具体数据。
     */
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView;
        if (useCardView) { // if the value in 'useCardView' is true
            // 'itemView' is equal to the layout in 'cell_card.xml'
            itemView = layoutInflater.inflate(R.layout.cell_card_2,parent,false);
        }
        else {
            // 'itemView' is equal to the layout in 'cell_normal.xml'
            itemView = layoutInflater.inflate(R.layout.cell_normal_2,parent,false);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Word word = allWords.get(position);
        holder.textViewNumber.setText(String.valueOf(position + 1));
        holder.textViewEnglish.setText(word.getWord());
        holder.textViewChinese.setText(word.getChineseMeaning());
        holder.aSwitchChineseInvisible.setOnCheckedChangeListener(null);
        // while open the app, it will set the status of the switch base on the database
        if (word.isChineseInvisible()) {
            // textViewchinese absent
            holder.textViewChinese.setVisibility(View.GONE);
            // the switch open
            holder.aSwitchChineseInvisible.setChecked(true);
        }
        else {
            // textViewchinese present
            holder.textViewChinese.setVisibility(View.VISIBLE);
            // the switch close
            holder.aSwitchChineseInvisible.setChecked(false);
        }
        // when click the itemView, it will go to the website of 'youdao'
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.youdao.com/result?word=" +
                        holder.textViewEnglish.getText() + "&lang=en");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                holder.itemView.getContext().startActivity(intent);
            }
        });
        // when open the switch beside the word, the chinese word will absent
        // else the chinese word will present
        holder.aSwitchChineseInvisible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    // the textViewChinese present
                    holder.textViewChinese.setVisibility(View.GONE);
                    // the switch open
                    word.setChineseInvisible(true);
                }
                else {
                    // the textViewChinese absent
                    holder.textViewChinese.setVisibility(View.VISIBLE);
                    // the switch close
                    word.setChineseInvisible(false);
                }
                // update the database
                wordViewModel.updateWords(word);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allWords.size();
    }

    // ViewHolder通常出现在Adapter里，为的是listview滚动的时候快速设置值，而不必每次都重新创建很多对象，从而提升性能。
    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNumber, textViewEnglish, textViewChinese;
        Switch aSwitchChineseInvisible;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNumber = itemView.findViewById(R.id.textViewNumber);
            textViewEnglish = itemView.findViewById(R.id.textViewEnglish);
            textViewChinese = itemView.findViewById(R.id.textViewChinese);
            aSwitchChineseInvisible = itemView.findViewById(R.id.switchChineseInvisible);
        }
    }
}
