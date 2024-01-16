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
    @JoinColumn(name = "user_id", nullable = false)
    private UsersTable user;

    @Column(name = "name_api")
    private String nameApi;

    @Column(name = "api_token")
    private String tokenApi;

    @Column(name = "secret_token")
    private String secretToken;

    public void setUserId(UsersTable usersTable) {
        this.user = usersTable;
    }

    public void setTokenApi(String tokenApi) {
        this.tokenApi = tokenApi;
    }

    public void setSecret(String secretToken) {
        this.secretToken = secretToken;
    }

    public void setNameApi(String nameApi) {
        this.nameApi = nameApi;
    }

    public int getId() {
        return this.id;
    }

    public String getTokenApi() {
        return this.tokenApi;
    }

    public String getNameApi() {
        return this.nameApi;
    }

    public String getSecret() {
        return this.secretToken;
    }

}
