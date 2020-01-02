package com.wojnarowicz.sfg.recipe.reactive;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.wojnarowicz.sfg.recipe.commands.PersonCommand;
import com.wojnarowicz.sfg.recipe.domain.Person;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
class MongoReactiveTest {

    Person michael = new Person("Michael", "Weston");
    Person fiona = new Person("Fiona", "Glenanne");
    Person sam = new Person("Sam", "Axe");
    Person jesse = new Person("Jesse", "Porter");
    
    @BeforeEach
    void setUp() throws Exception {
    }

    @Test
    void monoPersonTest() {
        Mono<Person> monoPerson = Mono.just(michael);
        
        Person person = monoPerson.block();
        
        log.info(person.sayMyName());
    }

    @Test
    void monoPersonCommandTest() {
        Mono<Person> monoPerson = Mono.just(fiona);
        
        PersonCommand personCommand = monoPerson.map(person -> new PersonCommand(person)).block();
        
        log.info(personCommand.sayMyName());
    }
    
    @Test
    void monoPersonFilterTest() {
        Mono<Person> monoPerson = Mono.just(sam);
        Person filteredPerson = monoPerson.filter(person -> person.getFirstName().equalsIgnoreCase("foo")).block();
        
        assertThrows(NullPointerException.class, () -> log.info(filteredPerson.sayMyName()));
    }
    
    @Test
    void fluxPersonTest() {
        Flux<Person> persons = Flux.just(michael, fiona, sam, jesse);
        
        persons.subscribe(person -> log.info(person.sayMyName()));
    }

    @Test
    void fluxPersonFilterTest() {
        Flux<Person> persons = Flux.just(michael, fiona, sam, jesse);
        
        persons.filter(person -> person.getFirstName().equals(fiona.getFirstName()))
        .subscribe(person -> log.info(person.sayMyName()));
    }
    
    @Test
    void fluxPersonDealyTest() {
        Flux<Person> persons = Flux.just(michael, fiona, sam, jesse);
        
        persons.delayElements(Duration.ofSeconds(1))
        .subscribe(person -> log.info(person.sayMyName()));
    }
    
    @Test
    void fluxPersonDealyWaitTest() throws InterruptedException {
        
        CountDownLatch countDownLatch = new CountDownLatch(1);
        
        Flux<Person> persons = Flux.just(michael, fiona, sam, jesse);
        
        persons.delayElements(Duration.ofSeconds(1))
        .doOnComplete(() -> countDownLatch.countDown())
        .subscribe(person -> log.info(person.sayMyName()));
        
        countDownLatch.await();
    }
    
    @Test
    void fluxPersonDealyWaitFilterTest() throws InterruptedException {
        
        CountDownLatch countDownLatch = new CountDownLatch(1);
        
        Flux<Person> persons = Flux.just(michael, fiona, sam, jesse);
        
        persons.delayElements(Duration.ofSeconds(2))
        .filter(person -> person.getFirstName().contains("a"))
        .doOnComplete(() -> countDownLatch.countDown())
        .subscribe(person -> log.info(person.sayMyName()));
        
        countDownLatch.await();
    }
}
