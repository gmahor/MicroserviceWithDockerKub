package com.eroom.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "map_roles_menu_sub_pages")
public class MapRoleMenuSubPages extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "menu_sub_pages_id")
    private Long menuSubPagesId;

    @ManyToOne
    @JoinColumn(name = "menu_sub_pages_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MenuSubpages menuSubPages;

    @Column(name = "role_id")
    private Long roleId;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Role role;

}
