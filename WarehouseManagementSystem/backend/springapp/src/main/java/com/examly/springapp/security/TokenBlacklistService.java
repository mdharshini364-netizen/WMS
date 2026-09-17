
package com.examly.springapp.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class TokenBlacklistService {

    // In production, use Redis or database for persistence
    private final Set<String> blacklistedTokens = new HashSet<>();

    // Add token to blacklist (on logout)
    public void blacklistToken(String token) {
        blacklistedTokens.add(token);
    }

    // Check if token is blacklisted
    public boolean isBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }

    // Remove expired tokens (cleanup - call periodically)
    public void clearExpiredTokens() {
        // In production, implement TTL-based cleanup
        // For now, this is a placeholder
    }
}

