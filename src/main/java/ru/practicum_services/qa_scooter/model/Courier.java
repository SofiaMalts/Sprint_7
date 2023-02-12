package ru.practicum_services.qa_scooter.model;

public class Courier {


    private String login;
    private String password;
    private String firstName;


    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }
    public Courier(String login, String firstName) {
        this.login = login;
    }
    public Courier(String password) {
        this.password = password;
    }


    public Courier() {
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLogin(String login){
        this.login = login;
    }
    public void setPassword(String login){
        this.password = password;
    }
    public void setFirstName(String login){
        this.firstName = firstName;
    }
}
