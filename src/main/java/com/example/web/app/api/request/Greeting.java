package com.example.web.app.api.request;
public  class Greeting {
    private String family;
    private String university;
    private String name;
    @Override
    public String toString() {
        return "Greeting{" +
                ", family='" + family + '\'' +
                ", university='" + university + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
// перевести в нижние регистры , тк не мапится
    public String getFamily() {
        return family;
    }
    public void setFamily(String family) {
        this.family = family;
    }
    public String getUniversity() {
        return university;
    }
    public void setUniversity(String university) {
        this.university = university;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
