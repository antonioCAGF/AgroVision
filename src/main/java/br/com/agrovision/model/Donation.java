package br.com.agrovision.model;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class Donation {
    private Long userId;
    private Long donationId;
    private String donationType;
    private String donationDescription;
    private String donationDatetime;
    private String donationStatus;
}


