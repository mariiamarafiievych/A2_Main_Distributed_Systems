package entities.dto;

import entities.Customer;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class CustomerDTO {

    private String firstName;
    private String lastName;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setName(String firstName) {
        this.firstName = firstName;
    }

}

