package com.douncoding.enterprise.domain.context;

/**
 * Value Object(값 객체)
 */
public class FullName {
    private String firstName;
    private String lastName;

    public FullName(String firstName, String lastName) {
        super();

        this.setFirstName(firstName);
        this.setLastName(lastName);
    }

    protected void setFirstName(String firstName) {
        if (firstName == null) {
            throw new IllegalArgumentException(
                    "Must provide a first name or initial.");
        }

        this.firstName = firstName;
    }

    protected void setLastName(String lastName) {
        if (lastName == null) {
            throw  new IllegalArgumentException(
                    "Must provide a last name or initial.");
        }

        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;
        if (anObject != null && this.getClass() == anObject.getClass()) {
            FullName typedObject = (FullName)anObject;
            equalObjects =
                    this.firstName.equals(typedObject.firstName) &&
                            this.lastName.equals(typedObject.lastName);
        }
        return equalObjects;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
