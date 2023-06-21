package com.ibsys.backend.core.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DispositinEigenfertigungResultId implements Serializable {
    @Column(name = "article_number")
    private int articleNumber;
    @Column(name = "stuecklisten_gruppe")
    @Enumerated(EnumType.STRING)
    private StuecklistenGruppe stuecklistenGruppe;
}