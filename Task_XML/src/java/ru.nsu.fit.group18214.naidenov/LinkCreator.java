package ru.nsu.fit.group18214.naidenov;

import java.util.List;

public class LinkCreator {

    /*public void recoverLinks(List<Person> persons) {
        for (Person p : persons) {
            if (p.getLastName() == null) {
                recoverLastName(p, persons);
            }
        }
    }*/

    /*private void recoverLastName(Person p, List<Person> personList) {
        commonCheck(p, p.getChildren(), personList);
        if (p.getLastName() == null) {
            commonCheck(p, p.getSpouses(), personList);
        }
        if (p.getLastName() == null) {
            recoverByParents(p, p.getParents(), personList);
        }
        if (p.getLastName() == null) {
            commonCheck(p, p.getSiblings(), personList);
        }
    }*/


    private void commonCheck(Person p, List<String> list, List<Person> personList) {
        for (String obj : list) {
            if (isId(obj)) {
                for (Person ch : personList) {
                    String id = ch.getId();
                    if (id != null && id.compareTo(obj) == 0) {
                        String second = ch.getLastName();
                        String gender = ch.getGender();
                        if (gender != null && gender.compareTo("male") == 0 && second != null) {
                            p.setLastName(ch.getLastName());
                            break;
                        }
                    }
                }
            } else {
                String[] parts = split(obj);
                if (parts.length > 2) {
                    String first = parts[0];
                    String second = parts[1];
                    for (Person ch : personList) {
                        String pFirst = ch.getLastName();
                        String pSecond = ch.getLastName();
                        if (pFirst != null && first.compareTo(pFirst) == 0 && pSecond != null && second.compareTo(pSecond) == 0) {
                            String gender = ch.getGender();
                            if (gender != null && gender.compareTo("male") == 0) {
                                p.setLastName(second);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private void recoverByParents(Person p, List<String> list, List<Person> personList) {
        for (String obj : list) {
            if (isId(obj)) {
                for (Person parent : personList) {
                    String id = parent.getId();
                    if (id != null && id.compareTo(obj) == 0) {
                        String second = parent.getLastName();
                        if (second != null) {
                            p.setLastName(second);
                        }
                    }
                }
            } else {
                String[] parts = split(obj);
                if (parts.length > 2) {
                    String second = parts[1];
                    p.setLastName(second);
                }
            }
        }

    }

    public boolean isId(String str) {
        if (str.length() > 2) {
            try {
                Integer.parseInt(str.substring(1));
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }
        return false;
    }

    public String[] split(String str) {

        return str.split("\\s+");
    }


    /*public List<Person> removeCopies(List<Person> personList){
        List<Person> newList = new ArrayList<>();
        for(Person p : personList){
            if(!isCopy(p,personList)){
                newList.add(p);
            }
        }
        return newList;
    }*/

    /*public boolean isCopy(Person person, List<Person> personList) {
        boolean isCopy = false;
        for (Person p : personList) {
            if (p.equals(person))
                continue;
            if (person.getId() != null && p.getId() != null && person.getId().compareTo(p.getId()) == 0) {

                if (person.getFirstName() != null && p.getFirstName() == null) {
                    p.setFirstName(person.getFirstName());
                }
                if (person.getLastName() != null && p.getLastName() == null) {
                    p.setLastName(person.getLastName());
                }
                addInformation(person, p);
                isCopy = true;
                break;
            } else {
                String first = person.getFirstName();
                String last = person.getLastName();
                String pFirst = p.getFirstName();
                String pLast = p.getLastName();
                if (first != null && last != null && pFirst != null && pLast != null && first.compareTo(pFirst) == 0 && last.compareTo(pLast) == 0) {
                    addInformation(person, p);
                    isCopy = true;
                    break;
                }
            }
        }
        return isCopy;
    }*/


    /*public void addInformation(Person person, Person p) {
        String gender = person.getGender();
        if (gender != null && p.getGender() == null) {
            p.setGender(gender);
        }
        String id = person.getId();
        if (id != null && p.getId() == null) {
            p.setId(id);
        }
        List<String> pChildren = person.getChildren();
        List<String> pParents = person.getParents();
        List<String> pSiblings = person.getSiblings();
        List<String> pSpouses = person.getSpouses();
        if (pChildren != null) {
            addNewElement(pChildren, p.getChildren());
        }
        if (pParents != null) {
            addNewElement(pParents, p.getParents());
        }
        if (pSiblings != null) {
            addNewElement(pSiblings, p.getSiblings());
        }
        if (pSpouses != null) {
            addNewElement(pSpouses, p.getSpouses());
        }

    }*/

    private void addNewElement(List<String> from, List<String> to) {
        for (String str : from) {
            if (!to.contains(str)) {
                to.add(str);
            }
        }


    }


}
