package ru.nsu.fit.group18214.naidenov;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "persons-type")
public class XmlConfig {
    @XmlElementWrapper(name = "persons")
    @XmlElement(name = "person")
    List<Person> personList;

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public XmlConfig(){
    }
}
