package ru.grigoriev.graduationproject.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.grigoriev.graduationproject.dto.VoteDto;
import ru.grigoriev.graduationproject.service.VoteService;
import ru.grigoriev.graduationproject.web.constant.Constant;

@Slf4j
@RestController
@RequestMapping(value = Constant.VERSION_URL + "/votes",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {

    private final VoteService service;
    private boolean flag = true;

    public VoteRestController(VoteService voteService) {
        this.service = voteService;
    }

    @GetMapping(value = "/allRestaurant")
    public ResponseEntity<String> getAllRestaurantWithMenuAndVotes() {
        log.info("IN getAllRestaurantWithMenuAndVotes");
        return ResponseEntity.ok(service.getAllRestaurantWithMenuAndVotes());
    }

    @PostMapping(value = "/save/{id}")
    public ResponseEntity<String> createVote(@PathVariable(name = "id") int id) {
        log.info("IN createVote");
        VoteDto result = service.create(id, flag);
        return ResponseEntity.ok("Vote for restaurant with id = " + id + ", name's restaurant = " + result.getName() + ", successfully create.");
    }

    @PostMapping(value = "/update/{id}")
    public ResponseEntity<String> updateVote(@PathVariable(name = "id") int id) {
        log.info("IN updateVote");
        VoteDto result = service.create(id, !flag);
        return ResponseEntity.ok("Vote for restaurant with id = " + id + ", name's restaurant = " + result.getName() + ", successfully update.");
    }
}
