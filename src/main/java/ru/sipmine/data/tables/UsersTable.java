package ru.sipmine.data.tables;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UsersTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "telegram_id")
    private long telegramId;

    @Column(name = "telegram_name")
    private String telegramName;


    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<ApiIngegratioTable> apiIngegratioTables;


    public void setId(int id) {
        this.id = id;
    }


    public void setTelegramId(long telegramId) {
        this.telegramId = telegramId;
    }

    public void setTelegramName(String telegramName) {
        this.telegramName = telegramName;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }
    public Long getTelegramId (){
        return telegramId;
    }

    public String getTelegramName() {
        return  telegramName;
    }

    public Set<ApiIngegratioTable> getApiTables(){
        return apiIngegratioTables;
    }


}


