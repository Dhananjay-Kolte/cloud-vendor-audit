package com.basic.sample.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.basic.sample.contract.dto.ResourceType;
import com.basic.sample.dto.AttackCategory;
import com.basic.sample.dto.AttackProgress;
import com.basic.sample.dto.AttackType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "attack_summary")
public class AttackSummaryBo {
    
	@Id
    // @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "attack_id")
	private String attackId;
	
	//e.g. bucket or serviceAccount
	@Column(name = "resource_name")
	private String resourceName;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "resource_type")
	private ResourceType resourceType;
    
    //What was done? e.g. Made public, Deleted, Created,...
    @Enumerated(EnumType.STRING)
    private AttackType action;
    
    @Transient
    private String attackName;
    
    //Granted permission
    @JsonIgnore
    private String permission;

    private Double timetaken;

    private Date timeStamp;
    
    private String status;
    
    //@JsonIgnore
    @Column(name = "parent_id")
    private Long parentId;
    
    @Transient
//    @JsonIgnore
    private List<AttackSummaryBo> childrens;
    
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cloudstate_id", referencedColumnName = "id")
    @JsonIgnore
    private CloudStateBo cloudState;
    
    @Transient
    private List<AttackCategory> categories;
    
    @Transient
    private String attNckId; 
    
    @Transient
    private String impact;
    
    @Transient
    private String description;
    
    @Transient
    private Boolean recovered;
    
    @Transient
    @Builder.Default
    private Boolean persist = true;
    
    @Transient
    private List<AttackProgress> details;
}