package nsu.fit.group18214.naidenov;

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
    public Set<String> parents = new HashSet<>();
    @XmlTransient
    public Set<String> children = new HashSet<>();
    @XmlTransient
    public Set<String> siblings = new HashSet<>();
    @XmlTransient
    public Set<String> spouses = new HashSet<>();
    @XmlTransient
    public Integer childrenNumber;
    @XmlTransient
    public Integer siblingsNumber;


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

    @XmlElementWrapper(name = "sons")
    @XmlElement(name = "son")
    @XmlIDREF
    public Set<Person> pSons;

    @XmlElementWrapper(name = "daughters")
    @XmlElement(name = "daughter")
    @XmlIDREF
    public Set<Person> pDaughters;

    @XmlElementWrapper(name = "sisters")
    @XmlElement(name = "sister")
    @XmlIDREF
    public Set<Person> pSisters;

    @XmlElementWrapper(name = "brothers")
    @XmlElement(name = "brother")
    @XmlIDREF
    public Set<Person> pBrothers;

    @XmlElement(name = "mother")
    @XmlIDREF
    public Person pMother;
    @XmlElement(name = "father")
    @XmlIDREF
    public Person pFather;
    @XmlElement(name = "husband")
    @XmlIDREF
    public Person pHusband;
    @XmlElement(name = "wife")
    @XmlIDREF
    public Person pWife;

    public Person() {


        daughters = new HashSet<>();
        sisters = new HashSet<>();
        sons = new HashSet<>();
        brothers = new HashSet<>();


        /*pDaughters = new HashSet<>();
        pSisters = new HashSet<>();
        pSons = new HashSet<>();
        pBrothers = new HashSet<>();*/

    }


    public void addSpouse(String sp) {
        spouses.add(sp);

    }

    public Set<String> getParents() {
        return parents;
    }

    public Set<String> getChildren() {
        return children;
    }

    public Set<String> getSiblings() {
        return siblings;
    }

    public Set<String> getSpouses() {
        return spouses;
    }

    public void addParent(String parent) {
        parents.add(parent);
    }

    public void addChild(String child) {
        this.children.add(child);
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

    public void addSibling(String siblings) {
        this.siblings.add(siblings);
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
