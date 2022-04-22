package com.easyPicture.easyPicture.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Images",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"src", "user_id"})
        }
)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Image implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(columnDefinition="TEXT")
    private String src;

    @Column(name = "user_id",nullable = false, updatable = false, insertable = false)
    private Long userId;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Image image = (Image) o;
        return id != null && Objects.equals(id, image.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
