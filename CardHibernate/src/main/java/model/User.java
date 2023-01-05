package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (nullable = false, unique = true)
    @NonNull
    private String login;

    @Column (nullable = false)
    @NonNull
    private String password;

    @Column (nullable = false)
    @NonNull
    private String name;

    @Column (nullable = false)
    @NonNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date regDate;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE)
    //TODO json только на ввод
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Category> categories = new ArrayList<>();

}
