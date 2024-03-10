package org.unibl.etf.onlinefitnessmanager.verification;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;


public class VerificationToken implements Serializable {



    String token;
    LocalDateTime createdAt;
    LocalDateTime expiresAt;


    public VerificationToken()
    {
        this.token = UUID.randomUUID().toString() + UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.expiresAt = this.createdAt.plusHours(1L);
    }
    public VerificationToken(LocalDateTime createdAt)
    {
        this.token = UUID.randomUUID().toString() + UUID.randomUUID().toString();

        this.createdAt = createdAt;
        this.expiresAt = this.createdAt.plusHours(1L);
    }

    /**
     * Will be used only to create a temporary object to find in HashMap of VerificationTokens
     * as this class uses only the UUID to compare two differect VerificationToken objects.
     * @param existingUUID the pregenerated UUID that was somehow requested
     */
    public VerificationToken(String existingUUID)
    {
        this.token = existingUUID;
    }

    public LocalDateTime getCreatedAt()
    {
        return this.createdAt;
    }

    public LocalDateTime getExpiresAt()
    {
        return this.expiresAt;
    }

    public String getToken()
    {
        return this.token;
    }

    public boolean isExpired()
    {
        return LocalDateTime.now().isAfter(this.expiresAt);
    }


    @Override
    public int hashCode() {
        return Objects.hash(token);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerificationToken that = (VerificationToken) o;
        return Objects.equals(token, that.token);
    }


}
