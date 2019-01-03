package io.revze.kamusku.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.revze.kamusku.R;
import io.revze.kamusku.model.EnglishIndonesiaWord;
import io.revze.kamusku.ui.WordDetailActivity;

public class EnglishIndonesiaWordAdapter extends RecyclerView.Adapter<EnglishIndonesiaWordAdapter.EnglishIndonesiaViewHolder> {
    private List<EnglishIndonesiaWord> englishIndonesiaWords = new ArrayList<>();
    private Context context;

    public EnglishIndonesiaWordAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public EnglishIndonesiaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EnglishIndonesiaViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_word, parent, false));
    }

    public void addItem(ArrayList<EnglishIndonesiaWord> englishIndonesiaWords) {
        this.englishIndonesiaWords = englishIndonesiaWords;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull EnglishIndonesiaViewHolder holder, int position) {
        final EnglishIndonesiaWord englishIndonesiaWord = englishIndonesiaWords.get(position);

        holder.tvWord.setText(englishIndonesiaWord.getWord());
        holder.layoutWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wordDetailIntent = new Intent(context, WordDetailActivity.class);
                wordDetailIntent.putExtra(WordDetailActivity.WORD, englishIndonesiaWord.getWord());
                wordDetailIntent.putExtra(WordDetailActivity.DESCRIPTION, englishIndonesiaWord.getDescription());
                context.startActivity(wordDetailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return englishIndonesiaWords.size();
    }

    public static class EnglishIndonesiaViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutWord;
        private TextView tvWord;

        public EnglishIndonesiaViewHolder(View itemView) {
            super(itemView);

            layoutWord = itemView.findViewById(R.id.layout_word);
            tvWord = itemView.findViewById(R.id.tv_word);
        }
    }
}
