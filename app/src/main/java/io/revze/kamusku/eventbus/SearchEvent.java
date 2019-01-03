package io.revze.kamusku.eventbus;

import java.util.ArrayList;

import io.revze.kamusku.model.IndonesiaEnglishWord;

public class SearchEvent {
    public String keywords;

    public SearchEvent(String keywords) {
        this.keywords = keywords;
    }
}
