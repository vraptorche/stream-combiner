package com.oracle.homework.service;


import io.reactivex.Observable;

import java.util.Arrays;
import java.util.Optional;

public class DefaultStreamCombiner implements StreamCombiner {

    @Override
    public Optional<Observable<String>> combineChunks(String... chunks) {
      return  Arrays.stream(chunks)
                .map(s -> s.split("\n"))
                .map(Observable::fromArray)
                .reduce(Observable::mergeWith);
    }
}
