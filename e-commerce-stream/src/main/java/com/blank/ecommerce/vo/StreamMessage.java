package com.blank.ecommerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StreamMessage {
    private Integer id;
    private String projectName;

    public static StreamMessage defaultMessage(){
        return new StreamMessage(1, "stream client message");
    }
}
