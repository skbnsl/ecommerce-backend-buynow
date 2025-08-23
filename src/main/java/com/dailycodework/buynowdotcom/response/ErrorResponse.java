package com.dailycodework.buynowdotcom.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.resource.transaction.spi.SynchronizationRegistry;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

        private String message;

}
