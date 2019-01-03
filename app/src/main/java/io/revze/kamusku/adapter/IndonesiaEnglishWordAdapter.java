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
import io.revze.kamusku.model.IndonesiaEnglishWord;
import io.revze.kamusku.ui.WordDetailActivity;

public class IndonesiaEnglishWordAdapter extends RecyclerView.Adapter<IndonesiaEnglishWordAdapter.IndonesiaEnglishViewHolder> {
    private List<IndonesiaEnglishWord> indonesiaEnglishWords = new ArrayList<>();
    private Context context;

    public IndonesiaEnglishWordAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public IndonesiaEnglishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IndonesiaEnglishViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_word, parent, false));
    }

    public void addItem(ArrayList<IndonesiaEnglishWord> indonesiaEnglishWords) {
        this.indonesiaEnglishWords = indonesiaEnglishWords;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull IndonesiaEnglishViewHolder holder, int position) {
        final IndonesiaEnglishWord indonesiaEnglishWord = indonesiaEnglishWords.get(position);

        holder.tvWord.setText(indonesiaEnglishWord.getWord());
        holder.layoutWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wordDetailIntent = new Intent(context, WordDetailActivity.class);
                wordDetailIntent.putExtra(WordDetailActivity.WORD, indonesiaEnglishWord.getWord());
                wordDetailIntent.putExtra(WordDetailActivity.DESCRIPTION, indonesiaEnglishWord.getDescription());
                context.startActivity(wordDetailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return indonesiaEnglishWords.size();
    }

    public static class IndonesiaEnglishViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutWord;
        private TextView tvWord;

        public IndonesiaEnglishViewHolder(View itemView) {
            super(itemView);

            layoutWord = itemView.findViewById(R.id.layout_word);
            tvWord = itemView.findViewById(R.id.tv_word);
        }
    }
}
