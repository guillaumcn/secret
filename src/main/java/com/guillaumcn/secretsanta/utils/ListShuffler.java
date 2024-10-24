package com.guillaumcn.secretsanta.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ListShuffler<T> {

    public List<T> shuffle(List<T> list) {
        List<T> shuffledList = new ArrayList<>(list);
        Collections.shuffle(shuffledList);
        return shuffledList;
    }
}
