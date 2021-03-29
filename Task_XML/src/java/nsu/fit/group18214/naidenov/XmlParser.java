package nsu.fit.group18214.naidenov;


import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.awt.image.AreaAveragingScaleFilter;
import java.io.*;
import java.util.*;

public class XmlParser {

    private Map<String, Person> idToPerson = new HashMap<>();
    private Map<String, Person> fullNameToPerson = new HashMap<>();

    private LinkCreator linkCreator = new LinkCreator();

    public Map<String, Person> parse() throws XMLStreamException, FileNotFoundException {

        Person curPerson = null;
        String tagContent = null;
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader =
                factory.createXMLStreamReader(
                        new FileInputStream(new File("D:\\Java Projects\\Cybersecurity\\Information-processing-and-storage\\Task_XML\\people.xml")));

        while (reader.hasNext()) {
            int event = reader.next();

            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    String localName = reader.getLocalName();
                    switch (localName) {
                        case "person":
                            curPerson = new Person();
                            if (reader.getAttributeCount() != 0) {
                                String value = reader.getAttributeValue(0).trim();
                                if (linkCreator.isId(value)) {
                                    curPerson.setId(value);
                                } else {
                                    String[] parts = value.split("\\s+");
                                    if (parts.length == 2) {
                                        curPerson.setFirstName(parts[0]);
                                        curPerson.setLastName(parts[1]);
                                    } else if (parts.length != 0) {
                                        if (parts[0].compareTo("") != 0 && parts[0].compareTo(" ") != 0)
                                            curPerson.setFirstName(parts[0]);
                                    }
                                }
                            }
                            break;

                        case "people":
                            String size = reader.getAttributeValue(0);
                            break;

                        case "id":
                            assert curPerson != null;
                            if (reader.getAttributeCount() != 0 && curPerson.getId() == null) {
                                String id = reader.getAttributeValue(0).trim();
                                curPerson.setId(id);
                            }
                            break;

                        case "gender":
                            assert curPerson != null;
                            if (reader.getAttributeCount() != 0 && curPerson.getGender() == null) {
                                String gender = reader.getAttributeValue(0).trim();
                                curPerson.setGender(gender);
                            }
                            break;
                        case "firstname":
                            assert curPerson != null;
                            if (reader.getAttributeCount() != 0 && curPerson.getFirstName() == null) {
                                String firstName = reader.getAttributeValue(0).trim();
                                curPerson.setFirstName(firstName);
                            }
                            break;
                        case "surname":
                            assert curPerson != null;
                            if (reader.getAttributeCount() != 0 && curPerson.getLastName() == null) {
                                String surname = reader.getAttributeValue(0).trim();
                                curPerson.setLastName(surname);
                            }
                            break;
                        case "wife":
                            if (reader.getAttributeCount() != 0) {
                                String wifeId = reader.getAttributeValue(0).trim();
                                curPerson.wife = wifeId;//addSpouse(spouse);
                                curPerson.setGender("male");
                            }
                            break;

                        //ID
                        case "husband":
                            if (reader.getAttributeCount() != 0) {
                                String husbandId = reader.getAttributeValue(0).trim();
                                curPerson.husband = husbandId;//addSpouse(spouse);
                                curPerson.setGender("female");
                            }
                            break;
                        case "spouce":
                            if (reader.getAttributeCount() != 0) {
                                String[] parts = reader.getAttributeValue(0).trim().split("\\s+");
                                if (parts[0].compareTo("NONE") == 0 || parts[0].compareTo("UNKNOWN") == 0 | parts[0].compareTo("EMPTY") == 0)
                                    break;
                                String spouse = parts[0] + " " + parts[1];
                                curPerson.addSpouse(spouse);
                                //curPerson.addSpouse(spouse);
                            }
                            break;

                        case "parent":
                            assert curPerson != null;
                            if (reader.getAttributeCount() != 0) {
                                String parent = reader.getAttributeValue(0).trim();
                                if (linkCreator.isId(parent)) {
                                    curPerson.addParent(parent);
                                }
                            }
                            break;
                        //ID
                        case "son":
                            if (reader.getAttributeCount() != 0) {
                                String child = reader.getAttributeValue(0).trim();
                                curPerson.sons.add(child);
                            }
                            break;
                        //ID
                        case "daughter":
                            if (reader.getAttributeCount() != 0) {
                                String child = reader.getAttributeValue(0).trim();
                                curPerson.daughters.add(child);
                            }
                            break;

                        //ID
                        case "siblings":
                            if (reader.getAttributeCount() != 0) {
                                String sib = reader.getAttributeValue(0).trim();
                                String[] parts = sib.split("\\s+");
                                for (String blyat : parts) {
                                    curPerson.addSibling(blyat);
                                }

                            }
                            break;

                        case "siblings-number":
                            if (reader.getAttributeCount() != 0) {
                                String number = reader.getAttributeValue(0).trim();
                                curPerson.siblingsNumber = Integer.parseInt(number);
                            }
                            break;
                        case "children-number":
                            if (reader.getAttributeCount() != 0) {
                                String number = reader.getAttributeValue(0).trim();
                                curPerson.childrenNumber = Integer.parseInt(number);
                            }
                            break;

                    }
                    break;

                case XMLStreamConstants.CHARACTERS:
                    tagContent = reader.getText().trim();
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    String contentTeg = reader.getLocalName();
                    switch (contentTeg) {
                        case "person":

                            String id = curPerson.getId();
                            String f = curPerson.getFirstName();
                            String s = curPerson.getLastName();

                            if (id != null) {
                                if (!idToPerson.containsKey(id)) {
                                    idToPerson.put(id, curPerson);
                                } else {
                                    Person oldPerson = idToPerson.get(id);
                                    mergePersons(curPerson, oldPerson);
                                }
                            }
                            if (f != null && s != null) {
                                String fullName = f + " " + s;
                                if (!fullNameToPerson.containsKey(fullName)) {
                                    fullNameToPerson.put(fullName, curPerson);
                                } else {
                                    Person oldPerson = fullNameToPerson.get(fullName);
                                    mergePersons(curPerson, oldPerson);

                                }
                            }

                            break;

                        case "gender":
                            assert curPerson != null;
                            if (tagContent.compareTo("F") == 0) {
                                curPerson.setGender("female");
                            }
                            if (tagContent.compareTo("M") == 0) {
                                curPerson.setGender("male");
                            }
                            break;

                        case "firstname":
                        case "first":
                            if (curPerson.getFirstName() == null) {
                                curPerson.setFirstName(tagContent);
                            }
                            break;

                        case "family":
                        case "family-name":
                            assert curPerson != null;
                            if (curPerson.getLastName() == null) {
                                curPerson.setLastName(tagContent);
                            }
                            break;


                        case "mother":
                            assert curPerson != null;
                            String[] parts = tagContent.split("\\s+");

                            curPerson.mother = parts[0] + " " + parts[1];
                            break;

                        case "father":
                            String[] partss = tagContent.split("\\s+");

                            curPerson.father = partss[0] + " " + partss[1];
                            break;
                        case "parent":
                            assert curPerson != null;
                            if (tagContent.compareTo("UNKNOWN") == 0)
                                break;
                            String[] partsss = tagContent.split("\\s+");
                            if (partsss.length == 2) {
                                curPerson.addParent(partsss[0] + " " + partsss[1]);
                            }

                            break;

                        case "child":
                            String child = tagContent;

                            String[] childParts = child.split("\\s+");
                            curPerson.addChild(childParts[0] + " " + childParts[1]);

                            break;

                        case "sister":
                            String[] split = tagContent.split("\\s+");
                            curPerson.sisters.add(split[0] + " " + split[1]);
                            break;


                        case "brother":
                            assert curPerson != null;
                            String[] split1 = tagContent.split("\\s+");
                            curPerson.brothers.add(split1[0] + " " + split1[1]);
                            break;
                    }
                    break;

                case XMLStreamConstants.START_DOCUMENT:
                    break;
            }

        }
        //linkCreator.recoverLinks(personList);
        //List<Person> res = linkCreator.removeCopies(personList);
        //mergeMaps();
        List<Person> dropList = new ArrayList<>();
        List<String> ids = Arrays.asList("P386310", "P391473", "P389809", "P405298", "P400086", "P405249", "P385622", "P395948", "P384181", "P399670", "P390327", "P385164", "P382644", "P387807", "P384591", "P389754", "P390017", "P379691", "P396443", "P380954", "P411821", "P406658");
        List<String> names = Arrays.asList("Seth Vicario", "Lurline Trawick", "Madeline Stoot", "Shemeka Pilar", "Oscar Silvester", "Tonya Loschiavo", "Janeen Robbinson", "Tanika Belstad", "Orlando Pennison", "Miki Whyel", "Kaylene Startz");

        List<Person> pidorasiWithOutFullName = new ArrayList<>();

        List<Person> pidorasiWithOutGender = new ArrayList<>();
        List<Person> pidorasiWithOutSiblings = new ArrayList<>();
        List<Person> pidorasiWithOutFullChildren = new ArrayList<>();
       /* for (Person p : idToPerson.values()) {
            if (dropList.contains(p)) {
                continue;
            }
            String pFullName = p.firstName + " " + p.lastName;
            for (Person p1 : idToPerson.values()) {
                if (!p1.equals(p)) {
                    String p1FullName = p1.firstName + " " + p1.lastName;
                    if (pFullName.compareTo(p1FullName) == 0) {
                        dropList.add(p1);
                        dropList.add(p);
                        ids.add(p1.id);
                        ids.add(p.id);
                        names.add(p.firstName + " " + p.lastName);
                        names.add(p1.firstName + " " + p1.lastName);
                        break;
                    }
                }
            }
            if (dropList.size() == 22) {
                break;
            }
        }*/

        for (String id : ids) {
            idToPerson.remove(id);
            //System.out.print(id + " ");
        }
        System.out.println();
        for (String name : names) {
            fullNameToPerson.remove(name);
            //System.out.print(name + " | ");
        }
        //Set<String> ids = idToPerson.keySet();
        /*for (Person p : dropList) {
            idToPerson.remove(p.id);
            fullNameToPerson.remove(p.firstName + " " + p.lastName);
        }*/
        mergeMaps();

        recoverChildren();
        recoverSib();

        for (Person p : idToPerson.values()) {
            if (p.firstName == null || p.lastName == null) {
                pidorasiWithOutFullName.add(p);
            }

            if (p.gender == null) {
                pidorasiWithOutGender.add(p);
            }
            if (p.siblingsNumber != null && p.siblingsNumber != (p.brothers.size() + p.sisters.size())) {
                pidorasiWithOutSiblings.add(p);
            }
            if (p.childrenNumber != null && p.childrenNumber != (p.sons.size() + p.daughters.size())) {
                pidorasiWithOutFullChildren.add(p);
            }
        }
        for (Person p : fullNameToPerson.values()) {
            if (p.firstName == null || p.lastName == null) {
                pidorasiWithOutFullName.add(p);
            }

            if (p.gender == null) {
                pidorasiWithOutGender.add(p);
            }
            if (p.siblingsNumber != null && p.siblingsNumber != (p.brothers.size() + p.sisters.size())) {
                pidorasiWithOutSiblings.add(p);
            }
            if (p.childrenNumber != null && p.childrenNumber != (p.sons.size() + p.daughters.size())) {
                pidorasiWithOutFullChildren.add(p);
            }
        }

        for (Person p : idToPerson.values()) {
            for (String dId : p.daughters) {
                Person d = idToPerson.get(dId);
                if (d != null) {
                    if (p.pDaughters == null) {
                        p.pDaughters = new HashSet<>();
                    }
                    p.pDaughters.add(d);
                }
            }
            for (String sisName : p.sisters) {
                Person s = fullNameToPerson.get(sisName);
                if (s != null) {
                    if (p.pSisters == null) {
                        p.pSisters = new HashSet<>();
                    }
                    p.pSisters.add(s);
                }
            }
            for (String sonId : p.sons) {
                Person son = idToPerson.get(sonId);
                if (son != null) {
                    if (p.pSons == null) {
                        p.pSons = new HashSet<>();
                    }
                    p.pSons.add(son);
                }

            }
            for (String broName : p.brothers) {
                Person bro = fullNameToPerson.get(broName);
                if (bro != null) {
                    if (p.pBrothers == null) {
                        p.pBrothers = new HashSet<>();
                    }
                    p.pBrothers.add(bro);
                }
            }
            Person m = fullNameToPerson.get(p.mother);
            if (m != null) {
                p.pMother = m;

            }
            m = fullNameToPerson.get(p.father);
            if (m != null) {

                p.pFather = m;
            }
            m = idToPerson.get(p.husband);
            if (m != null) {
                p.pHusband = m;
            }
            m = idToPerson.get(p.wife);
            if (m != null) {
                p.pWife = m;
            }

        }


        return idToPerson;
    }

    private void mergeMaps() {
        for (Person newPerson : idToPerson.values()) {
            String f = newPerson.getFirstName();
            String s = newPerson.getLastName();
            String fullName;
            if (f != null && s != null) {
                fullName = f + " " + s;
                if (fullNameToPerson.containsKey(fullName)) {
                    Person oldPerson = fullNameToPerson.get(fullName);
                    mergePersons(newPerson, oldPerson);
                    mergePersons(oldPerson, newPerson);
                } else {
                    fullNameToPerson.put(fullName, newPerson);
                }
            }
        }
    }


    private void mergePersons(Person newPerson, Person oldPerson) {
        //Person oldPerson = idToPerson.get(newPerson.getId());
        if (oldPerson.id == null && newPerson.id != null) {
            oldPerson.id = newPerson.id;
        }
        if (oldPerson.childrenNumber == null && newPerson.childrenNumber != null) {
            oldPerson.childrenNumber = newPerson.childrenNumber;
        }
        if (oldPerson.siblingsNumber == null && newPerson.siblingsNumber != null) {
            oldPerson.siblingsNumber = newPerson.siblingsNumber;
        }
        if (oldPerson.getFirstName() == null && newPerson.getFirstName() != null) {
            oldPerson.setFirstName(newPerson.getFirstName());
        }
        if (oldPerson.getLastName() == null && newPerson.getLastName() != null) {
            oldPerson.setLastName(newPerson.getLastName());
        }
        if (oldPerson.father == null && newPerson.father != null) {
            oldPerson.father = newPerson.father;
        }
        if (oldPerson.mother == null && newPerson.mother != null) {
            oldPerson.mother = newPerson.mother;
        }

        if (oldPerson.wife == null && newPerson.wife != null) {
            oldPerson.wife = newPerson.wife;
        }
        if (oldPerson.husband == null && newPerson.husband != null) {
            oldPerson.husband = newPerson.husband;
        }

        if (oldPerson.gender == null && newPerson.gender != null) {
            oldPerson.gender = newPerson.gender;
        }
        oldPerson.parents.addAll(newPerson.parents);

        oldPerson.brothers.addAll(newPerson.brothers);

        oldPerson.sisters.addAll(newPerson.sisters);

        oldPerson.daughters.addAll(newPerson.daughters);

        oldPerson.sons.addAll(newPerson.sons);

        oldPerson.children.addAll(newPerson.children);

        oldPerson.siblings.addAll(newPerson.siblings);

        oldPerson.spouses.addAll(newPerson.spouses);





        /*Person person = idToPerson.get(wifeId);
        if (person != null) {
            person.setGender("female");
            if (person.husband == null && curPerson.getId() != null) {
                person.husband = curPerson.getId();
            }
        }
        Person person = idToPerson.get(husbandId);
        if (person != null) {
            person.setGender("male");
            if (person.wife == null && curPerson.getId() != null) {
                person.wife = curPerson.getId();
            }
        }

        Person person = fullNameToPerson.get(spouse);
        if (person != null) {
            if (person.getGender().compareTo("male") == 0) {
                curPerson.setGender("female");
                curPerson.husband = spouse;

            } else if (person.getGender().compareTo("female") == 0) {
                curPerson.setGender("male");
                curPerson.wife = spouse;
            }

        }*/


    }

    private void recoverChildren() {
        for (Person p : idToPerson.values()) {
            for (String name : p.children) {
                Person child = fullNameToPerson.get(name);
                if (child == null) {
                    p.childrenNumber--;
                    continue;
                }
                if (child.gender.compareTo("male") == 0) {
                    p.sons.add(child.id);

                }
                if (child.gender.compareTo("female") == 0) {
                    p.daughters.add(child.id);
                }
            }
            p.children.clear();
        }
    }

    private void recoverSib() {
        for (Person p : idToPerson.values()) {
            for (String id : p.siblings) {
                Person sib = idToPerson.get(id);
                if (sib == null) {
                    p.siblingsNumber--;
                    continue;
                }
                if (sib.gender.compareTo("male") == 0) {
                    p.brothers.add(sib.firstName + " " + sib.getLastName());
                }
                if (sib.gender.compareTo("female") == 0) {
                    p.sisters.add(sib.firstName + " " + sib.getLastName());
                }

            }
            p.siblings.clear();
        }

    }

}
