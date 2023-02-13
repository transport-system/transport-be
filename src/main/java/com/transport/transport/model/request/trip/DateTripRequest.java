package com.transport.transport.model.request.trip;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DateTripRequest {
    @NotBlank
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date date;
}