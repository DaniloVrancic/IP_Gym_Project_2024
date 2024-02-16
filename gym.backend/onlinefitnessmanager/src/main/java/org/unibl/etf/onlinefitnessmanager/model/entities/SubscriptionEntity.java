package org.unibl.etf.onlinefitnessmanager.model.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "subscription", schema = "db_online_fitness", catalog = "")
public class SubscriptionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity subscriber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionEntity that = (SubscriptionEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public UserEntity getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(UserEntity subscriber) {
        this.subscriber = subscriber;
    }
}
