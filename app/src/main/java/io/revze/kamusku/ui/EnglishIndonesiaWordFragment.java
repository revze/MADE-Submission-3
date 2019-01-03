package io.revze.kamusku.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import io.revze.kamusku.R;
import io.revze.kamusku.adapter.EnglishIndonesiaWordAdapter;
import io.revze.kamusku.db.EnglishIndonesiaWordHelper;
import io.revze.kamusku.eventbus.ClearSearchEvent;
import io.revze.kamusku.eventbus.SearchEvent;
import io.revze.kamusku.model.EnglishIndonesiaWord;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnglishIndonesiaWordFragment extends Fragment {

    private Context context;
    private EventBus eventBus;
    private ArrayList<EnglishIndonesiaWord> englishIndonesiaWords = new ArrayList<>();
    private ArrayList<EnglishIndonesiaWord> filteredEnglishIndonesiaWords = new ArrayList<>();
    private EnglishIndonesiaWordAdapter adapter;

    public EnglishIndonesiaWordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_english_indonesia_word, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        eventBus = EventBus.getDefault();
        eventBus.register(this);
        Context context = requireActivity();
        EnglishIndonesiaWordHelper helper = new EnglishIndonesiaWordHelper(context);
        RecyclerView rvWord = view.findViewById(R.id.rv_word);
        adapter = new EnglishIndonesiaWordAdapter(context);
        rvWord.setLayoutManager(new LinearLayoutManager(context));
        rvWord.setAdapter(adapter);
        helper.open();
        englishIndonesiaWords = helper.getAllData();
        helper.close();
        adapter.addItem(englishIndonesiaWords);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSearchEvent(SearchEvent event) {
        String keywords = event.keywords.trim().toLowerCase();

        if (!keywords.equals("")) {
            filteredEnglishIndonesiaWords.clear();

            for (EnglishIndonesiaWord word : englishIndonesiaWords) {
                if (word.getWord().toLowerCase().contains(keywords)) filteredEnglishIndonesiaWords.add(word);
            }

            adapter.addItem(filteredEnglishIndonesiaWords);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onClearSearchEvent(ClearSearchEvent event) {
        adapter.addItem(englishIndonesiaWords);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        super.onDestroy();
    }
}
