package co.edu.javeriana.libreria.domain;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

    private Integer id;

    private String name;

    private String user_name;

    private String cargo;


    public User(){

    }

    public User(Integer id, String name, String user_name, String cargo) {
        this.id = id;
        this.name = name;
        this.user_name = user_name;
        this.cargo = cargo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
