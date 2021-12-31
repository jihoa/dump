package org.springframework.samples.petclinic.restful;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.restful.resolver.ClientIP;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MemberController {
    final MemberService memberService;
    final MemberRepository memberRepository;

    public MemberController(MemberService memberService, MemberRepository memberRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }

    @GetMapping(value = "/members",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Member>> getMembers() {
        //log.info("Request Client IP: {}", clientIp);
        return ResponseEntity.ok().body(memberService.findByAll());

    }
    @GetMapping(value = "/members/{username}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Member> getMember(@PathVariable String username) {
        //log.info("Request Client IP: {}", clientIp);
        return ResponseEntity.ok().body(memberService.findByUsername(username));
        //return ResponseEntity.ok().body(memberRepository.findByCustom(username));

    }

    @PostMapping(value = "/members", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Member> addMember(@Valid @RequestBody Member member) {
        memberService.add(member);
        return ResponseEntity.ok(memberService.findByUsername(member.getUsername()));
    }

    @PutMapping(value = "/members", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Member> modifyMember(@Valid @RequestBody Member member) {
        memberService.update(member);
        return ResponseEntity.ok().body(member);
    }

    @DeleteMapping(value = "/members/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeMember(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.ok("Member is deleted");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions (MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}
