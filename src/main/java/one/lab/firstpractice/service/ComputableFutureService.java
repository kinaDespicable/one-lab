package one.lab.firstpractice.service;

import one.lab.firstpractice.model.dto.response.AggregatedDTO;

import java.util.concurrent.CompletableFuture;

public interface ComputableFutureService {

    CompletableFuture<AggregatedDTO> makeCall();

}
