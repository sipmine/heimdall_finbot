package ru.sipmine.data.tables;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "api_integration")
public class ApiIngegratioTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private UsersTable user;

    @Column(name = "api_token")
    private String tokenApi;



    public void setTokenApi(String tokenApi) {
        this.tokenApi = tokenApi;
    }

    public int getId() {
        return this.id;
    }


    public String getTokenApi() {
        return this.tokenApi;
    }


}
