package webApp.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import webApp.models.Person;
import webApp.services.PeopleService;

@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;

    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if(peopleService.getPersonByName(person.getName()).isPresent()){
            errors.rejectValue("name", "", "person with that name has already exist");
        }

    }
}
