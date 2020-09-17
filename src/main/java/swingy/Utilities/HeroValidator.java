package swingy.Utilities;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import swingy.Models.Hero;

public class HeroValidator {
    private static Validator validator;

    public HeroValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public boolean isValidHero(Hero hero) {
        try {
            Set<ConstraintViolation<Hero>> constraintViolations = validator.validate(hero);
            if (constraintViolations.size() != 0) {
                System.out.println("Uh oh! Thats a bad hero!");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("Uh oh! Thats a bad hero!");
            return false;
        }
    }
}
