package csulb.cecs323.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@NamedNativeQuery(
        name = "ReturnAdHocTeam",
        query = "SELECT * " +
                "FROM AUTHORING_ENTITIES " +
                "WHERE name = ? AND AUTHORING_ENTITY_TYPE = 'Ad Hoc Team'",
        resultClass = Ad_Hoc_Team.class
)
@DiscriminatorValue("Ad Hoc Team")
public class Ad_Hoc_Team extends Authoring_Entities{

    @ManyToMany
    @JoinTable (
            name = "individual_on_team",
            joinColumns = @JoinColumn(name = "team_email"),
            inverseJoinColumns = @JoinColumn(name = "individual_email")
    )
    Set<Individual_Author> individual_authors;

    public Ad_Hoc_Team(){}

    public Ad_Hoc_Team(String email, String name)
    {
        super(email, name);
    }

    @Override
    public String toString(){
        return super.toString();
    }
}
