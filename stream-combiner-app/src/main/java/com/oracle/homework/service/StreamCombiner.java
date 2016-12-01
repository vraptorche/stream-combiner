package com.oracle.homework.service;


import io.reactivex.Observable;

import java.util.Optional;

public interface StreamCombiner {

    Optional<Observable<String>> combineChunks(String... chunks);
}
