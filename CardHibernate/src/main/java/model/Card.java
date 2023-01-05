package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "cards", uniqueConstraints = {@UniqueConstraint(columnNames =
        {"category_id", "answer", "question"})})
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @NonNull
    private String question;

    @Column(nullable = false)
    @NonNull
    private String answer;

    @Column(nullable = false)
    @NonNull
    private Date creationDate;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;



}
