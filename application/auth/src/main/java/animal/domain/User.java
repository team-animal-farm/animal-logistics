package animal.domain;

import animal.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "p_user")
public class User extends BaseEntity {

    @Id
    private String username;

    private String nickname;

    private String email;

    private String phone;

    private String password;

    private Address address;

    private UserRole role;
}
