package com.mrsupw;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Student extends Person {
    private Float score;
    Student (String name,Integer age, Float score){
        super(name,age);
        this.score = score;
    }
    public static void main(String[] args) {
        List<Person> people = new ArrayList<>();
        people.add(new Student("aa",18,56.3f));
        people.add(new Person("aa",18));
        people.add(new Student("aa",18,56.3f));
        people.add(new Person("aa",18));
        people.forEach(person -> {
            if(person.getClass() == Student.class){
                System.out.println(person);
            }
        });
    }
}
