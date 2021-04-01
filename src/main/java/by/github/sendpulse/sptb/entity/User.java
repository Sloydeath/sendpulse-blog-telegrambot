package by.github.sendpulse.sptb.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode
@ToString
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

    @Column(name = "active")
    private boolean active;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_x_group_sub",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_sub_id"))
    private List<SubscriptionGroup> subscriptions;
}
