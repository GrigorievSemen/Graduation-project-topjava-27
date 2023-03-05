package ru.grigoriev.graduationproject.web.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.grigoriev.graduationproject.dto.VoteDto;
import ru.grigoriev.graduationproject.service.VoteService;
import ru.grigoriev.graduationproject.web.user.constant.Constant;

@Slf4j
@RestController
@RequestMapping(value = Constant.VERSION_URL + "/vote",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {

    private final VoteService service;
    private boolean flag = true;

    public VoteRestController(VoteService voteService) {
        this.service = voteService;
    }

    @GetMapping(value = "/allRestaurant")
    public ResponseEntity<String> getAllRestaurant() {
        log.info("IN getAllRestaurant");
        return ResponseEntity.ok(service.getAllRestaurant());
    }

    @GetMapping(value = "/allVote")
    public ResponseEntity<String> getAllVote() {
        log.info("IN getAllVote");
        return ResponseEntity.ok(service.getAllVote());
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
