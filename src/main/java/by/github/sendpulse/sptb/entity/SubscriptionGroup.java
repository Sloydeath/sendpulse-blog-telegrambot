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

    @Column(name = "url_address")
    private String url;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_x_group_sub",
            joinColumns = @JoinColumn(name = "group_sub_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

    public void addUser(User user) {
        this.users.add(user);
        user.getSubscriptions().add(this);
    }

    public void removeUser(User user) {
        this.users.remove(user);
        user.getSubscriptions().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionGroup that = (SubscriptionGroup) o;
        return lastArticleId == that.lastArticleId && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastArticleId, url);
    }

    @Override
    public String toString() {
        return "SubscriptionGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastArticleId=" + lastArticleId +
                ", url='" + url + '\'' +
                '}';
    }
}
