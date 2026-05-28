package org.myspring.springrecap.dtos;

import lombok.Builder;
import org.myspring.springrecap.enums.Status;

@Builder
public record TodoDTO(Status status, String description) {
}
