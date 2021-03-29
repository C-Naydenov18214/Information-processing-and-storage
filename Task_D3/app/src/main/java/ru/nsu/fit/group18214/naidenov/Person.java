package ru.nsu.fit.group18214.naidenov;

import javax.xml.bind.annotation.*;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "person-type")
public class Person {
    @XmlID
    @XmlAttribute(name = "person-id", required = true)
    public String id;

    @XmlElement(name = "firstname", required = true)
    public String firstName;
    @XmlElement(name = "surname", required = true)
    public String lastName;
    @XmlElement(name = "gender", required = true)
    public String gender;

    @XmlTransient
    public Set<String> sons;

    @XmlTransient
    public Set<String> daughters;

    @XmlTransient
    public Set<String> sisters;

    @XmlTransient
    public Set<String> brothers;

    @XmlTransient
    public String mother;

    @XmlTransient
    public String father;

    @XmlTransient
    public String husband;

    @XmlTransient
    public String wife;


    public Person() {
        daughters = new HashSet<>();
        sisters = new HashSet<>();
        sons = new HashSet<>();
        brothers = new HashSet<>();
    }


    public void setId(String id) {
        this.id = id;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }


    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }


    public String getGender() {
        return gender;
    }
}

