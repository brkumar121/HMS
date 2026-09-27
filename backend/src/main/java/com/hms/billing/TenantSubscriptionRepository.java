package com.hms.billing;
import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface TenantSubscriptionRepository extends JpaRepository<TenantSubscription,UUID> {}
