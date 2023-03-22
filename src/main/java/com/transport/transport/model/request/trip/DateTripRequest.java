package com.transport.transport.model.request.trip;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateTripRequest implements Serializable {
    @NotBlank
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date date;
}