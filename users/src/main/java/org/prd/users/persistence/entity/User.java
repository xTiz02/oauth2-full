package org.prd.users.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "auth_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String username;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean account_expired;

    @Column(nullable = false)
    private boolean account_locked;

    @Column(nullable = false)
    private boolean credentials_expired;

    @Column(nullable = false)
    private boolean enabled;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserPermission> other_permissions = new ArrayList<>();

    public ArrayList<String> getPermissionsArray(){
        ArrayList<String> allPermissions = new ArrayList<>(this.getRole().getPermissionsArray());

        if(this.getOther_permissions().isEmpty()){
            return allPermissions;
        }

        this.getOther_permissions().forEach(
                userPermission ->  {
                    allPermissions.add(userPermission.getPermission().getName());
                }
        );
        return allPermissions;
    }

}