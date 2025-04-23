package com.webflux.sample.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Persons")
public class PersonsDocument implements Serializable {

    @Id
    private String id;
    public String name; //Used by patch request
    public String email; //Used by patch request
    @Builder.Default
    private boolean active = true;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @DocumentReference
    private Set<AddressDocument> addresses = new HashSet<>();
    @DocumentReference
    private Set<PhonesDocument> phones = new HashSet<>();

}
