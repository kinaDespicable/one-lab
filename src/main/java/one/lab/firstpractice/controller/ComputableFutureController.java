package one.lab.firstpractice.controller;

import lombok.RequiredArgsConstructor;
import one.lab.firstpractice.model.dto.response.AggregatedDTO;
import one.lab.firstpractice.service.ComputableFutureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/computable-future")
public class ComputableFutureController {


    private final ComputableFutureService computableFutureService;

    @GetMapping
    public ResponseEntity<CompletableFuture<AggregatedDTO>> computable() {
        return new ResponseEntity<>(computableFutureService.makeCall(), HttpStatus.OK);
    }

}


