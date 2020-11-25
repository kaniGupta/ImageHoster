package ImageHoster.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "images")
public class Image {

    //@Id annotation specifies that the corresponding attribute is a primary key
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Column annotation specifies that the attribute will be mapped to the column in the database.
    //Here the column name is explicitly mentioned as 'id'
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    // Text is a Postgres specific column type that allows you to save
    // text based data that will be longer than 256 characters
    // this is a base64 encoded version of the image
    @Column(columnDefinition = "TEXT")
    private String imageFile;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "image", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();

    public Image(final int id, final String title, final String imageFile, final Date date) {
        this.id = id;
        this.title = title;
        this.imageFile = imageFile;
        this.date = date;
    }

    public Image(final int id, final String title, final String imageFile, final String description, final Date date) {
        this.id = id;
        this.title = title;
        this.imageFile = imageFile;
        this.description = description;
        this.date = date;
    }
}
