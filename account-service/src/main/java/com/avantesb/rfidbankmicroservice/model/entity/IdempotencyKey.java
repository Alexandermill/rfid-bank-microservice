package com.avantesb.rfidbankmicroservice.model.entity;

import com.avantesb.rfidbankmicroservice.model.request.TransferRequest;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@RedisHash("IdempotencyKey")
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class IdempotencyKey implements Serializable {

    @Id
    @Indexed
    private String key;
    private TransferRequest request;

    @TimeToLive
    private Long expiration;
}
