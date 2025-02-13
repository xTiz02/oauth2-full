package org.prd.users.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.prd.users.util.RoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "auth_roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GrantedPermission> permissions = new ArrayList<>();


    public ArrayList<String> getPermissionsArray(){
        if(this.getPermissions().isEmpty()){
            return new ArrayList<>();
        }
        ArrayList<String> permissions = new ArrayList<>();
        for(GrantedPermission permission : this.getPermissions()){
            permissions.add(permission.getPermission().getName());
        }
        return permissions;
    }


//    public void addUserRole(UserRoleEntity user_role) {
//        user_roles.add(user_role);
//        user_role.setRole(this);
//    }
//
//    public void removeUserRole(UserRoleEntity user_role) {
//        user_roles.remove(user_role);
//        user_role.setRole(null);
//    }

}

//For @ManyToMany associations, the REMOVE entity state transition doesn't make sense to be cascaded because it will propagate beyond the link table.