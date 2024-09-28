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
@Table(name = "menu_sub_pages")
public class MenuSubpages extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "name")
    private String name;

    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "sub_menu_order")
    private Integer subMenuOrder;

    @Column(name = "sub_menu_icon", columnDefinition = "TEXT")
    private String subMenuIcon;

    @ManyToOne
    @JoinColumn(name = "menu_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Menu menu;
}
