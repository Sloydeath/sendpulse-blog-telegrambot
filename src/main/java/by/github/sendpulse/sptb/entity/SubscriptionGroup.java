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
@Table(name = "group_sub")
public class SubscriptionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_article_id")
    private int lastArticleId;

    @ManyToMany(mappedBy = "subscriptions", fetch = FetchType.EAGER)
    private List<User> users;
}
