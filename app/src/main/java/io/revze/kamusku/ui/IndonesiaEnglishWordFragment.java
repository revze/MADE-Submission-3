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
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import io.revze.kamusku.R;
import io.revze.kamusku.adapter.IndonesiaEnglishWordAdapter;
import io.revze.kamusku.db.IndonesiaEnglishWordHelper;
import io.revze.kamusku.eventbus.ClearSearchEvent;
import io.revze.kamusku.eventbus.SearchEvent;
import io.revze.kamusku.model.IndonesiaEnglishWord;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndonesiaEnglishWordFragment extends Fragment {

    private Context context;
    private EventBus eventBus;
    private ArrayList<IndonesiaEnglishWord> indonesiaEnglishWords = new ArrayList<>();
    private ArrayList<IndonesiaEnglishWord> filteredIndonesiaEnglishWords = new ArrayList<>();
    private IndonesiaEnglishWordAdapter adapter;

    public IndonesiaEnglishWordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_indonesia_english_word, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        eventBus = EventBus.getDefault();
        eventBus.register(this);

        context = requireActivity();
        IndonesiaEnglishWordHelper helper = new IndonesiaEnglishWordHelper(context);
        RecyclerView rvWord = view.findViewById(R.id.rv_word);
        adapter = new IndonesiaEnglishWordAdapter(context);
        rvWord.setLayoutManager(new LinearLayoutManager(context));
        rvWord.setAdapter(adapter);
        helper.open();
        indonesiaEnglishWords = helper.getAllData();
        helper.close();
        adapter.addItem(indonesiaEnglishWords);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSearchEvent(SearchEvent event) {
        String keywords = event.keywords.trim().toLowerCase();

        if (!keywords.equals("")) {
            filteredIndonesiaEnglishWords.clear();

            for (IndonesiaEnglishWord word : indonesiaEnglishWords) {
                if (word.getWord().toLowerCase().contains(keywords)) filteredIndonesiaEnglishWords.add(word);
            }

            adapter.addItem(filteredIndonesiaEnglishWords);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onClearSearchEvent(ClearSearchEvent event) {
        adapter.addItem(indonesiaEnglishWords);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        super.onDestroy();
    }
}
