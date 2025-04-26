package com.dmitry.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Сущность перевода средств между пользователями.
 * Содержит ID отправителя, получателя суммы, сумму перевода и дату создания.
 */
@Entity
@Table(name = "transfers", schema = "banking")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(
        name = "transfers_seq",
        sequenceName = "seq_transfers"
)
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transfers_seq")
    private Long id;

    @Column(name = "from_user_id", nullable = false)
    private Long fromUserId;

    @Column(name = "to_user_id", nullable = false)
    private Long toUserId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "Transfer{id=" + id + ", fromUserId=" + fromUserId +
                ", toUserId=" + toUserId + ", amount=" + amount + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transfer transfer)) return false;
        return id != null && id.equals(transfer.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
