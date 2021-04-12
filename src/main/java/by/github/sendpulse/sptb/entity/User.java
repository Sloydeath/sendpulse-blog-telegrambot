package by.github.sendpulse.sptb.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "high_score")
    private int highScore;

    @Column(name = "current_score")
    private int currentScore;

    @Column(name = "active")
    private boolean active;

    @Column(name = "quiz_active")
    private boolean quizActive;

    @Column(name = "ts")
    private String dateOfRegistration;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<SubscriptionGroup> subscriptions = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return highScore == user.highScore && active == user.active && Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(dateOfRegistration, user.dateOfRegistration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, highScore, active, dateOfRegistration);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", highScore=" + highScore +
                ", active=" + active +
                ", dateOfRegistration='" + dateOfRegistration + '\'' +
                '}';
    }
}
